package cz.sedlaj19.autoskola.presentation.presenters.impl;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.interactors.GetAllInstructorsInteractor;
import cz.sedlaj19.autoskola.domain.interactors.GetAllStudentsInteractor;
import cz.sedlaj19.autoskola.domain.interactors.GetInstructorsRidesInteractor;
import cz.sedlaj19.autoskola.domain.interactors.impl.GetAllInstructorsInteractorImpl;
import cz.sedlaj19.autoskola.domain.interactors.impl.GetAllStudentsInteractorImpl;
import cz.sedlaj19.autoskola.domain.interactors.impl.GetInstructorsRidesInteractorImpl;
import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.presentation.presenters.InstructorPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.base.AbstractPresenter;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;

/**
 * Created by Honza on 7. 8. 2016.
 */
public class InstructorPresenterImpl extends AbstractPresenter implements InstructorPresenter,
        GetAllInstructorsInteractor.Callback{

    private InstructorPresenter.View view;
    private FirebaseAuth auth;
    private boolean studentsRetrieved;
    private boolean instructorsRetrieved;

    public InstructorPresenterImpl(Executor executor,
                                   MainThread mainThread,
                                   View view) {
        super(executor, mainThread);
        this.view = view;
        this.auth = FirebaseAuth.getInstance();
        this.studentsRetrieved = false;
        this.instructorsRetrieved = false;
    }

    @Override
    public void doLogout() {
        auth.signOut();
        view.onLogoutFinished();
    }

//    @Override
//    public void getAllStudents() {
//        view.showProgress();
//        GetAllStudentsInteractor getAllStudentsInteractor = new GetAllStudentsInteractorImpl(
//                ThreadExecutor.getInstance(),
//                MainThreadImpl.getInstance(),
//                this
//        );
//        getAllStudentsInteractor.execute();
//    }

    @Override
    public void getAllInstructors() {
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

//    @Override
//    public void onStudentsRetrieved() {
//        studentsRetrieved = true;
//        if(everythingRetrieved()) {
//            view.hideProgress();
//        }
//        view.onStudentsRetrieved();
//    }

    @Override
    public void onInstructorsRetrieved() {
        instructorsRetrieved = true;
        if(everythingRetrieved()){
            view.hideProgress();
        }
        view.onInstructorsRetrieved();
    }

    private boolean everythingRetrieved(){
        return instructorsRetrieved && studentsRetrieved;
    }

}
