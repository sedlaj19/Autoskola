package cz.sedlaj19.autoskola.presentation.presenters;

import java.util.List;

import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.presentation.presenters.base.BasePresenter;
import cz.sedlaj19.autoskola.presentation.ui.BaseView;

/**
 * Created by Honza on 28. 7. 2016.
 */
public interface DashboardPresenter extends BasePresenter {

    interface View extends BaseView{

        void onLogoutFinished();
        void onUserRidesRetrieved();
        void onInstructorRetrieved();

    }

    void doLogout();
    void getUserRides();
    void getInstructors();

}
