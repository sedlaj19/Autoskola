package cz.sedlaj19.autoskola.domain.interactors.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.interactors.GetStudentsNamesInteractor;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;
import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.domain.repository.Container;

/**
 * Created by Honza on 7. 8. 2016.
 */
public class GetStudentsNamesInteractorImpl extends AbstractInteractor implements GetStudentsNamesInteractor {

    private Callback callback;

    public GetStudentsNamesInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback) {
        super(threadExecutor, mainThread);
        this.callback = callback;
    }

    @Override
    public void run() {
        List<User> students = Container.getInstance().getStudents();
        List<String> result = new ArrayList<>();
        for(User user : students){
            result.add(user.getName() + " " + user.getSurname());
        }
        callback.onStudentsNamesRetrieved(result);
    }
}
