package cz.sedlaj19.autoskola.domain.interactors.impl;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.interactors.CreateUserInteractor;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;
import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.storage.converter.UserConverter;

/**
 * Created by Honza on 5. 8. 2016.
 */
public class CreateUserInteractorImpl extends AbstractInteractor implements CreateUserInteractor,
        OnCompleteListener<Void>{

    private DatabaseReference database;
    private Callback callback;
    private User user;

    public CreateUserInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                    Callback callback, User user) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.user = user;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void run() {
        cz.sedlaj19.autoskola.storage.model.User userToStore = UserConverter.convertToStorageModel(user);
        String key = database.child(Constants.FirebaseModels.USERS).push().getKey();
        user.setId(key);
        database.child(Constants.FirebaseModels.USERS).child(key).setValue(userToStore).addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        callback.onUserCreated(task, user);
    }
}
