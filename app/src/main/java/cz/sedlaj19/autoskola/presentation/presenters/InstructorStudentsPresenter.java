package cz.sedlaj19.autoskola.presentation.presenters;

import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.presentation.presenters.base.BasePresenter;

/**
 * Created by Honza on 12. 8. 2016.
 */
public interface InstructorStudentsPresenter extends BasePresenter {

    interface View{

        void onStudentsRetrieved();
        void onStudentClicked(User user);

    }

    void getStudents();

}
