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
import cz.sedlaj19.autoskola.domain.interactors.GetAllUsersInteractor;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.storage.converter.UserConverter;
import cz.sedlaj19.autoskola.storage.model.User;

/**
 * Created by Honza on 8. 8. 2016.
 */
public class GetAllUsersInteractorImpl extends AbstractInteractor
        implements GetAllUsersInteractor {

    private Callback callback;
    private DatabaseReference database;

    public GetAllUsersInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                     Callback callback) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void run() {
        final Container container = Container.getInstance();
        database.child(Constants.FirebaseModels.USERS).addValueEventListener(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        container.clearInstructors();
                        container.clearStudents();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            User user = snapshot.getValue(User.class);
                            cz.sedlaj19.autoskola.domain.model.User u = UserConverter.convertToDomainModel(user);
                            u.setId(snapshot.getKey());
                            if(user.isInstructor()){
                                Log.d(this.getClass().toString(), user.toString());
                                container.addInstructor(u);
                            }else if(!user.isFinished()){
                                container.addStudent(u);
                            }
                        }
                        database.child(Constants.FirebaseModels.USERS).removeEventListener(this);
                        callback.onUsersRetrieved();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }
}
