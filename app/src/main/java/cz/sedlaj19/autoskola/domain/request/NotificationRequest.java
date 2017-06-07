package cz.sedlaj19.autoskola.domain.request;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import cz.sedlaj19.autoskola.Constants;
import cz.sedlaj19.autoskola.domain.model.Data;
import cz.sedlaj19.autoskola.domain.model.Notification;

/**
 * Created by Honza on 22. 1. 2017.
 */

public class NotificationRequest {

    private String to;
    private Notification notification;
    private Data data;

    public NotificationRequest(String to, Notification notification, Data data){
        this.to = to;
        this.notification = notification;
        this.data = data;
    }

    public NotificationRequest(String to, Notification notification, int id, long date){
        this.to = to;
        this.notification = notification;
        this.data = new Data(id, date);
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
