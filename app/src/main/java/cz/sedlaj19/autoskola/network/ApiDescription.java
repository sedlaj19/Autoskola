package cz.sedlaj19.autoskola.network;

import cz.sedlaj19.autoskola.domain.request.NotificationRequest;
import cz.sedlaj19.autoskola.domain.response.NotificationResponse;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Honza on 22. 1. 2017.
 */

public interface ApiDescription {

    @POST("fcm/send")
    Observable<Response<NotificationResponse>> sendNotification(@Header("Authorization") String auth,
                                                                @Body NotificationRequest request);

}
