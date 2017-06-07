package cz.sedlaj19.autoskola.presentation.presenters.impl;

import com.google.android.gms.tasks.Task;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.interactors.CreateUserInteractor;
import cz.sedlaj19.autoskola.domain.interactors.impl.CreateUserInteractorImpl;
import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.presentation.presenters.base.AbstractPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.AddStudentPresenter;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;

/**
 * Created by Honza on 25. 1. 2017.
 */

public class AddStudentPresenterImpl extends AbstractPresenter implements
        AddStudentPresenter,
        CreateUserInteractor.Callback{

    private View view;

    public AddStudentPresenterImpl(Executor executor, MainThread mainThread, View view) {
        super(executor, mainThread);
        this.view = view;
    }

    @Override
    public void createUser(String name, String surname, String email, String phone) {
        User user = new User(name, surname, phone, email, false, null);
        CreateUserInteractor interactor = new CreateUserInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                user
        );
        interactor.execute();
    }

    @Override
    public boolean checkName(String name) {
        // TODO
        return true;
    }

    @Override
    public boolean checkEmail(String email) {
        // TODO
        return true;
    }

    @Override
    public boolean checkPhone(String phone) {
        // TODO
        return true;
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
    public void onUserCreated(Task<Void> result, User user) {
        if(!result.isSuccessful()){
            view.showError("User has not been created.", Constants.Login.LOGIN_ERROR_TOAST);
            return;
        }
        view.onStudentCreated(user);
    }
}
