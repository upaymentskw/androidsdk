package com.upayments.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


public class UpaymentGatewayAppUtils {

    public static final String KEY_SAVE_EVENT = "SENT_EVENT";
    public static final String ABC="https://";
    public static final String NAME="api.upayments.com";
    public static   boolean EVENT_INSTALL=false ;
    public static  String refererUrlSave ="" ;
    //Eventx
    public static final String INSTALL="install";
    public static final String ORGANIC="organic";
    public static final String OPEN="open";
    public static final String KEY_ORDER_VALUE="order_value";

    // payment-request
    public static final String TGET="payment-request";
    public static final String TGET_SAND="test-payment";
    public static final String QUERRY="?" ;

    public static final String SLASH="/";

    public static final int SDK_VERSION = 23;
    /**
     * @return true If device has Android Marshmallow or above version
     */
    public static boolean hasM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isMSupportDevice(Context ctx) {
        return Build.VERSION.SDK_INT >= UpaymentGatewayAppUtils.SDK_VERSION;
    }




}
