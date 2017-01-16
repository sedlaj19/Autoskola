package cz.sedlaj19.autoskola.domain.interactors.impl;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.interactors.GetUserInteractor;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;
import cz.sedlaj19.autoskola.storage.converter.UserConverter;
import cz.sedlaj19.autoskola.storage.model.User;

/**
 * Created by Honza on 5. 8. 2016.
 */
public class GetUserInteractorImpl extends AbstractInteractor implements GetUserInteractor {

    private DatabaseReference database;
    private Callback callback;
    private String email;

    public GetUserInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                 Callback callback, String email) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.email = email;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void run() {
        database.child(Constants.FirebaseModels.USERS).addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        User user = dataSnapshot.getValue(User.class);
                        if(user != null && user.getEmail() != null && user.getEmail().equals(email)){
                            cz.sedlaj19.autoskola.domain.model.User u = UserConverter.convertToDomainModel(user);
                            u.setId(dataSnapshot.getKey());
                            database.child(Constants.FirebaseModels.USERS).removeEventListener(this);
                            callback.onUserRetrieved(u);
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Log.d(this.getClass().toString(), "onChildChanged");
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Log.d(this.getClass().toString(), "onChildRemoved");
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        Log.d(this.getClass().toString(), "onChildMoved");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }
}
