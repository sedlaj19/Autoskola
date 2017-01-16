package cz.sedlaj19.autoskola.presentation.presenters.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.interactors.CreateUserInteractor;
import cz.sedlaj19.autoskola.domain.interactors.SignUpInteractor;
import cz.sedlaj19.autoskola.domain.interactors.impl.CreateUserInteractorImpl;
import cz.sedlaj19.autoskola.domain.interactors.impl.SignUpInteractorImpl;
import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.presentation.presenters.SignUpPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.base.AbstractPresenter;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;

/**
 * Created by Honza on 28. 7. 2016.
 */
public class SignUpPresenterImpl extends AbstractPresenter implements SignUpPresenter,
        SignUpInteractor.Callback, CreateUserInteractor.Callback, FirebaseAuth.AuthStateListener{

    private SignUpPresenter.View view;
    private FirebaseAuth auth;
    private User user;

    private boolean flag;

    public SignUpPresenterImpl(Executor executor,
                               MainThread mainThread,
                               View view) {
        super(executor, mainThread);
        this.view = view;
        this.auth = FirebaseAuth.getInstance();
        this.flag = true;
    }

    @Override
    public void doSignUp(String email, String password, String firstname, String surname,
                         String phone, boolean instructor, String instructorPassword) {
        // TODO: check rest of the user attributes
        if(!email.contains("@")){
            view.showError("Wrong email address.", Constants.Login.LOGIN_ERROR_EMAIL);
            return;
        }
        if(password.length() < 6){
            view.showError("The password is too short, use 6 characters at least.", Constants.Login.LOGIN_ERROR_PASSWORD);
            return;
        }
        if(instructor && (instructorPassword == null || !instructorPassword.equals(Constants.SignUp.SIGNU_UP_INSTRUCTOR_PASSWORD))){
            view.showError("The password to create user as instructor is incorrect.", Constants.SignUp.SIGN_UP_ERROR_INSTR_PASSWORD);
            return;
        }
        view.showProgress();
        this.user = new User(firstname, surname, phone, email, instructor);
        SignUpInteractor signUpInteractor = new SignUpInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                email,
                password
        );
        signUpInteractor.execute();
    }

    @Override
    public void start() {
        this.auth.addAuthStateListener(this);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        this.auth.removeAuthStateListener(this);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onSignUpFinished(Task<AuthResult> resultTask) {
        if(!resultTask.isSuccessful()){
            view.hideProgress();
            view.showError("Sign up failed. Contact the admin please.", Constants.Login.LOGIN_ERROR_TOAST);
            return;
        }
        createUser();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        // TODO: Udelat to mozna pres onSignUpFinished, kde se to vola pouze jednou ...
    }

    @Override
    public void onUserCreated(Task<Void> result, User user) {
        view.hideProgress();
        if(!result.isSuccessful()){
            // TODO: Vytvoreny user pri registraci by se mel smazat ...
            view.showError("Sign up failed. Contact the admin please.", Constants.Login.LOGIN_ERROR_TOAST);
            return;
        }
        view.onSignUpSuccessful();
    }

    private void createUser(){
        CreateUserInteractor createUserInteractor = new CreateUserInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                this.user
        );
        createUserInteractor.execute();
    }

}
