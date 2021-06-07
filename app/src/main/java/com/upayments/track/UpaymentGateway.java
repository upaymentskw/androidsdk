package com.upayments.track;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
//import android.os.Build;
//import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;

import androidx.annotation.Nullable;
//import android.support.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
//import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;

//import com.adosizanalytics.mobo.BuildConfig;
//import com.adosizanalytics.mobo.BuildConfig;

//import com.adosizanalytics.mobo.BuildConfig;
//import com.adosizanalytics.mobo.BuildConfig;
//import com.google.android.gms.ads.identifier.AdvertisingIdClient;
//import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
//import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.upayments.UPaymentCallBack;
import com.upayments.activity.Test;
import com.upayments.util.UpaymentGatewayAppPreferences;
import com.upayments.util.UpaymentGatewayAppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Adil on 25/11/2016.
 */
public class UpaymentGateway {

    private static final String TAG = UpaymentGateway.class.getSimpleName();
    protected static final int HANDLER_MESSAGE_RETRY = 1;
    static String mAdoDomain=null;
    public static UPaymentCallBack UPaymentCallBack;
    private static UpaymentGateway mAdoInstance;
     public static Context mAppContext;
    protected Executor mAdoExecutor = Executors.newSingleThreadExecutor();
    static UpaymentGatewayAppPreferences upaymentGatewayAppPreferences;
    @Nullable

    static String sdk_merchant_id="";
    static String sdk_api_key="";


    protected Handler mUiHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_MESSAGE_RETRY:
                    UpaymentGatewayLog.d("Retry strategy");
                    UpaymentGateway.getInstance().track(null, UPaymentCallBack);
                    break;
            }
        }
    };

    private UpaymentGateway() {
        //cannot be initialized anywhere else
    }

    public static UpaymentGateway getInstance() {
        if (mAdoInstance == null) {
            mAdoInstance = new UpaymentGateway();
        }
        return mAdoInstance;
    }

