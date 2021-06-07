package com.upayments.track;

import android.util.Log;

/**
 * Created by Adil
 */
public class UpaymentGatewayLog {

    private static String TAG = UpaymentGateway.class.getSimpleName();
    protected static boolean LOG_ENABLED = false;

//    public static void v(String msg) {
//        Log.v(TAG, msg);
//    }

    public static void d(String msg, boolean mandatory) {
        if (LOG_ENABLED || mandatory) Log.d(TAG, (mandatory ? "AdosizAnalytics Analytics : " : "") + msg);
    }

    public static void d(String msg) {
        d(msg, false);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    public static void assertCondition(boolean condition, String msg) {
        if (condition) {
            return;
        }
        throw new IllegalStateException(msg);
    }

    public static void warnCondition(boolean condition, String msg) {
        if (condition) {
            return;
        }
        w(msg);
    }

//    public static boolean isDebug() {
//        Context ctx = AdosizAnalytics.getContext();
//        try {
//            Class<?> clazz = Class.forName(ctx.getPackageName() + ".BuildConfig");
//            Field field = clazz.getField("DEBUG");
//            return (boolean) field.get(null);
//        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
