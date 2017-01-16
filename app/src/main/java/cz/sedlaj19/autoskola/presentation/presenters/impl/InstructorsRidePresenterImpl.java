package cz.sedlaj19.autoskola.presentation.presenters.impl;

import android.util.Log;

import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.interactors.GetInstructorsRidesInteractor;
import cz.sedlaj19.autoskola.domain.interactors.impl.GetInstructorsRidesInteractorImpl;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.presentation.presenters.InstructorRidesPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.base.AbstractPresenter;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;

/**
 * Created by Honza on 12. 8. 2016.
 */
public class InstructorsRidePresenterImpl extends AbstractPresenter implements InstructorRidesPresenter,
        GetInstructorsRidesInteractor.Callback{

    InstructorRidesPresenter.View view;

    public InstructorsRidePresenterImpl(Executor executor, MainThread mainThread,
                                        View view) {
        super(executor, mainThread);
        this.view = view;
    }

    @Override
    public void getInstructorsRides() {
        GetInstructorsRidesInteractor interactor = new GetInstructorsRidesInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                Container.getInstance().getLoggedInUser().getId()
        );
        interactor.execute();
    }

    @Override
    public void start() {

    }

    @Override
    public void resume() {
        Container container = Container.getInstance();
        if(container.isRideChange()){
            view.onRideChanged(container.getUpdatedRidePosition());
            container.setRideChange(false);
            container.setUpdatedRidePosition(-1);
        }
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
        view.onInstructorRidesRetrieved();
    }
}
