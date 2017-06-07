package cz.sedlaj19.autoskola.domain.model;

/**
 * Created by Honza on 22. 1. 2017.
 */

public class Notification {

    private String body;
    private String title;

    public Notification(String body, String title){
        this.body = body;
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
