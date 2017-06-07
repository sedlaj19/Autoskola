package cz.sedlaj19.autoskola.presentation.presenters;

import cz.sedlaj19.autoskola.presentation.presenters.base.BasePresenter;

/**
 * Created by Honza on 15. 2. 2017.
 */

public interface ItemSwipePresenter extends BasePresenter{

    void onSwiped(int direction);
    void handleSwipeAction(Object o, int direction);

}
