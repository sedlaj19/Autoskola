package cz.sedlaj19.autoskola.domain.interactors.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.interactors.SignUpInteractor;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;

/**
 * Created by Honza on 28. 7. 2016.
 */
public class SignUpInteractorImpl extends AbstractInteractor implements SignUpInteractor,
        OnCompleteListener<AuthResult>{

    private SignUpInteractor.Callback callback;
    private FirebaseAuth auth;
    private String email;
    private String password;

    public SignUpInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                Callback callback, String email, String password) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.auth = FirebaseAuth.getInstance();
        this.email = email;
        this.password = password;
    }

    @Override
    public void run() {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        callback.onSignUpFinished(task);
    }
}
