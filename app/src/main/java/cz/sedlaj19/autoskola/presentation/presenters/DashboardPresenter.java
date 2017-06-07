package cz.sedlaj19.autoskola.presentation.presenters;

import cz.sedlaj19.autoskola.presentation.presenters.base.BasePresenter;
import cz.sedlaj19.autoskola.presentation.ui.views.BaseView;
import cz.sedlaj19.autoskola.presentation.ui.views.GetCarsView;
import cz.sedlaj19.autoskola.presentation.ui.views.WebsiteView;

/**
 * Created by Honza on 28. 7. 2016.
 */
public interface DashboardPresenter extends WebsitePresenter {

    interface View extends WebsiteView {

        void onLogoutFinished();
        void onUserRidesRetrieved();
        void onInstructorRetrieved();

    }

    void doLogout();
    void getUserRides();
    void getCars();
    void getInstructors();
    void carsRetrieved(boolean retrieved);
    String checkUrl(String url);

}
