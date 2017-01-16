package cz.sedlaj19.autoskola.domain.interactors.impl;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.interactors.GetUserByNameAndSurnameInteractor;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;
import cz.sedlaj19.autoskola.storage.converter.UserConverter;
import cz.sedlaj19.autoskola.storage.model.User;

/**
 * Created by Honza on 11. 8. 2016.
 */
public class GetUserByNameAndSurnameInteractorImpl extends AbstractInteractor implements
        GetUserByNameAndSurnameInteractor{

    private Callback callback;
    private String name;
    private String surname;
    private DatabaseReference database;

    public GetUserByNameAndSurnameInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                                 Callback callback, String name, String surname) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.name = name;
        this.surname = surname;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void run() {
        database.child(Constants.FirebaseModels.USERS).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            User user = snapshot.getValue(User.class);
                            if(user.getName().equals(name) && user.getSurname().equals(surname)){
                                user.setId(snapshot.getKey());
                                database.child(Constants.FirebaseModels.USERS).removeEventListener(this);
                                callback.onUserRetrieved(UserConverter.convertToDomainModel(user));
                                return;
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
