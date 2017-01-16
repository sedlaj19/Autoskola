package cz.sedlaj19.autoskola.domain.interactors;

import java.util.List;

import cz.sedlaj19.autoskola.domain.interactors.base.Interactor;
import cz.sedlaj19.autoskola.domain.model.User;

/**
 * Created by Honza on 7. 8. 2016.
 */
public interface GetAllStudentsInteractor extends Interactor {

    interface Callback{

        void onStudentsRetrieved();

    }

}
