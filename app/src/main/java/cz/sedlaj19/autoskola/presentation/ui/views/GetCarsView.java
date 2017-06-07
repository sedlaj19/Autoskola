package cz.sedlaj19.autoskola.presentation.ui.views;

import java.util.List;

import cz.sedlaj19.autoskola.domain.model.Car;

/**
 * Created by Honza on 29. 1. 2017.
 */

public interface GetCarsView extends BaseView{

    void onCarsRetrieved(List<Car> cars);

}
