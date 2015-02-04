package com.judopay.android.library.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;


public class UiUtils {

    /**
     *
     * @param context of you activity
     * @param dips value in device independent pixels
     * @return actual pixel value for the current devices density
     */
    public static int toPixels(final Context context, float dips) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return (int) (dips * ((float) metrics.densityDpi / 160));//px = dp * (dpi / 160)
    }
}
