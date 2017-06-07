package cz.sedlaj19.autoskola.domain.interactors.impl;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.interactors.GetUserByDeviceInteractor;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;
import cz.sedlaj19.autoskola.storage.converter.UserConverter;
import cz.sedlaj19.autoskola.storage.model.User;
import cz.sedlaj19.autoskola.utils.Converter;

/**
 * Created by Honza on 22. 1. 2017.
 */

public class GetUserByDeviceInteractorImpl extends AbstractInteractor implements
        GetUserByDeviceInteractor{

    private Callback callback;
    private String deviceId;
    private DatabaseReference database;

    public GetUserByDeviceInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                         String deviceId, Callback callback) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.deviceId = deviceId;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void run() {
        database.child(Constants.FirebaseModels.USERS).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot == null) return;
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            User user = snapshot.getValue(User.class);
                            if(user.getDeviceId().equals(deviceId)){
                                callback.onUserRetrieved(UserConverter.convertToDomainModel(user));
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }
}
