package cz.sedlaj19.autoskola.utils;

import android.content.Context;
import android.content.SharedPreferences;

import cz.sedlaj19.autoskola.Constants;

/**
 * Created by Honza on 22. 1. 2017.
 */

public class SharedPrefHelper {

    public static final String SHARED_PREFERENCES = "sedlaj19.autoskola";

    public static void saveDeviceId(Context context, String deviceId){
        getSharedPreferencesEditor(context)
                .putString(Constants.SharedPreferences.KEY_DEVICE_ID, deviceId).commit();
    }

    public static String getDeviceId(Context context){
        return getSharedPreferences(context).getString(Constants.SharedPreferences.KEY_DEVICE_ID, null);
    }

    private static SharedPreferences.Editor getSharedPreferencesEditor(Context context){
        return getSharedPreferences(context).edit();
    }

    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

}
