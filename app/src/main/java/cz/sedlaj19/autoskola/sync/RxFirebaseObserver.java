package cz.sedlaj19.autoskola.sync;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cz.sedlaj19.autoskola.domain.model.Ride;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Honza on 29. 1. 2017.
 */

public class RxFirebaseObserver {

    private static final String TAG = RxFirebaseObserver.class.toString();

    @NonNull
    public static Observable<DataSnapshot> observe(final Query query) {
        return Observable.create(subscriber -> {
            ValueEventListener listener = query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(dataSnapshot);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(new RuntimeException(databaseError.getMessage()));
                    }
                }
            });
            subscriber.add(Subscriptions.create(() -> query.removeEventListener(listener)));
        });
    }

    public static Observable<DataSnapshot> observeEqualTo(final Query query, String key, String value){
        return Observable.create(subscriber -> {
            ValueEventListener listener = query.orderByChild(key).equalTo(value).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(!subscriber.isUnsubscribed()){
                        subscriber.onNext(dataSnapshot);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    if(!subscriber.isUnsubscribed()){
                        subscriber.onError(new RuntimeException(databaseError.getMessage()));
                    }
                }
            });
            subscriber.add(Subscriptions.create(() -> query.removeEventListener(listener)));
        });
    }

    public static Observable<Boolean> delete(DatabaseReference reference) {
        return Observable.create(subscriber -> {
            if (!subscriber.isUnsubscribed()) {
                reference.removeValue().addOnCompleteListener(task -> {
                    subscriber.onNext(true);
                });
            }
        });
    }

    public static Observable<Boolean> update(DatabaseReference database, String key,
                                          String path, Map<String, Object> values) {
        return Observable.create(subscriber -> {
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/" + path + "/" + key, values);
            if(!subscriber.isUnsubscribed()){
                database.updateChildren(childUpdates).addOnCompleteListener(task -> {
                    subscriber.onNext(true);
                });
            }
        });
    }
}
