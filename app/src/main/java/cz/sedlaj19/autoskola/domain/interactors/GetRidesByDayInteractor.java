package cz.sedlaj19.autoskola.domain.interactors;

import java.util.List;

import cz.sedlaj19.autoskola.domain.interactors.base.Interactor;
import cz.sedlaj19.autoskola.domain.model.Ride;

/**
 * Created by Honza on 14. 8. 2016.
 */
public interface GetRidesByDayInteractor extends Interactor {

    interface Callback{

        void onRidesRetrieved(List<Ride> rides);

    }

}
