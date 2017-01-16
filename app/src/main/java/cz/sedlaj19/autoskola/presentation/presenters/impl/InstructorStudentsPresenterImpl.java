package cz.sedlaj19.autoskola.presentation.presenters.impl;

import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.interactors.GetAllStudentsInteractor;
import cz.sedlaj19.autoskola.domain.interactors.impl.GetAllStudentsInteractorImpl;
import cz.sedlaj19.autoskola.presentation.presenters.InstructorStudentsPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.base.AbstractPresenter;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;

/**
 * Created by Honza on 12. 8. 2016.
 */
public class InstructorStudentsPresenterImpl extends AbstractPresenter implements InstructorStudentsPresenter,
        GetAllStudentsInteractorImpl.Callback{

    private InstructorStudentsPresenter.View view;

    public InstructorStudentsPresenterImpl(Executor executor, MainThread mainThread,
                                           View view) {
        super(executor, mainThread);
        this.view = view;
    }

    @Override
    public void getStudents() {
        GetAllStudentsInteractor getAllStudentsInteractor = new GetAllStudentsInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
        getAllStudentsInteractor.execute();
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
    public void onStudentsRetrieved() {
        view.onStudentsRetrieved();
    }
}
