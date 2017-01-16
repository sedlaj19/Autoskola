package cz.sedlaj19.autoskola.domain.interactors.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import cz.sedlaj19.autoskola.domain.interactors.GetAllStudentsInteractor;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.storage.converter.UserConverter;
import cz.sedlaj19.autoskola.storage.model.User;

/**
 * Created by Honza on 7. 8. 2016.
 */
public class GetAllStudentsInteractorImpl extends AbstractInteractor implements GetAllStudentsInteractor{

    private DatabaseReference database;
    private Callback callback;

    public GetAllStudentsInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                        Callback callback) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void run() {
        final Container container = Container.getInstance();
        container.clearStudents();
        database.child(Constants.FirebaseModels.USERS).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            User user = snapshot.getValue(User.class);
                            if(!user.isFinished() && !user.isInstructor()){
                                cz.sedlaj19.autoskola.domain.model.User u = UserConverter.convertToDomainModel(user);
                                u.setId(snapshot.getKey());
                                Log.d(this.getClass().toString(), user.toString());
                                container.addStudent(u);
                            }
                        }
                        database.child(Constants.FirebaseModels.USERS).removeEventListener(this);
                        callback.onStudentsRetrieved();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }
}
