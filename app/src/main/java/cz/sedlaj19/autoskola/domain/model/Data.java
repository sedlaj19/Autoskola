package cz.sedlaj19.autoskola.domain.model;

/**
 * Created by Honza on 23. 2. 2017.
 */

public class Data {

    private int notificationId;
    private long date;

    public Data(int notificationId, long date){
        this.notificationId = notificationId;
        this.date = date;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
