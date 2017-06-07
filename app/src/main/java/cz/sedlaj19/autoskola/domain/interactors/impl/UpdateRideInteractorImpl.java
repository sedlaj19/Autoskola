package cz.sedlaj19.autoskola.domain.interactors.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.executor.Executor;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.interactors.UpdateRideInteractor;
import cz.sedlaj19.autoskola.domain.interactors.base.AbstractInteractor;
import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.storage.converter.RideConverter;

/**
 * Created by Honza on 13. 8. 2016.
 */
public class UpdateRideInteractorImpl extends AbstractInteractor implements UpdateRideInteractor,
        OnCompleteListener<Void>{

    private Callback callback;
    private Ride ride;
    private DatabaseReference database;

    public UpdateRideInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                    Callback callback, Ride ride) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.ride = ride;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void run() {
        Log.d(this.getClass().toString(), "Zkousim updatovat ... " + ride.getId());
        cz.sedlaj19.autoskola.storage.model.Ride rideToStore = RideConverter.convertToStrorageModel(this.ride);
        Map<String, Object> rideValues = rideToStore.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + Constants.FirebaseModels.RIDES + "/" + ride.getId(), rideValues);
        database.updateChildren(childUpdates).addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        Log.d(this.getClass().toString(), "Updated");
        callback.onRideUpdated(ride);
    }
}
