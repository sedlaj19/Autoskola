package cz.sedlaj19.autoskola.presentation.presenters;


import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.presentation.presenters.base.BasePresenter;
import cz.sedlaj19.autoskola.presentation.ui.BaseView;

public interface MainPresenter extends BasePresenter {

    interface View extends BaseView {

        void onUserRetrieved(User user);
        void onLoginSuccessful(String email);

    }

    void doLogin(String username, String password);
    void getUserByEmail(String email);
}
