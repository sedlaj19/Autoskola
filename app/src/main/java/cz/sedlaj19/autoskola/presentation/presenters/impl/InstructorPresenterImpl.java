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
import cz.sedlaj19.autoskola.domain.interactors.impl.GetAllUsersInteractorImpl;
import cz.sedlaj19.autoskola.domain.model.Website;
import cz.sedlaj19.autoskola.presentation.presenters.InstructorPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.base.AbstractPresenter;
import cz.sedlaj19.autoskola.sync.DatabaseHelper;
import cz.sedlaj19.autoskola.sync.RxFirebaseObserver;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Honza on 7. 8. 2016.
 */
public class InstructorPresenterImpl extends AbstractPresenter implements InstructorPresenter,
        GetAllUsersInteractor.Callback{

    private InstructorPresenter.View view;
    private FirebaseAuth auth;
    private boolean studentsRetrieved;
    private boolean instructorsRetrieved;
    private DatabaseReference database;

    public InstructorPresenterImpl(Executor executor,
                                   MainThread mainThread,
                                   View view) {
        super(executor, mainThread);
        this.view = view;
        this.auth = FirebaseAuth.getInstance();
        this.studentsRetrieved = false;
        this.instructorsRetrieved = false;
        this.database = FirebaseDatabase.getInstance().getReference();
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
        GetAllUsersInteractor interactor = new GetAllUsersInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
        interactor.execute();
    }

    @Override
    public void getCars() {
        DatabaseHelper.getCars(database, view);
    }

    @Override
    public void getWebsites() {
        DatabaseHelper.getWebsites(database, view);
    }

    @Override
    public void changeWebsite(String url) {
        Website website = new Website();
        website.setUrl(url);
        RxFirebaseObserver.update(database, "", Constants.FirebaseModels.WEBSITES, website.toMap())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onWebsiteChanged, this::onError);
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
    public void onUsersRetrieved() {
        instructorsRetrieved = true;
        if(everythingRetrieved()){
            view.hideProgress();
        }
        view.onInstructorsRetrieved();
    }

    private void onError(Throwable throwable){
        Log.e(this.getClass().toString(), "ERROR", throwable);
    }

    private void onWebsiteChanged(Boolean response){
        Log.d(this.getClass().toString(), "TEST: website changed");
    }

    private boolean everythingRetrieved(){
        return instructorsRetrieved && studentsRetrieved;
    }

}
