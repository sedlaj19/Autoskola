package cz.sedlaj19.autoskola;

import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Honza on 28. 7. 2016.
 */
public class Constants {

    public class Login{
        public static final int LOGIN_ERROR_TOAST = 0;
        public static final int LOGIN_ERROR_EMAIL = 1;
        public static final int LOGIN_ERROR_PASSWORD = 2;
    }

    public class SignUp{
        public static final int SIGN_UP_ERROR_TOAST = 0;
        public static final int SIGN_UP_ERROR_EMAIL = 1;
        public static final int SIGN_UP_ERROR_PASSWORD = 2;
        public static final int SIGN_UP_ERROR_INSTR_PASSWORD = 3;

        public static final String SIGNU_UP_INSTRUCTOR_PASSWORD = "1234";
    }

    public class Common{
        public static final String LOGGED_USER = "loggedUser";
        public static final String RIDE_TO_EDIT = "rideToEdit";
    }

    public class FirebaseModels{
        public static final String USERS = "users";
        public static final String RIDES = "rides";
        public static final String CARS = "cars";
        public static final String WEBSITES = "websites";

        public static final String KEY_INSTRUCTOR = "instructor";
        public static final String KEY_STUDENT = "student";
    }

    public class AddRide{
        public static final String DATE_PICKER_FRAGMENT_TAG = "datePickerFragment";
        public static final String TIME_PICKER_FRAGMENT_TAG = "timePickerFragment";
    }

    public class Instructor{
        public static final String STUDENT_DIALOG_FRAGMENT_TAG = "studentDialogFragment";

        public static final int TAB_RIDES = 0;
        public static final int TAB_STUDENTS = 1;
    }

    public class Notification{
        public static final int PENDING_INTENT_CODE = 100;

        public static final int NOTIFICATION_DELAY_DAY = 86400000;

        public static final String KEY_NOTIFICATION_ID = "notification_id";
        public static final String KEY_NOTIFICATION = "notification";

        public static final String DATA_KEY_NOTIFICATION_ID = "notificationId";
        public static final String DATA_KEY_NOTIFICATION_DATE = "date";

        // TODO: odtud by to melo zmizet asi jinam pak no ...
        public static final String AUTHORIZATION_TOKEN = "key=AIzaSyCjO2_RlTjNkduy1kE1BYVd0XbX9Oe8Njg";
    }

    public class SharedPreferences{
        public static final String KEY_DEVICE_ID = "deviceId";
    }

    public class Network{
        public static final String BASE_URL = "https://fcm.googleapis.com/";
    }

    public class ItemSwipe{
        public static final int SWIPE_DELETE = ItemTouchHelper.LEFT;
        public static final int SWIPE_UPDATE = ItemTouchHelper.RIGHT;
    }
}
