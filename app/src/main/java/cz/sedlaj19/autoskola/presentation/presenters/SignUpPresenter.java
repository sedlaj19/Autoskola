package cz.sedlaj19.autoskola.presentation.presenters;

import cz.sedlaj19.autoskola.presentation.presenters.base.BasePresenter;
import cz.sedlaj19.autoskola.presentation.ui.views.BaseView;

/**
 * Created by Honza on 28. 7. 2016.
 */
public interface SignUpPresenter extends BasePresenter {

    interface View extends BaseView{

        void onSignUpSuccessful();

    }

    void doSignUp(String email, String password, String firstname, String surname, String phone,
                  boolean instructor, String instructorPassword, String deviceId);

}
