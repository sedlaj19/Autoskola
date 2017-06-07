package cz.sedlaj19.autoskola.domain.interactors;

import cz.sedlaj19.autoskola.domain.interactors.base.Interactor;

/**
 * Created by Honza on 22. 1. 2017.
 */

public interface UpdateUserInteractor extends Interactor {

    interface Callback{

        void onUserUpdated();

    }

}
