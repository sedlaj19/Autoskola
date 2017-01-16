package cz.sedlaj19.autoskola.domain.interactors.impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.interactors.GetInstructorsRidesInteractor;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.storage.converter.RideConverter;
import cz.sedlaj19.autoskola.storage.model.Ride;

/**
 * Created by Honza on 11. 8. 2016.
 */
public class GetInstructorsRidesInteractorImpl extends AbstractInteractor implements GetInstructorsRidesInteractor {

    private Callback callback;
    private String instructorId;
    private DatabaseReference database;

    public GetInstructorsRidesInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                             Callback callback, String instructorId) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.instructorId = instructorId;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void run() {
        database.child(Constants.FirebaseModels.RIDES).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Container container = Container.getInstance();
                        container.clearRides();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Ride ride = snapshot.getValue(Ride.class);
                            if(ride.getInstructor().equals(instructorId)){
                                cz.sedlaj19.autoskola.domain.model.Ride r =
                                        RideConverter.convertToDomainModel(ride);
                                r.setId(snapshot.getKey());
                                container.addRide(r);
                            }
                        }
                        database.child(Constants.FirebaseModels.RIDES).removeEventListener(this);
                        callback.onRidesRetrieved();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }
}
