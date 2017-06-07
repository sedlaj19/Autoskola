package cz.sedlaj19.autoskola.sync;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.storage.converter.RideConverter;
import rx.Observable;

/**
 * Created by Honza on 13. 2. 2017.
 */

public class FilterHelper {

    public static Observable<List<Ride>> filterRides(DataSnapshot dataSnapshot){
        return Observable.create(subscriber -> {
            List<Ride> rides = new ArrayList<>();
            for(DataSnapshot data : dataSnapshot.getChildren()){
                cz.sedlaj19.autoskola.storage.model.Ride ride = data.getValue(cz.sedlaj19.autoskola.storage.model.Ride.class);
                if(ride.isCompleted()){
                    continue;
                }
                Ride filteredRide = RideConverter.convertToDomainModel(ride);
                filteredRide.setId(data.getKey());
                rides.add(filteredRide);
            }
            if(!subscriber.isUnsubscribed()){
                subscriber.onNext(rides);
            }
        });
    }

}
