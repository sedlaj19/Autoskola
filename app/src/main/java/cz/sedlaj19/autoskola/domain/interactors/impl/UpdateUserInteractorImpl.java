package cz.sedlaj19.autoskola.domain.interactors.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;
import cz.sedlaj19.autoskola.domain.interactors.UpdateUserInteractor;
import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.storage.converter.UserConverter;

/**
 * Created by Honza on 22. 1. 2017.
 */

public class UpdateUserInteractorImpl extends AbstractInteractor implements UpdateUserInteractor,
        OnCompleteListener<Void>{

    private Callback callback;
    private User user;
    private DatabaseReference database;

    public UpdateUserInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                    User user, Callback callback) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.user = user;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void run() {
        cz.sedlaj19.autoskola.storage.model.User newUser = UserConverter.convertToStorageModel(this.user);
        Map<String, Object> userValues = newUser.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Users/" + newUser.getId(), userValues);
        database.updateChildren(childUpdates).addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        Log.d(this.getClass().toString(), "TEST: User updated ...");
        callback.onUserUpdated();
    }
}
