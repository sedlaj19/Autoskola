package cz.sedlaj19.autoskola.domain.interactors;

import cz.sedlaj19.autoskola.domain.interactors.base.Interactor;
import cz.sedlaj19.autoskola.domain.model.User;

/**
 * Created by Honza on 22. 1. 2017.
 */

public interface GetUserByDeviceInteractor extends Interactor {

    interface Callback{

        void onUserRetrieved(User user);
    }

}
