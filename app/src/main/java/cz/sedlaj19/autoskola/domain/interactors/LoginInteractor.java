package cz.sedlaj19.autoskola.domain.interactors;


import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import cz.sedlaj19.autoskola.domain.interactors.base.Interactor;

public interface LoginInteractor extends Interactor {

    interface Callback {

        void onLoginFinished(Task<AuthResult> resultTask);

    }

}
