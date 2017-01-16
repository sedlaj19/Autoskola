package cz.sedlaj19.autoskola.presentation.presenters;

import cz.sedlaj19.autoskola.presentation.presenters.base.BasePresenter;
import cz.sedlaj19.autoskola.presentation.ui.BaseView;

/**
 * Created by Honza on 7. 8. 2016.
 */
public interface InstructorPresenter extends BasePresenter {

    interface View extends BaseView{

        void onLogoutFinished();
//        void onStudentsRetrieved();
        void onInstructorsRetrieved();

    }

    void doLogout();
//    void getAllStudents();
    void getAllInstructors();

}
