package cz.sedlaj19.autoskola.domain.interactors.impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.interactors.GetRidesByDayInteractor;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;
import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.storage.converter.RideConverter;

/**
 * Created by Honza on 14. 8. 2016.
 */
public class GetRidesByDayInteractorImpl extends AbstractInteractor implements GetRidesByDayInteractor {

    private Callback callback;
    private long date;
    private DatabaseReference database;

    public GetRidesByDayInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                       Callback callback, long date) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.date = date;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void run() {
        database.child(Constants.FirebaseModels.RIDES).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final List<Ride> rides = new ArrayList<>();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            cz.sedlaj19.autoskola.storage.model.Ride ride =
                                    snapshot.getValue(cz.sedlaj19.autoskola.storage.model.Ride.class);
                            if (compareDates(ride.getDate())) {
                                rides.add(RideConverter.convertToDomainModel(ride));
                            }
                        }
                        database.child(Constants.FirebaseModels.RIDES).removeEventListener(this);
                        Collections.sort(rides);
                        mMainThread.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onRidesRetrieved(rides);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }

    private boolean compareDates(long rideDateMillis){
        Calendar rideCalendar = Calendar.getInstance();
        Calendar addCalendar = Calendar.getInstance();
        rideCalendar.setTimeInMillis(rideDateMillis);
        addCalendar.setTimeInMillis(date);
        int year = rideCalendar.get(Calendar.YEAR) - addCalendar.get(Calendar.YEAR);
        int month = rideCalendar.get(Calendar.MONTH) - addCalendar.get(Calendar.MONTH);
        int day = rideCalendar.get(Calendar.DAY_OF_MONTH) - addCalendar.get(Calendar.DAY_OF_MONTH);
        return year == 0 && month == 0 && day == 0;
    }
}
