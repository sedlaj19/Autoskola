package cz.sedlaj19.autoskola.domain.interactors.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.interactors.LoginInteractor;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;
import cz.sedlaj19.autoskola.domain.repository.Repository;

public class LoginInteractorImpl extends AbstractInteractor implements LoginInteractor,
        OnCompleteListener<AuthResult> {

    private LoginInteractor.Callback callback;
    private FirebaseAuth auth;
    private String username;
    private String password;

    public LoginInteractorImpl(Executor threadExecutor,
                               MainThread mainThread,
                               Callback callback,
                               String username, String password) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.auth = FirebaseAuth.getInstance();
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        Log.d(this.getClass().toString(), "signInWithEmailAndPassword:onComplete: " + task.isSuccessful());
        callback.onLoginFinished(task);
    }
}
