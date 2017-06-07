package cz.sedlaj19.autoskola.sync;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.model.Car;
import cz.sedlaj19.autoskola.domain.model.Website;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.presentation.ui.views.GetCarsNamesView;
import cz.sedlaj19.autoskola.presentation.ui.views.GetCarsView;
import cz.sedlaj19.autoskola.presentation.ui.views.WebsiteView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Honza on 29. 1. 2017.
 */

public class DatabaseHelper {

    private static final String TAG = DatabaseHelper.class.toString();

    // TODO: vyresit promenny Subscription abych mohl unsub a pak taky errory a succesy

    public static void getCars(DatabaseReference database, GetCarsView view){
        // TODO: tady by to dokonce mohlo delat problem ze to neni volano z main thread vlakna ...
        RxFirebaseObserver.observe(database.child(Constants.FirebaseModels.CARS))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataSnapshot -> {
                    List<Car> cars = new ArrayList<>();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        cars.add(snapshot.getValue(Car.class));
                    }
                    Container.getInstance().setCars(cars);
                    view.onCarsRetrieved(cars);
                }, DatabaseHelper::onError);
    }

    public static void getCarsNames(DatabaseReference database, GetCarsNamesView view){
        RxFirebaseObserver.observe(database.child(Constants.FirebaseModels.CARS))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataSnapshot -> {
                    List<String> cars = new ArrayList<>();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        cars.add(snapshot.getValue(Car.class).getName());
                    }
                    view.onCarsRetrieved(cars);
                }, DatabaseHelper::onError);
    }

    public static void getWebsites(DatabaseReference database, WebsiteView view){
        RxFirebaseObserver.observe(database.child(Constants.FirebaseModels.WEBSITES))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataSnapshot -> {
                    Website website = dataSnapshot.getValue(Website.class);
                    String url = "";
                    if(website != null){
                        url = website.getUrl();
                    }
                    view.onWebsitesRetrieved(url);
                }, DatabaseHelper::onError);
    }

    public static void onError(Throwable throwable){
        Log.d(TAG, "ERROR", throwable);
    }

}
