package cz.sedlaj19.autoskola.presentation.presenters;

import java.util.List;

import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.presentation.presenters.base.BasePresenter;
import cz.sedlaj19.autoskola.presentation.ui.views.BaseView;
import cz.sedlaj19.autoskola.presentation.ui.views.GetCarsNamesView;
import cz.sedlaj19.autoskola.presentation.ui.views.GetCarsView;

/**
 * Created by Honza on 7. 8. 2016.
 */
public interface AddRidePresenter extends BasePresenter {

    interface View extends GetCarsNamesView {

        void onStudentsNamesRetrieved(List<String> names);
        void onInstructorsNamesRetrieved(List<String> names);
        void onRideCreated(Ride ride);
        void onRideUpdated(int position);
        void onRidesRetrieved(List<Ride> rides);

    }

    void getStudentsNames();
    void getInstructorsNames();
    void getCars();
    void createRide(long date, String car, String instructor, String student, String key, String notes);
    void getDayRides(long date);
}
