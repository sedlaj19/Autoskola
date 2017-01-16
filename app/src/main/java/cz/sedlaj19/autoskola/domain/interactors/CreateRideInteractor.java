package cz.sedlaj19.autoskola.domain.interactors;

import com.google.android.gms.tasks.Task;

import cz.sedlaj19.autoskola.domain.interactors.base.Interactor;
import cz.sedlaj19.autoskola.domain.model.Ride;

/**
 * Created by Honza on 7. 8. 2016.
 */
public interface CreateRideInteractor extends Interactor {

    interface Callback{

        void onRideCreated(Task<Void> result, Ride ride);

    }

}
