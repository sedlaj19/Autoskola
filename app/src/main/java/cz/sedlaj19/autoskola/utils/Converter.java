package cz.sedlaj19.autoskola.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Honza on 8. 8. 2016.
 */
public class Converter {

    public static String convertDateToString(Date date){
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String convertDateToShortString(Date date){
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String convertTimeToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(date);
    }

    public static String convertDateAndTimeToString(Date date){
        String d = convertDateToString(date);
        String time = convertTimeToString(date);
        return time + " " + d;
    }

}
