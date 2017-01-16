package cz.sedlaj19.autoskola.domain.interactors.impl;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.interactors.CreateRideInteractor;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;
import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.storage.converter.RideConverter;

/**
 * Created by Honza on 7. 8. 2016.
 */
public class CreateRideInteractorImpl extends AbstractInteractor implements CreateRideInteractor,
        OnCompleteListener<Void>{

    private DatabaseReference database;
    private Callback callback;
    private Ride ride;

    public CreateRideInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                    Callback callback, Ride ride) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.ride = ride;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void run() {
        cz.sedlaj19.autoskola.storage.model.Ride rideToStore = RideConverter.convertToStrorageModel(this.ride);
        String key = database.child(Constants.FirebaseModels.RIDES).push().getKey();
        ride.setId(key);
        database.child(Constants.FirebaseModels.RIDES).child(key).setValue(rideToStore).addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        callback.onRideCreated(task, ride);
    }
}
