package cz.sedlaj19.autoskola.presentation.presenters.impl;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.interactors.GetAllUsersInteractor;
import cz.sedlaj19.autoskola.domain.interactors.impl.GetAllUsersInteractorImpl;
import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.presentation.presenters.InstructorStudentsPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.base.AbstractPresenter;
import cz.sedlaj19.autoskola.storage.converter.UserConverter;
import cz.sedlaj19.autoskola.sync.RxFirebaseObserver;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Honza on 12. 8. 2016.
 */
public class InstructorStudentsPresenterImpl extends AbstractPresenter implements InstructorStudentsPresenter,
        GetAllUsersInteractorImpl.Callback{

    private InstructorStudentsPresenter.View view;
    private DatabaseReference database;

    public InstructorStudentsPresenterImpl(Executor executor, MainThread mainThread,
                                           View view) {
        super(executor, mainThread);
        this.view = view;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void getStudents() {
        GetAllUsersInteractor getAllStudentsInteractor = new GetAllUsersInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
        getAllStudentsInteractor.execute();
    }

    @Override
    public void onSwiped(int direction) {
        int text = R.string.student_finished;
        if(direction == Constants.ItemSwipe.SWIPE_DELETE){
            text = R.string.student_removed;
        }else if(direction == Constants.ItemSwipe.SWIPE_UPDATE){

        }
        view.showSnackBar(text, direction);
    }

    @Override
    public void handleSwipeAction(Object o, int direction) {
        User student;
        if(o instanceof User){
            student = (User) o;
        }else{
            return;
        }
        if(direction == Constants.ItemSwipe.SWIPE_DELETE){
            // TODO: add everywhere subscription variables, so no memory leaks occur
            RxFirebaseObserver.delete(database.child(Constants.FirebaseModels.USERS).child(student.getId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onStudentDeleted, this::onError);
        }else{
            student.setFinished(true);
            cz.sedlaj19.autoskola.storage.model.User user = UserConverter.convertToStorageModel(student);
            RxFirebaseObserver.update(database, user.getId(), Constants.FirebaseModels.USERS, user.toMap())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onStudentUpdated, this::onError);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void resume() {
        getStudents();
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
    public void onUsersRetrieved() {
        view.onStudentsRetrieved();
    }

    private void onError(Throwable throwable){
        Log.e(this.getClass().toString(), "ERROR", throwable);
    }

    private void onStudentDeleted(Boolean response){

    }

    private void onStudentUpdated(Boolean response){

    }
}
