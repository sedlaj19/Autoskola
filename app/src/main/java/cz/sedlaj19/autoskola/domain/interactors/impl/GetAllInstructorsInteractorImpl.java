package cz.sedlaj19.autoskola.domain.interactors.impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.interactors.GetAllInstructorsInteractor;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.storage.converter.UserConverter;
import cz.sedlaj19.autoskola.storage.model.User;

/**
 * Created by Honza on 8. 8. 2016.
 */
public class GetAllInstructorsInteractorImpl extends AbstractInteractor
        implements GetAllInstructorsInteractor {

    private Callback callback;
    private DatabaseReference database;

    public GetAllInstructorsInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                           Callback callback) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void run() {
        final Container container = Container.getInstance();
        container.clearInstructors();
        database.child(Constants.FirebaseModels.USERS).addValueEventListener(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            User user = snapshot.getValue(User.class);
                            if(user.isInstructor()){
                                Log.d(this.getClass().toString(), user.toString());
                                cz.sedlaj19.autoskola.domain.model.User u = UserConverter.convertToDomainModel(user);
                                u.setId(snapshot.getKey());
                                container.addInstructor(u);
                            }
                        }
                        database.child(Constants.FirebaseModels.USERS).removeEventListener(this);
                        callback.onInstructorsRetrieved();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }
}
