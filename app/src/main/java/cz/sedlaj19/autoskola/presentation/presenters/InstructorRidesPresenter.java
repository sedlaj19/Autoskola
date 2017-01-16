package cz.sedlaj19.autoskola.presentation.presenters;

import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.presentation.presenters.base.BasePresenter;

/**
 * Created by Honza on 12. 8. 2016.
 */
public interface InstructorRidesPresenter extends BasePresenter {

    interface View{

        void onInstructorRidesRetrieved();
        void onRideClicked(Ride ride);
        void onRideChanged(int position);

    }

    void getInstructorsRides();

}
