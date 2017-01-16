package cz.sedlaj19.autoskola.domain.interactors;

import cz.sedlaj19.autoskola.domain.interactors.base.Interactor;
import cz.sedlaj19.autoskola.domain.model.Ride;

/**
 * Created by Honza on 13. 8. 2016.
 */
public interface UpdateRideInteractor extends Interactor {

    interface Callback{

        void onRideUpdated(Ride ride);

    }

}
