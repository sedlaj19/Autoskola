package cz.sedlaj19.autoskola.presentation.ui.views;

import java.util.List;

/**
 * Created by Honza on 29. 1. 2017.
 */

public interface GetCarsNamesView extends BaseView{

    void onCarsRetrieved(List<String> names);

}
