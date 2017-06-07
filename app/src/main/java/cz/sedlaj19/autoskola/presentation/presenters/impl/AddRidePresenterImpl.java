package cz.sedlaj19.autoskola.presentation.presenters.impl;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.interactors.CreateRideInteractor;
import cz.sedlaj19.autoskola.domain.interactors.GetInstructorsNamesInteractor;
import cz.sedlaj19.autoskola.domain.interactors.GetRidesByDayInteractor;
import cz.sedlaj19.autoskola.domain.interactors.GetStudentsNamesInteractor;
import cz.sedlaj19.autoskola.domain.interactors.GetUserByNameAndSurnameInteractor;
import cz.sedlaj19.autoskola.domain.interactors.UpdateRideInteractor;
import cz.sedlaj19.autoskola.domain.interactors.impl.CreateRideInteractorImpl;
import cz.sedlaj19.autoskola.domain.interactors.impl.GetInstructorsNamesInteractorImpl;
import cz.sedlaj19.autoskola.domain.interactors.impl.GetRidesByDayInteractorImpl;
import cz.sedlaj19.autoskola.domain.interactors.impl.GetStudentsNamesInteractorImpl;
import cz.sedlaj19.autoskola.domain.interactors.impl.GetUserByNameAndSurnameInteractorImpl;
import cz.sedlaj19.autoskola.domain.interactors.impl.UpdateRideInteractorImpl;
import cz.sedlaj19.autoskola.domain.model.Notification;
import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.domain.request.NotificationRequest;
import cz.sedlaj19.autoskola.domain.response.NotificationResponse;
import cz.sedlaj19.autoskola.network.Client;
import cz.sedlaj19.autoskola.presentation.presenters.AddRidePresenter;
import cz.sedlaj19.autoskola.presentation.presenters.base.AbstractPresenter;
import cz.sedlaj19.autoskola.sync.DatabaseHelper;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Honza on 7. 8. 2016.
 */
public class AddRidePresenterImpl extends AbstractPresenter implements AddRidePresenter,
        GetStudentsNamesInteractor.Callback, CreateRideInteractor.Callback,
        GetInstructorsNamesInteractor.Callback, GetUserByNameAndSurnameInteractor.Callback,
        UpdateRideInteractor.Callback, GetRidesByDayInteractor.Callback{

    private AddRidePresenter.View view;
    private Ride createdRide;
    private boolean isUpdate;
    private String key;
    private User user;
    private DatabaseReference database;

    public AddRidePresenterImpl(Executor executor, MainThread mainThread, View view) {
        super(executor, mainThread);
        this.view = view;
        this.isUpdate = false;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void start() {

    }

    @Override
    public void resume() {

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
    public void getStudentsNames() {
        GetStudentsNamesInteractor interactor = new GetStudentsNamesInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
        interactor.execute();
    }

    @Override
    public void getInstructorsNames() {
        GetInstructorsNamesInteractor interactor = new GetInstructorsNamesInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
        interactor.execute();
    }

    @Override
    public void getCars() {
        DatabaseHelper.getCarsNames(database, view);
    }

    @Override
    public void createRide(long date, String car, String instructor, String student,
                           String key, String notes) {
        String carKey = Container.getInstance().getCarKeyByName(car);
        createdRide = new Ride(carKey, date, Container.getInstance().getLoggedInUser().getId(),
                                null, notes);
        this.key = key;
        this.isUpdate = key != null;
        String name = student.substring(0, student.indexOf(" "));
        String surname = student.substring(student.indexOf(" ")+1, student.length());
        GetUserByNameAndSurnameInteractor interactor = new GetUserByNameAndSurnameInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                name,
                surname
        );
        interactor.execute();
    }

    @Override
    public void getDayRides(long date) {
        GetRidesByDayInteractor interactor = new GetRidesByDayInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                date
        );
        interactor.execute();
    }

    @Override
    public void onStudentsNamesRetrieved(List<String> names) {
        view.onStudentsNamesRetrieved(names);
    }

    @Override
    public void onNamesRetrieved(List<String> names) {
        view.onInstructorsNamesRetrieved(names);
    }

    @Override
    public void onRideCreated(Task<Void> result, Ride ride) {
        view.hideProgress();
        if(!result.isSuccessful()){
            // TODO: pridat proc ...
            view.showError("Ride was not created.", Constants.Login.LOGIN_ERROR_TOAST);
            return;
        }
        Container container = Container.getInstance();
        container.addRide(ride);
        container.setRideChange(true);
        sendNotification("You have a new ride", ride.getDate(), ride.getUniqueId(), ride.getDateMillis());
        view.onRideCreated(ride);
    }

    @Override
    public void onUserRetrieved(User user) {
        this.user = user;
        createdRide.setStudent(user.getId());
        if(isUpdate){
            createdRide.setId(key);
            UpdateRideInteractor interactor = new UpdateRideInteractorImpl(
                    ThreadExecutor.getInstance(),
                    MainThreadImpl.getInstance(),
                    this,
                    createdRide
            );
            interactor.execute();
        }else {
            CreateRideInteractor interactor = new CreateRideInteractorImpl(
                    ThreadExecutor.getInstance(),
                    MainThreadImpl.getInstance(),
                    this,
                    createdRide
            );
            interactor.execute();
        }
    }

    @Override
    public void onRideUpdated(Ride ride) {
        Container container = Container.getInstance();
        int position = container.updateRide(ride);
        container.setRideChange(true);
        container.setUpdatedRidePosition(position);
        sendNotification("Ride updated", ride.getDate(), ride.getUniqueId(), ride.getDateMillis());
        view.onRideCreated(ride);
    }

    @Override
    public void onRidesRetrieved(List<Ride> rides) {
        view.onRidesRetrieved(rides);
    }

    private void sendNotification(String title, String body, int id, long date){
        Client.getApiClient().sendNotification(Constants.Notification.AUTHORIZATION_TOKEN,
                new NotificationRequest(user.getDeviceId(), new Notification(body, title), id, date))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSendNotificationSuccess, this::onError);
    }

    private void onError(Throwable throwable){
        Log.d(this.getClass().toString(), "TEST: error ...");
        throwable.printStackTrace();
    }

    private void onSendNotificationSuccess(Response<NotificationResponse> response){
        Log.d(this.getClass().toString(), "TEST: povedlo se " + response.code());
        if(response.body() != null){
            Log.d(this.getClass().toString(), "TEST: " + response.body().isSuccess());
        }
    }
}
