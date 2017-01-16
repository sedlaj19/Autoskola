package cz.sedlaj19.autoskola.domain.interactors.impl;

import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.interactors.DashboardInteractor;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;

/**
 * Created by Honza on 28. 7. 2016.
 */
public class DashboardInteractorImpl extends AbstractInteractor implements DashboardInteractor{

    private DashboardInteractor.Callback callback;


    public DashboardInteractorImpl(Executor threadExecutor,
                                   MainThread mainThread,
                                   Callback callback) {
        super(threadExecutor, mainThread);
        this.callback = callback;
    }

    @Override
    public void run() {

    }
}
