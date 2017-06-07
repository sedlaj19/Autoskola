package cz.sedlaj19.autoskola.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import cz.sedlaj19.autoskola.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Honza on 22. 1. 2017.
 */

public class Client {

    public static ApiDescription getApiClient(){
        return new Retrofit.Builder()
                .baseUrl(Constants.Network.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .build()
                .create(ApiDescription.class);
    }

    private static OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }
}
