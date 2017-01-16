package cz.sedlaj19.autoskola.domain.interactors.impl;

import java.util.ArrayList;
import java.util.List;

import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.interactors.GetInstructorsNamesInteractor;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;
import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.domain.repository.Container;

/**
 * Created by Honza on 8. 8. 2016.
 */
public class GetInstructorsNamesInteractorImpl extends AbstractInteractor
        implements GetInstructorsNamesInteractor{

    private Callback callback;

    public GetInstructorsNamesInteractorImpl(Executor threadExecutor,
                                             MainThread mainThread,
                                             Callback callback) {
        super(threadExecutor, mainThread);
        this.callback = callback;
    }

    @Override
    public void run() {
        List<User> instructors = Container.getInstance().getInstructors();
        List<String> result = new ArrayList<>();
        for(User user : instructors){
            result.add(user.getName() + " " + user.getSurname());
        }
        callback.onNamesRetrieved(result);
    }
}