//    public static String getSDKVersion() {
//        return BuildConfig.VERSION_NAME;
//    }

    public static String getEuidl() {

        ContentResolver contentResolver = UpaymentGateway.getContext().getContentResolver();
      return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);

    }

    /**
     * Initialization.
     * @param context the app context
     */

   // sdk_merchant_id = key  , api_key=  sdk_api_key1
    public static void init(Context context,String merchant_id,String api_key, boolean token) {
        UpaymentGatewayLog.LOG_ENABLED = token;
        sdk_merchant_id=merchant_id;
        sdk_api_key=api_key;

        UpaymentGatewayLog.e("Install process inside first init");

        UpaymentGatewayLog.assertCondition(mAppContext == null && mAdoDomain == null, "Init must be called only once.");
        UpaymentGatewayLog.assertCondition(UpaymentGatewayHelper.isPermissionGranted(context, Manifest.permission.INTERNET),
                "Init failed : permission is missing. You must add permission " +
                        Manifest.permission.INTERNET + " in your app Manifest.xml.");
        UpaymentGatewayLog.assertCondition(UpaymentGatewayHelper.isPermissionGranted(context, Manifest.permission.ACCESS_NETWORK_STATE),
                "Init failed : permission is missing: Your must add permission " + Manifest.permission
                        .ACCESS_NETWORK_STATE + " in your app Manifest.xml");
        UpaymentGatewayLog.assertCondition(UpaymentGatewayHelper.isPermissionGranted(context, Manifest.permission.ACCESS_WIFI_STATE),
                "Init failed : permission is missing: Your must add permission " + Manifest.permission
                        .ACCESS_WIFI_STATE + " in your app Manifest.xml");

        UpaymentGatewayLog.assertCondition(context != null, "Init failed : context is null. You must provide a valid context.");
//

        mAppContext = context;
        mAdoDomain = UpaymentGatewayAppUtils.ABC + UpaymentGatewayAppUtils.NAME+ UpaymentGatewayAppUtils.SLASH+ UpaymentGatewayAppUtils.TGET;
        upaymentGatewayAppPreferences = new UpaymentGatewayAppPreferences(context);
            init();

    }

    private static boolean  inits(UpaymentGatewayEvent properties) {
        ArrayList<String> arrayList= new ArrayList<>();
        String eventType=null;

        // Get from Share Pref
        if(null!= upaymentGatewayAppPreferences){
           String ValueSaved= upaymentGatewayAppPreferences.getString(UpaymentGatewayAppUtils.KEY_SAVE_EVENT,"");
            // Convert to List
           if(!TextUtils.isEmpty(ValueSaved))
                arrayList=  UpaymentGatewayHelper.convertToArray(ValueSaved);

            try {
                eventType=    properties.getJson().getString("event_type");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            boolean status=   UpaymentGatewayHelper.checkExit(arrayList,eventType);
            //
            if(status){
                return status;
              //  getInstance().track(null);
            }else{
                if(null!=properties){

                    try {
                    String event=    properties.getJson().getString("event_type");
                        if(!TextUtils.isEmpty(event)){

//                            arrayList.add(event);
//                            String toSave=  UpaymentGatewayHelper.convertToString(arrayList);
//                            upaymentGatewayAppPreferences.putString(UpaymentGatewayAppUtils.KEY_SAVE_EVENT,toSave);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return status;
            }

        }
        return false;
    }

    private static void init( ) {
        ArrayList<String> arrayList= new ArrayList<>();

        Log.d("Install Process 1","Process");
        // Get from Share Pref
        if(null!= upaymentGatewayAppPreferences){
            String ValueSaved= upaymentGatewayAppPreferences.getString(UpaymentGatewayAppUtils.KEY_SAVE_EVENT,"");
            // Convert to List
            if(!TextUtils.isEmpty(ValueSaved))
                arrayList=  UpaymentGatewayHelper.convertToArray(ValueSaved);

            boolean status=   UpaymentGatewayHelper.checkExit(arrayList, UpaymentGatewayAppUtils.INSTALL);
            //
            if(status){
                //getInstance().track(null, this);
            }else{

//                UpaymentGatewayEvent genericTag = new UpaymentGatewayEvent.Builder(UpaymentGatewayAppUtils.INSTALL)
//                        .setEvent(UpaymentGatewayAppUtils.INSTALL)
//                        .build();
//
//                AdosizAnalytics.getInstance().track(genericTag);

            }

        }

    }




   public static Context getContext() {
        UpaymentGatewayLog.assertCondition(mAppContext != null, "The SDK has not been initialized. You must call AdosizAnalytics" +
                ".init(Context, String) once.");
        return mAppContext;
    }

    public static void setContext(Context mAppContext){

        if(mAppContext==null){

            UpaymentGateway.mAppContext=mAppContext;
        }
    }

    public void track(final UpaymentGatewayEvent properties, UPaymentCallBack callBack) {
        UpaymentGatewayLog.d("track process 3");
        this.UPaymentCallBack= callBack;

        List<String> storedProperties = null;
        try {
            if(properties!=null){
                storedProperties = UpaymentGatewayFileHelper.getLines();
                String line = storedProperties.get(0);
                JSONObject lineJson = new JSONObject(line);
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(lineJson);
                UpaymentGatewayFileHelper.deleteLines(jsonArray.length());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        UpaymentGatewayLog.assertCondition(mAdoDomain != null, "The SDK has not been initialized. You must call UpaymentGateway" +
                ".init(Context, String) once.");
        if (properties == null) {
            mAdoExecutor.execute(new AdosizAnalyticsStoredPropTracker(mUiHandler));
        } else {
       //  if(!sendStatus(properties))
            mAdoExecutor.execute(new UpaymentGatewayPropTracker(properties, mUiHandler));
        }

        // mAdoExecutor.execute(new UpaymentGatewayPropTracker(properties, mUiHandler));

    }

    public void setAndroidId(String androidId) {
        String ndroidId = androidId;
    }

}