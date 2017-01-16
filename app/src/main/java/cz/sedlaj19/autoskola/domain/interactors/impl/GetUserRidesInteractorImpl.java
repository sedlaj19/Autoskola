package cz.sedlaj19.autoskola.domain.interactors.impl;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
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
import cz.sedlaj19.autoskola.domain.interactors.GetUserRidesInteractor;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;
import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.storage.converter.RideConverter;

/**
 * Created by Honza on 7. 8. 2016.
 */
public class GetUserRidesInteractorImpl extends AbstractInteractor implements GetUserRidesInteractor {

    private DatabaseReference database;
    private Callback callback;
    private String userId;

    public GetUserRidesInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                      Callback callback, String userId) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.userId = userId;
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
                            cz.sedlaj19.autoskola.storage.model.Ride ride = snapshot.getValue(cz.sedlaj19.autoskola.storage.model.Ride.class);
                            if(ride.getStudent().equals(userId)){
                                Ride r = RideConverter.convertToDomainModel(ride);
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
