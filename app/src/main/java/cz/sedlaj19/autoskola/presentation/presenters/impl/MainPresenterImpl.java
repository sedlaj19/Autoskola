package cz.sedlaj19.autoskola.presentation.presenters.impl;


import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.OnClick;
import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.interactors.GetUserInteractor;
import cz.sedlaj19.autoskola.domain.interactors.LoginInteractor;
import cz.sedlaj19.autoskola.domain.interactors.impl.GetUserInteractorImpl;
import cz.sedlaj19.autoskola.domain.interactors.impl.LoginInteractorImpl;
import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.presentation.presenters.MainPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.base.AbstractPresenter;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;

public class MainPresenterImpl extends AbstractPresenter implements MainPresenter,
        LoginInteractor.Callback, GetUserInteractor.Callback, FirebaseAuth.AuthStateListener {

    private MainPresenter.View view;
    private FirebaseAuth auth;

    private boolean flag;

    public MainPresenterImpl(Executor executor,
                             MainThread mainThread,
                             View view) {
        super(executor, mainThread);
        this.view = view;
        this.auth = FirebaseAuth.getInstance();
        this.flag = true;
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
    public void onLoginFinished(Task<AuthResult> resultTask) {
        view.hideProgress();
        if(!resultTask.isSuccessful()){
            view.showError("Login failed. This account does not exist.", Constants.Login.LOGIN_ERROR_TOAST);
            flag = true;
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null && flag){
//            view.hideProgress();
            view.onLoginSuccessful(user.getEmail());
            flag = false;
        }
    }

    @Override
    public void doLogin(String username, String password) {
        //TODO: check the internet connection
        if(!username.contains("@")){
            view.showError("Wrong email address.", Constants.Login.LOGIN_ERROR_EMAIL);
        }
        if(password.length() < 6){
            view.showError("The password is too short, use 6 characters at least.", Constants.Login.LOGIN_ERROR_PASSWORD);
        }
        view.showProgress();
        LoginInteractor loginInteractor = new LoginInteractorImpl(
                ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                this, username, password);
        loginInteractor.execute();
    }

    @Override
    public void getUserByEmail(String email) {
        view.showProgress();
        GetUserInteractor getUserInteractor = new GetUserInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                email
        );
        getUserInteractor.execute();
    }

    @Override
    public void onUserRetrieved(User user) {
        Container.getInstance().setLoggedInUser(user);
        view.onUserRetrieved(user);
        view.hideProgress();
    }
}
