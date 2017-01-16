package cz.sedlaj19.autoskola.domain.interactors;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import cz.sedlaj19.autoskola.domain.interactors.base.Interactor;
import cz.sedlaj19.autoskola.domain.model.User;

/**
 * Created by Honza on 28. 7. 2016.
 */
public interface SignUpInteractor extends Interactor{

    interface Callback{

        void onSignUpFinished(Task<AuthResult> resultTask);

    }

}
