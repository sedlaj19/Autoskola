package cz.sedlaj19.autoskola.presentation.presenters;

import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.presentation.presenters.base.BasePresenter;
import cz.sedlaj19.autoskola.presentation.ui.views.BaseView;

/**
 * Created by Honza on 25. 1. 2017.
 */

public interface AddStudentPresenter extends BasePresenter {

    interface View extends BaseView{

        void onStudentCreated(User user);

    }

    void createUser(String name, String surname, String email, String phone);
    boolean checkName(String name);
    boolean checkEmail(String email);
    boolean checkPhone(String phone);

}
