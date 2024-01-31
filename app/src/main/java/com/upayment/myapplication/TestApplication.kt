package com.upayment.testapp

import android.app.Application
import android.widget.Toast
import com.upayment.upaymentsdk.track.UpaymentGateway

class TestApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // 1 for Production and zero for Sandbox

        val tokenNonWhiteLabel :String = "oxxnDz0ES48qyaT96f8VG6YYyFr0krk2akJI7LH5";
        val tokenWhiteLabel :String = "aS4OMOvxGd1UDsTgxRBEE1af5BqSaOVDa5eMtch2";
        UpaymentGateway.init(this,"123",tokenNonWhiteLabel,true,"1");

    }
}