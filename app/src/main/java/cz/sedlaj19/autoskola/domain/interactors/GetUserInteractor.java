package cz.sedlaj19.autoskola.domain.interactors;

import cz.sedlaj19.autoskola.domain.interactors.base.Interactor;
import cz.sedlaj19.autoskola.domain.model.User;

/**
 * Created by Honza on 5. 8. 2016.
 */
public interface GetUserInteractor extends Interactor{

    interface Callback{

        void onUserRetrieved(User user);

    }

}
