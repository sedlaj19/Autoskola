package cz.sedlaj19.autoskola.sync;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.IntegerRes;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.DateFormat;
import java.util.Map;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.presentation.ui.activities.MainActivity;
import is.stokkur.dateutils.DateUtils;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = MyFirebaseMessagingService.class.toString();

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "TEST: From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "TEST: Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "TEST: Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getData());
        }

    }

    private void sendNotification(String messageBody, Map<String, String> data){
        Notification notification = createNotification(messageBody);
        int id = Integer.valueOf(data.get(Constants.Notification.DATA_KEY_NOTIFICATION_ID));
        long date = Long.valueOf(data.get(Constants.Notification.DATA_KEY_NOTIFICATION_DATE));
        scheduleLocalNotification(date, id);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.Notification.PENDING_INTENT_CODE, notification);
    }

    private void scheduleLocalNotification(long date, int id){
        Notification notification2 = createNotification("Scheduled ride tomorrow at " + DateUtils.formatDateByPattern(date, DateUtils.PATTERN_HHmm));
        Intent notificationIntent = new Intent(getApplicationContext(), NotificationPublisher.class);
        notificationIntent.putExtra(Constants.Notification.KEY_NOTIFICATION_ID, id);
        notificationIntent.putExtra(Constants.Notification.KEY_NOTIFICATION, notification2);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), id, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        manager.setExact(AlarmManager.RTC_WAKEUP, date + Constants.Notification.NOTIFICATION_DELAY_DAY, pendingIntent);
//        manager.setExact(AlarmManager.RTC_WAKEUP, date + 20000, pendingIntent);
    }

    private Notification createNotification(String messageBody){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, Constants.Notification.PENDING_INTENT_CODE, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_add_circle_outline_white_48dp)
                .setContentTitle("Autoskola3S")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        return builder.build();
    }
}
