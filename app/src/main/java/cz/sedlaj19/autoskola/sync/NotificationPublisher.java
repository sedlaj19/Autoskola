package cz.sedlaj19.autoskola.sync;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cz.sedlaj19.autoskola.Constants;

/**
 * Created by Honza on 23. 2. 2017.
 */

public class NotificationPublisher extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Notification notification = intent.getParcelableExtra(Constants.Notification.KEY_NOTIFICATION);
        int id = intent.getIntExtra(Constants.Notification.KEY_NOTIFICATION_ID, 0);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);
    }
}
