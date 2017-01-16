package cz.sedlaj19.autoskola.domain.interactors;

import java.util.List;

import cz.sedlaj19.autoskola.domain.interactors.base.Interactor;

/**
 * Created by Honza on 7. 8. 2016.
 */
public interface GetStudentsNamesInteractor extends Interactor {

    interface Callback{

        void onStudentsNamesRetrieved(List<String> names);

    }

}
