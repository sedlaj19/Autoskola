package cz.sedlaj19.autoskola.presentation.presenters;

import android.support.v7.widget.RecyclerView;

import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.presentation.presenters.base.BasePresenter;
import cz.sedlaj19.autoskola.presentation.ui.views.ItemSwipeView;

/**
 * Created by Honza on 12. 8. 2016.
 */
public interface InstructorRidesPresenter extends ItemSwipePresenter {

    interface View extends ItemSwipeView{

        void onInstructorRidesRetrieved();
        void onRideClicked(Ride ride);
        void onRideChanged(int position);
        void onRideDeleted(boolean result);
        void removeRow(int position);

    }

    void getInstructorsRides();

}
