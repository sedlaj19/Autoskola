package cz.sedlaj19.autoskola.presentation.presenters.impl;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.interactors.CreateCarInteractor;
import cz.sedlaj19.autoskola.domain.interactors.impl.CreateCarInteractorImpl;
import cz.sedlaj19.autoskola.domain.model.Car;
import cz.sedlaj19.autoskola.presentation.presenters.base.AbstractPresenter;
import cz.sedlaj19.autoskola.presentation.presenters.ManageCarsPresenter;
import cz.sedlaj19.autoskola.sync.DatabaseHelper;
import cz.sedlaj19.autoskola.sync.RxFirebaseObserver;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by Honza on 25. 1. 2017.
 */

public class ManageCarsPresenterImpl extends AbstractPresenter implements
        ManageCarsPresenter,
        CreateCarInteractor.Callback{

    private View view;
    private DatabaseReference database;

    public ManageCarsPresenterImpl(Executor executor, MainThread mainThread, View view) {
        super(executor, mainThread);
        this.view = view;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void getCars() {
        DatabaseHelper.getCars(database, view);
    }

    @Override
    public void saveCar(String name) {
        CreateCarInteractor interactor = new CreateCarInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                new Car(name),
                this);
        interactor.execute();
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
    public void onCarCreated(Task<Void> result, Car car) {
        Log.d(this.getClass().toString(), "TEST: car created ...");
    }
}
