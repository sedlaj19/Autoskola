package cz.sedlaj19.autoskola.domain.interactors;

import cz.sedlaj19.autoskola.domain.interactors.base.Interactor;

/**
 * Created by Honza on 28. 7. 2016.
 */
public interface DashboardInteractor extends Interactor {

    interface Callback{

        void onLogout();

    }

}
