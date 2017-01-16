package cz.sedlaj19.autoskola.domain.interactors;

import java.util.List;

import cz.sedlaj19.autoskola.domain.interactors.base.Interactor;

/**
 * Created by Honza on 8. 8. 2016.
 */
public interface GetInstructorsNamesInteractor extends Interactor {

    interface Callback{

        void onNamesRetrieved(List<String> names);

    }
}
