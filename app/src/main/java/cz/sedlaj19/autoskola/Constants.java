package cz.sedlaj19.autoskola;

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
        public static final String USERS = "Users";
        public static final String RIDES = "Rides";
    }

    public class AddRide{
        public static final String DATE_PICKER_FRAGMENT_TAG = "datePickerFragment";
        public static final String TIME_PICKER_FRAGMENT_TAG = "timePickerFragment";
    }

    public class Instructor{
        public static final String STUDENT_DIALOG_FRAGMENT_TAG = "studentDialogFragment";
    }
}
