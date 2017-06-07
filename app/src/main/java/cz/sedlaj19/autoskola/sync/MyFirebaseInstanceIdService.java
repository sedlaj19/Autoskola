package cz.sedlaj19.autoskola.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.executor.MainThread;
import cz.sedlaj19.autoskola.domain.executor.impl.ThreadExecutor;
import cz.sedlaj19.autoskola.domain.interactors.GetUserByDeviceInteractor;
import cz.sedlaj19.autoskola.domain.interactors.UpdateUserInteractor;
import cz.sedlaj19.autoskola.domain.interactors.impl.GetUserByDeviceInteractorImpl;
import cz.sedlaj19.autoskola.domain.interactors.impl.UpdateUserInteractorImpl;
import cz.sedlaj19.autoskola.storage.model.User;
import cz.sedlaj19.autoskola.threading.MainThreadImpl;
import cz.sedlaj19.autoskola.utils.SharedPrefHelper;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService implements
        GetUserByDeviceInteractor.Callback,
        UpdateUserInteractor.Callback{

    public MyFirebaseInstanceIdService() {
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(this.getClass().toString(), "TEST: Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);

    }

    private void sendRegistrationToServer(String token){
        // TODO: je potreba to ulozit do firebase databaze
        Log.d(this.getClass().toString(), "TEST: ukladani do db - " + token);
        String oldToken = SharedPrefHelper.getDeviceId(getApplicationContext());
        SharedPrefHelper.saveDeviceId(getApplicationContext(), token);
        if(oldToken != null){
            getUser(oldToken);
        }else{
            // TODO: udelat update pri prihlaseni, je to vlastne to samy, ale je potreba si nekde
            // ulozit, ze po prihlaseni je potreba zmena... Nebo to kontrolovat tak, ze ziskam
            // usera po prihlaseni a porovnam deviceId, pokud budou sedet, tak nic, pokud ne, tak
            // updatnu usera s novym tokenem...
        }
    }

    private void getUser(final String oldToken){
        GetUserByDeviceInteractor interactor = new GetUserByDeviceInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                oldToken,
                this
        );
        interactor.execute();
    }

    @Override
    public void onUserRetrieved(cz.sedlaj19.autoskola.domain.model.User user) {
        String newToken = SharedPrefHelper.getDeviceId(getApplicationContext());
        user.setDeviceId(newToken);
        UpdateUserInteractor interactor = new UpdateUserInteractorImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                user,
                this
        );
        interactor.execute();
    }

    @Override
    public void onUserUpdated() {
        // TODO: neco udelat?
    }
}
