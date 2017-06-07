package cz.sedlaj19.autoskola.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cz.sedlaj19.autoskola.R;

/**
 * Created by Honza on 15. 2. 2017.
 */

public class ItemSwipeHelper {

    private static final Paint paint = new Paint();

    public static void onDrawChild(Context context, float dX, int colorLeft,
                                   int colorRight, int drawableLeft, int drawableRight,
                                   RecyclerView.ViewHolder viewHolder, Canvas canvas) {
        View itemView = viewHolder.itemView;
        // Margin from the sides
        float marginShort = UiUtils.dpToPx(context, 15);
        // Margin from the sides with icon width
        float marginLong = UiUtils.dpToPx(context, 47);
        float height = (float) itemView.getBottom() - (float) itemView.getTop();
        // 32 is icon size, like width and height
        float diff = height - UiUtils.dpToPx(context, 32);
        float marginVertical = diff / 2;
        if (dX > 0) {
            RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
            RectF icon_dest = new RectF((float) itemView.getLeft() + marginShort, (float) itemView.getTop() + marginVertical, (float) itemView.getLeft() + marginLong, (float) itemView.getBottom() - marginVertical);
            getBitmap(context, canvas, background, icon_dest, R.color.green, R.drawable.ic_check_white_48dp);
        } else {
            RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
            RectF icon_dest = new RectF((float) itemView.getRight() - marginLong, (float) itemView.getTop() + marginVertical, (float) itemView.getRight() - marginShort, (float) itemView.getBottom() - marginVertical);
            getBitmap(context, canvas, background, icon_dest, R.color.red, R.drawable.ic_clear_white_48dp);
        }
    }

    private static void getBitmap(Context context, Canvas canvas, RectF background,
                                  RectF icon_dest, int colorId, int drawableId) {
        paint.setColor(context.getResources().getColor(colorId));
        canvas.drawRect(background, paint);
        Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, drawableId);
        Bitmap out = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(out);
        drawable.setBounds(0, 0, c.getWidth(), c.getHeight());
        drawable.draw(c);
        canvas.drawBitmap(out, null, icon_dest, paint);
    }

}
