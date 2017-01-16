package cz.sedlaj19.autoskola.domain.interactors;

import com.google.android.gms.tasks.Task;

import cz.sedlaj19.autoskola.domain.interactors.base.Interactor;
import cz.sedlaj19.autoskola.domain.model.User;

/**
 * Created by Honza on 5. 8. 2016.
 */
public interface CreateUserInteractor extends Interactor{

    interface Callback{

        void onUserCreated(Task<Void> result, User user);

    }

}
