package cz.sedlaj19.autoskola.domain.interactors;

import cz.sedlaj19.autoskola.domain.interactors.base.Interactor;

/**
 * Created by Honza on 8. 8. 2016.
 */
public interface GetAllInstructorsInteractor extends Interactor {

    interface Callback{

        void onInstructorsRetrieved();

    }
}
