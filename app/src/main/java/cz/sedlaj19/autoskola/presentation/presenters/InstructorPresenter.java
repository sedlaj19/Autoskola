package cz.sedlaj19.autoskola.presentation.presenters;

import cz.sedlaj19.autoskola.presentation.presenters.base.BasePresenter;
import cz.sedlaj19.autoskola.presentation.ui.views.BaseView;
import cz.sedlaj19.autoskola.presentation.ui.views.GetCarsView;
import cz.sedlaj19.autoskola.presentation.ui.views.WebsiteView;

/**
 * Created by Honza on 7. 8. 2016.
 */
public interface InstructorPresenter extends WebsitePresenter {

    interface View extends WebsiteView {

        void onLogoutFinished();
//        void onStudentsRetrieved();
        void onInstructorsRetrieved();

    }

    void doLogout();
//    void getAllStudents();
    void getAllInstructors();
    void getCars();
    void changeWebsite(String url);

}
