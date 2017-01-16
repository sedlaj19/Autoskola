package cz.sedlaj19.autoskola.presentation.presenters.impl;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.interactors.GetAllInstructorsInteractor;
import cz.sedlaj19.autoskola.domain.interactors.GetUserRidesInteractor;
import cz.sedlaj19.autoskola.domain.interactors.impl.GetAllInstructorsInteractorImpl;
import cz.sedlaj19.autoskola.domain.interactors.impl.GetUserRidesInteractorImpl;
import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.presentation.presenters.DashboardPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.base.AbstractPresenter;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;

/**
 * Created by Honza on 28. 7. 2016.
 */
public class DashboardPresenterImpl extends AbstractPresenter implements DashboardPresenter,
        GetUserRidesInteractor.Callback, GetAllInstructorsInteractor.Callback{

    private DashboardPresenter.View view;
    private FirebaseAuth auth;
    private boolean ridesRetrieved;
    private boolean instructorsRetrieved;

    public DashboardPresenterImpl(Executor executor,
                                  MainThread mainThread,
                                  View view) {
        super(executor, mainThread);
        this.view = view;
        this.auth = FirebaseAuth.getInstance();
        this.instructorsRetrieved = false;
        this.ridesRetrieved = false;
    }

    @Override
    public void doLogout() {
        auth.signOut();
        view.onLogoutFinished();
    }

    @Override
    public void getUserRides() {
        view.showProgress();
        ridesRetrieved = false;
        Log.d(this.getClass().toString(), "getUserRides");
        GetUserRidesInteractor interactor = new GetUserRidesInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                Container.getInstance().getLoggedInUser().getId()
        );
        interactor.execute();
    }

    @Override
    public void getInstructors() {
        instructorsRetrieved = false;
        view.showProgress();
        GetAllInstructorsInteractor interactor = new GetAllInstructorsInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
        interactor.execute();
    }

    @Override
    public void start() {

    }

    @Override
    public void resume() {

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
        Log.d(this.getClass().toString(), "Krucinal");
        ridesRetrieved = true;
        hideProgress();
        view.onUserRidesRetrieved();
    }

    @Override
    public void onInstructorsRetrieved() {
        instructorsRetrieved = true;
        hideProgress();
        view.onInstructorRetrieved();
    }

    private void hideProgress(){
        if(instructorsRetrieved && ridesRetrieved){
            view.hideProgress();
        }
    }
}
