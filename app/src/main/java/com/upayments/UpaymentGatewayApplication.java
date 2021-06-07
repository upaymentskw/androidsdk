package com.upayments;

import android.app.Application;

import com.upayments.track.UpaymentGateway;


/**
 * Created by Adil
 */
public class UpaymentGatewayApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
     UpaymentGateway.init(this,"","",true);
    }
}
