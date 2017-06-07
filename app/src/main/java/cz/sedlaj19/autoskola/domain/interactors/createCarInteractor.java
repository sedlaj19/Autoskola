package cz.sedlaj19.autoskola.domain.interactors;

import com.google.android.gms.tasks.Task;

import cz.sedlaj19.autoskola.domain.interactors.base.Interactor;
import cz.sedlaj19.autoskola.domain.model.Car;

/**
 * Created by Honza on 25. 1. 2017.
 */

public interface CreateCarInteractor extends Interactor {

    interface Callback{

        void onCarCreated(Task<Void> result, Car car);

    }
}
