package cz.sedlaj19.autoskola.domain.interactors.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;
import cz.sedlaj19.autoskola.domain.interactors.CreateCarInteractor;
import cz.sedlaj19.autoskola.domain.model.Car;

/**
 * Created by Honza on 25. 1. 2017.
 */

public class CreateCarInteractorImpl extends AbstractInteractor implements
        CreateCarInteractor,
        OnCompleteListener<Void>{

    private Car car;
    private Callback callback;
    private DatabaseReference database;

    public CreateCarInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                   Car car, Callback callback) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.car = car;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void run() {
        String key = database.child(Constants.FirebaseModels.CARS).push().getKey();
        car.setId(key);
        database.child(Constants.FirebaseModels.CARS).child(key).setValue(car).addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        callback.onCarCreated(task, car);
    }
}
