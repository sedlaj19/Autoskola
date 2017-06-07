package cz.sedlaj19.autoskola.presentation.presenters.impl;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.interactors.GetAllUsersInteractor;
import cz.sedlaj19.autoskola.domain.interactors.GetUserRidesInteractor;
import cz.sedlaj19.autoskola.domain.interactors.impl.GetAllUsersInteractorImpl;
import cz.sedlaj19.autoskola.domain.interactors.impl.GetUserRidesInteractorImpl;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.presentation.presenters.DashboardPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.base.AbstractPresenter;
import cz.sedlaj19.autoskola.sync.DatabaseHelper;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by Honza on 28. 7. 2016.
 */
public class DashboardPresenterImpl extends AbstractPresenter implements DashboardPresenter,
        GetUserRidesInteractor.Callback, GetAllUsersInteractor.Callback{

    private DashboardPresenter.View view;
    private FirebaseAuth auth;
    private boolean ridesRetrieved;
    private boolean instructorsRetrieved;
    private boolean carsRetrieved;
    private DatabaseReference database;

    public DashboardPresenterImpl(Executor executor,
                                  MainThread mainThread,
                                  View view) {
        super(executor, mainThread);
        this.view = view;
        this.auth = FirebaseAuth.getInstance();
        this.instructorsRetrieved = false;
        this.ridesRetrieved = false;
        this.carsRetrieved = false;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void doLogout() {
        auth.signOut();
        view.onLogoutFinished();
    }

    @Override
    public void getUserRides() {
        if(!instructorsRetrieved && !carsRetrieved){
            return;
        }
        view.showProgress();
        ridesRetrieved = false;
        GetUserRidesInteractor interactor = new GetUserRidesInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                Container.getInstance().getLoggedInUser().getId()
        );
        interactor.execute();
    }

    @Override
    public void getCars() {
        carsRetrieved = false;
        DatabaseHelper.getCars(database, view);
    }

    @Override
    public void getInstructors() {
        instructorsRetrieved = false;
        view.showProgress();
        GetAllUsersInteractor interactor = new GetAllUsersInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
        interactor.execute();
    }

    @Override
    public void carsRetrieved(boolean retrieved) {
        carsRetrieved = retrieved;
    }

    @Override
    public String checkUrl(String url) {
        if(!url.contains("http")){
            return "http://" + url;
        }
        if(!url.contains(".")){
            return null;
        }
        return url;
    }

    @Override
    public void start() {

    }

    @Override
    public void resume() {
        getInstructors();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onRidesRetrieved() {
        ridesRetrieved = true;
        hideProgress();
        view.onUserRidesRetrieved();
    }

    @Override
    public void onUsersRetrieved() {
        instructorsRetrieved = true;
        hideProgress();
        view.onInstructorRetrieved();
    }

    private void hideProgress(){
        if(ridesRetrieved){
            view.hideProgress();
        }
    }

    @Override
    public void getWebsites() {
        DatabaseHelper.getWebsites(database, view);
    }
}
