package cz.sedlaj19.autoskola.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;

/**
 * Class description
 */

public class UiUtils {

    /**
     * Converts the dp to pixels.
     *
     * @param ctx context
     * @param dp  dp to be converted
     * @return number of pixels
     */
    public static int dpToPx(Context ctx, int dp) {
        Resources resources = ctx.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    /**
     * Dismisses keyboard.
     *
     * @param activity
     */
    public static void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }

}
