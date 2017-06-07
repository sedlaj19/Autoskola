package cz.sedlaj19.autoskola.presentation.presenters;

import java.util.List;

import cz.sedlaj19.autoskola.domain.model.Car;
import cz.sedlaj19.autoskola.presentation.presenters.base.BasePresenter;
import cz.sedlaj19.autoskola.presentation.ui.views.BaseView;
import cz.sedlaj19.autoskola.presentation.ui.views.GetCarsView;

/**
 * Created by Honza on 25. 1. 2017.
 */

public interface ManageCarsPresenter extends BasePresenter {

    interface View extends GetCarsView {

        void onCarSaved(Car car);

    }

    void getCars();
    void saveCar(String name);

}
