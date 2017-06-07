package cz.sedlaj19.autoskola.presentation.presenters.impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.interactors.GetInstructorsRidesInteractor;
import cz.sedlaj19.autoskola.domain.interactors.impl.GetInstructorsRidesInteractorImpl;
import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.presentation.presenters.InstructorRidesPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.base.AbstractPresenter;
import cz.sedlaj19.autoskola.storage.converter.RideConverter;
import cz.sedlaj19.autoskola.sync.FilterHelper;
import cz.sedlaj19.autoskola.sync.RxFirebaseObserver;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Honza on 12. 8. 2016.
 */
public class InstructorsRidePresenterImpl extends AbstractPresenter implements
        InstructorRidesPresenter,
        GetInstructorsRidesInteractor.Callback{

    private InstructorRidesPresenter.View view;
    private DatabaseReference database;
    private Subscription rideSubscription;
    private Subscription filterSubscription;

    public InstructorsRidePresenterImpl(Executor executor, MainThread mainThread,
                                        View view) {
        super(executor, mainThread);
        this.view = view;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void getInstructorsRides() {
        rideSubscription = RxFirebaseObserver.observeEqualTo(
                database.child(Constants.FirebaseModels.RIDES),
                "instructor",
                Container.getInstance().getLoggedInUser().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRidesRetrieved, this::onError);
    }

    @Override
    public void onSwiped(int direction) {
        int text = R.string.ride_finished;
        if(direction == Constants.ItemSwipe.SWIPE_DELETE){
            text = R.string.ride_deleted;
        }else if(direction == Constants.ItemSwipe.SWIPE_UPDATE){

        }
        view.showSnackBar(text, direction);
    }

    @Override
    public void handleSwipeAction(Object o, int direction) {
        Ride ride;
        Log.d(this.getClass().toString(), "TEST: jsem tady, mel bych mazat ... " + (o == null));
        if(o instanceof Ride) {
            ride = (Ride) o;
        }else {
            return;
        }
        Log.d(this.getClass().toString(), "TEST: mazu? " + direction + " | " + ride.getId());
        if(direction == Constants.ItemSwipe.SWIPE_DELETE){
            // TODO: add everywhere subscription variables, so no memory leaks occur
            RxFirebaseObserver.delete(database.child(Constants.FirebaseModels.RIDES).child(ride.getId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onRideDeleted, this::onError);
        }else{
            ride.setCompleted(true);
            cz.sedlaj19.autoskola.storage.model.Ride rideToUpdate = RideConverter.convertToStrorageModel(ride);
            RxFirebaseObserver.update(database, ride.getId(), Constants.FirebaseModels.RIDES, rideToUpdate.toMap())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onRideUpdated, this::onError);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void resume() {
        getInstructorsRides();
        Container container = Container.getInstance();
        if(container.isRideChange()){
            view.onRideChanged(container.getUpdatedRidePosition());
            container.setRideChange(false);
            container.setUpdatedRidePosition(-1);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onRidesRetrieved() {
        view.onInstructorRidesRetrieved();
    }

    private void onRideDeleted(Boolean response){
        Log.d(this.getClass().toString(), "TEST: tak jsem tady pane ...");
        view.onRideDeleted(response);
    }

    private void onRideUpdated(Boolean response){
        Log.d(this.getClass().toString(), "TEST: jizda pozmenena ...");
    }

    private void onRidesRetrieved(DataSnapshot rides){
        unsubscribe(rideSubscription);
        filterSubscription = FilterHelper.filterRides(rides)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRidesFiltered, this::onError);
    }

    private void onRidesFiltered(List<Ride> rides){
        Container.getInstance().clearRides();
        Container.getInstance().setRides(rides);
        view.onInstructorRidesRetrieved();
    }

    private void onError(Throwable throwable){
        Log.e(this.getClass().toString(), "ERROR", throwable);
    }

    private void unsubscribe(Subscription subscription){
        if(subscription != null){
            subscription.unsubscribe();
        }
    }
}
