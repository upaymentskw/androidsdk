package com.upayments.track;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;


import com.upayments.UPaymentCallBack;
import com.upayments.activity.ActivityWeb;
import com.upayments.util.UpaymentGatewayAppPreferences;
import com.upayments.util.UpaymentGatewayAppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


/**
 * Created by Adil on 15/03/2015.
 */
class UpaymentGatewayHttpClient {
    public static final int DEFAULT_CONNECTION_TIMEOUT = 5 * 1000;  // 5s
    private static volatile int mTimeOut = DEFAULT_CONNECTION_TIMEOUT;
    private static String key_s_url= "";
    private static String key_e_url="";
    private static String key_noti_url= "";
    private static String key_dev_mode="";
    private static String UrlToLoad="";
    public synchronized static boolean postData(String value, boolean trueCallBack) {
        String path=null;
        JSONObject jsonObjectSend=null;

        String jsonString=null;
        UpaymentGatewayLog.d("-> posting data : " + value);

        if(TextUtils.isEmpty(value)) {
            UPaymentCallBack uPaymentCallBack2=   (UpaymentGateway.UPaymentCallBack);
            uPaymentCallBack2.errorPayUpayment("No any Data");
            UpaymentGatewayLog.d("-> posting empty data : " + jsonString);
            return false;

        }
        try {

            String merId="";String userName="";String mPass="";String mAPI="";
            String mOrderId="";String mTotalPrice= "";String mCurrencyCode="";String mSuccUrl="";String mErrorUrl="";
            String mNotifyUrl=""; String gateWay="";
            if(null!=value){
                JSONArray object= new JSONArray(value);
                if(object!=null && object.length()>0){
                    jsonObjectSend=object.getJSONObject(0);

                    if(null!=jsonObjectSend){
                        jsonString=      jsonObjectSend.getString("VALUE");
                     jsonString=    jsonString.toString();
                        UpaymentGatewayLog.d("-> posting value server : " + jsonString);
                        if(jsonObjectSend.has(UpaymentGatewayEvent.KEY_TEST_MODE))
                            key_dev_mode=       jsonObjectSend.getString(UpaymentGatewayEvent.KEY_TEST_MODE);

                        if(jsonObjectSend.has(UpaymentGatewayEvent.KEY_MERCHANT_ID)) {
                            merId = jsonObjectSend.getString(UpaymentGatewayEvent.KEY_MERCHANT_ID);
                            if(TextUtils.isEmpty(merId) && trueCallBack){

                                UPaymentCallBack uPaymentCallBack2=   (UpaymentGateway.UPaymentCallBack);
                                uPaymentCallBack2.errorPayUpayment("Merchant id empty");
                                UpaymentGatewayLog.d("-> posting empty Merchant id : " + jsonString);
                                return false;
                            }

                        }


                        if(jsonObjectSend.has(UpaymentGatewayEvent.KEY_USERNAME)) {
                            userName = jsonObjectSend.getString(UpaymentGatewayEvent.KEY_USERNAME);

                            if(TextUtils.isEmpty(userName) && trueCallBack){

                                UPaymentCallBack uPaymentCallBack2=   (UpaymentGateway.UPaymentCallBack);
                                uPaymentCallBack2.errorPayUpayment("userName  empty");
                                UpaymentGatewayLog.d("-> posting empty UserName  : " + jsonString);
                                return false;
                            }
                        }

                        if(jsonObjectSend.has(UpaymentGatewayEvent.KEY_PASSWORD)) {
                            mPass = jsonObjectSend.getString(UpaymentGatewayEvent.KEY_PASSWORD);
                            if(TextUtils.isEmpty(mPass) && trueCallBack){

                                UPaymentCallBack uPaymentCallBack2=   (UpaymentGateway.UPaymentCallBack);
                                uPaymentCallBack2.errorPayUpayment("Password  empty");
                                UpaymentGatewayLog.d("-> posting empty Password  : " + jsonString);
                                return false;
                            }
                        }

                        if(jsonObjectSend.has(UpaymentGatewayEvent.KEY_API_KEY)) {
                            mAPI = jsonObjectSend.getString(UpaymentGatewayEvent.KEY_API_KEY);
                            if(TextUtils.isEmpty(mAPI) && trueCallBack){

                                UPaymentCallBack uPaymentCallBack2=   (UpaymentGateway.UPaymentCallBack);
                                uPaymentCallBack2.errorPayUpayment("API Key empty");
                                UpaymentGatewayLog.d("-> posting empty API Key : " + jsonString);
                                return false;
                            }
                        }

                        if(jsonObjectSend.has(UpaymentGatewayEvent.KEY_ORDER_ID)) {
                            mOrderId = jsonObjectSend.getString(UpaymentGatewayEvent.KEY_ORDER_ID);
                            if(TextUtils.isEmpty(mOrderId) && trueCallBack){

                                UPaymentCallBack uPaymentCallBack2=   (UpaymentGateway.UPaymentCallBack);
                                uPaymentCallBack2.errorPayUpayment("Order Id  empty");
                                UpaymentGatewayLog.d("-> posting Order Id  Key : " + jsonString);
                                return false;
                            }
                        }

                        if(jsonObjectSend.has(UpaymentGatewayEvent.KEY_TOTAL_PRICE)) {
                            mTotalPrice = jsonObjectSend.getString(UpaymentGatewayEvent.KEY_TOTAL_PRICE);
                            if(TextUtils.isEmpty(mTotalPrice) && trueCallBack){

                                UPaymentCallBack uPaymentCallBack2=   (UpaymentGateway.UPaymentCallBack);
                                uPaymentCallBack2.errorPayUpayment("Total Price   empty");
                                UpaymentGatewayLog.d("-> posting Total Price  Key : " + jsonString);
                                return false;
                            }
                        }

                        if(jsonObjectSend.has(UpaymentGatewayEvent.KEY_CURRENCY_CODE)) {
                            mCurrencyCode = jsonObjectSend.getString(UpaymentGatewayEvent.KEY_CURRENCY_CODE);
                            if(TextUtils.isEmpty(mCurrencyCode) && trueCallBack){

                                UPaymentCallBack uPaymentCallBack2=   (UpaymentGateway.UPaymentCallBack);
                                uPaymentCallBack2.errorPayUpayment("Currency Code   empty");
                                UpaymentGatewayLog.d("-> posting Currency Code  Key : " + jsonString);
                                return false;
                            }
                        }

                        if(jsonObjectSend.has(UpaymentGatewayEvent.KEY_S_URL)) {
                            mSuccUrl = jsonObjectSend.getString(UpaymentGatewayEvent.KEY_S_URL);
                            if(TextUtils.isEmpty(mSuccUrl) && trueCallBack){

                                UPaymentCallBack uPaymentCallBack2=   (UpaymentGateway.UPaymentCallBack);
                                uPaymentCallBack2.errorPayUpayment("Success Url empty");
                                UpaymentGatewayLog.d("-> posting Success Url  Value missing : " + jsonString);
                                return false;
                            }
                        }
                        if(jsonObjectSend.has(UpaymentGatewayEvent.KEY_E_URL)) {
                            mErrorUrl = jsonObjectSend.getString(UpaymentGatewayEvent.KEY_E_URL);
                            if(TextUtils.isEmpty(mErrorUrl) && trueCallBack){

                                UPaymentCallBack uPaymentCallBack2=   (UpaymentGateway.UPaymentCallBack);
                                uPaymentCallBack2.errorPayUpayment("Error Url   empty");
                                UpaymentGatewayLog.d("-> posting Error Url empty : " + jsonString);
                                return false;
                            }
                        }

                        if(jsonObjectSend.has(UpaymentGatewayEvent.KEY_PAYMENTGATEWAY)) {
                            gateWay = jsonObjectSend.getString(UpaymentGatewayEvent.KEY_PAYMENTGATEWAY);
                            if(TextUtils.isEmpty(gateWay) && trueCallBack){

                                UPaymentCallBack uPaymentCallBack2=   (UpaymentGateway.UPaymentCallBack);
                                uPaymentCallBack2.errorPayUpayment("PaymentGateway Name   empty");
                                UpaymentGatewayLog.d("-> posting PaymentGateway Name empty : " + jsonString);
                                return false;
                            }
                        }

                        if(jsonObjectSend.has(UpaymentGatewayEvent.KEY_NOTIFYURL)) {
                            mNotifyUrl = jsonObjectSend.getString(UpaymentGatewayEvent.KEY_NOTIFYURL);
                            if(TextUtils.isEmpty(mNotifyUrl) && trueCallBack){

                                UPaymentCallBack uPaymentCallBack2=   (UpaymentGateway.UPaymentCallBack);
                                uPaymentCallBack2.errorPayUpayment("Notify Url  empty");
                                UpaymentGatewayLog.d("-> posting Notify Url empty : " + jsonString);
                                return false;
                            }
                        }

                        if(TextUtils.isEmpty(mNotifyUrl) || TextUtils.isEmpty(gateWay) || TextUtils.isEmpty(mErrorUrl) ||
                                TextUtils.isEmpty(mSuccUrl) || TextUtils.isEmpty(mCurrencyCode) || TextUtils.isEmpty(mTotalPrice)
                       ||TextUtils.isEmpty(mOrderId) || TextUtils.isEmpty(mAPI)  || TextUtils.isEmpty(mPass)
                       || TextUtils.isEmpty(userName) || TextUtils.isEmpty(merId)){

                            UpaymentGatewayLog.d("-> post all neccessary field : " + jsonString);

                            return false;
                        }

                    }
                }
            }
            String urlTest= "";
            URL url=null;
            if(key_dev_mode=="0")
                URLEncoder.encode(UpaymentGatewayAppUtils.ABC + UpaymentGatewayAppUtils.NAME+ UpaymentGatewayAppUtils.SLASH+ UpaymentGatewayAppUtils.TGET,"UTF-8");
            else
                URLEncoder.encode(UpaymentGatewayAppUtils.ABC + UpaymentGatewayAppUtils.NAME+ UpaymentGatewayAppUtils.SLASH+ UpaymentGatewayAppUtils.TGET_SAND,"UTF-8");

            if(key_dev_mode=="0")
                 urlTest = UpaymentGatewayAppUtils.ABC + UpaymentGatewayAppUtils.NAME+ UpaymentGatewayAppUtils.SLASH+ UpaymentGatewayAppUtils.TGET;
            else
                 urlTest = UpaymentGatewayAppUtils.ABC + UpaymentGatewayAppUtils.NAME+ UpaymentGatewayAppUtils.SLASH+ UpaymentGatewayAppUtils.TGET_SAND;

            UpaymentGatewayLog.d("->url final : " + urlTest);
            if(key_dev_mode=="0")
             url = new URL(UpaymentGatewayAppUtils.ABC + UpaymentGatewayAppUtils.NAME+ UpaymentGatewayAppUtils.SLASH+ UpaymentGatewayAppUtils.TGET);
            else
                 url = new URL(UpaymentGatewayAppUtils.ABC + UpaymentGatewayAppUtils.NAME+ UpaymentGatewayAppUtils.SLASH+ UpaymentGatewayAppUtils.TGET_SAND);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(mTimeOut);
            conn.setReadTimeout(mTimeOut);
            conn.setRequestMethod("POST");
            // IF there is json data we want to do a post
            if (value != null) {
                // POST
                conn.setDoOutput(true); // Forces post
              //  conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("charset", "utf-8");
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                writer.write(jsonObjectSend.toString());
                writer.flush();
                writer.close();
                //
            } else {
              //   GET
                conn.setDoOutput(false); // Defaults to false, but for readability
            }
            int statusCode = conn.getResponseCode();
            UpaymentGatewayLog.d("-> response code: " + statusCode);
            if (conn.getResponseCode() == 200) {
                CharSequence response = UpaymentGatewayHelper.toString(conn.getInputStream());
                if(response!=null){
                    response.toString();
                    JSONObject jsonObjectReponse= new JSONObject(response.toString());
                    if(jsonObjectReponse!=null){
                 String status=       jsonObjectReponse.getString("status");

                 if(!TextUtils.isEmpty(status) && status.equals("errors") ){

                  String errorMessage=   jsonObjectReponse.getString("error_msg");
                  String error_code= jsonObjectReponse.getString("error_code");
                     UpaymentGatewayLog.e("postData failed : " + errorMessage);
                     UPaymentCallBack uPaymentCallBack=   (UpaymentGateway.UPaymentCallBack);
                     uPaymentCallBack.errorPayUpayment(error_code.toString());
                     return  true;
                 }

                        if(!TextUtils.isEmpty(status) && status.equals("success")){


                            if(jsonObjectReponse.has("paymentURL")){
                                UrlToLoad=    jsonObjectReponse.getString("paymentURL");
                            }
                            if(jsonObjectSend.has(UpaymentGatewayEvent.KEY_S_URL)){
                                key_s_url   =       jsonObjectSend.getString(UpaymentGatewayEvent.KEY_S_URL);
                            }

                            if(jsonObjectSend.has(UpaymentGatewayEvent.KEY_E_URL)){
                                key_e_url=       jsonObjectSend.getString(UpaymentGatewayEvent.KEY_E_URL);
                            }

                            if(jsonObjectSend.has(UpaymentGatewayEvent.KEY_NOTIFYURL))
                                key_noti_url=       jsonObjectSend.getString(UpaymentGatewayEvent.KEY_NOTIFYURL);


                            if(!TextUtils.isEmpty(UrlToLoad)){
                                try {
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intentSend=    new Intent(UpaymentGateway.mAppContext, ActivityWeb.class);
                                            intentSend.putExtra("postDat",UrlToLoad);
                                            intentSend.putExtra("successUrl",key_s_url);
                                            intentSend.putExtra("errorUrl",key_e_url);
                                            intentSend.putExtra("notifyUrl",key_noti_url);
                                            UpaymentGateway.mAppContext.startActivity(intentSend);

                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else{
                                UpaymentGatewayLog.e("postData failed : " + "Error");
                                UPaymentCallBack uPaymentCallBack=   (UpaymentGateway.UPaymentCallBack);
                                uPaymentCallBack.errorPayUpayment("Error on Getting paymentURL");
                                return  false;

                            }

                        }

                    }
                   // refereshData(jsonObjectSend);
                    return true;
                }
              //  refereshData(jsonObjectSend);
                return true;
            } else {
                return false;
            }
        } catch (IOException | JSONException e) {
            UpaymentGatewayLog.e("postData failed : " + e);
            if(trueCallBack){
                UPaymentCallBack uPaymentCallBack=   (UpaymentGateway.UPaymentCallBack);
                uPaymentCallBack.errorPayUpayment(e.toString());
            }

        }
        return false;
    }


    private static void refereshData(JSONObject jsonObjectSend) {
        if(null!=jsonObjectSend){
            try {
                String event_type=    jsonObjectSend.getString("event_type");
                if(event_type!=null && !TextUtils.isEmpty(event_type)){
                    refereshValue(event_type);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static void refereshValue(String event_type) {
            ArrayList<String> arrayList= new ArrayList<>();
        Context context= UpaymentGateway.getContext();
        if(null!=context){
            UpaymentGatewayAppPreferences upaymentGatewayAppPreferences = new UpaymentGatewayAppPreferences(UpaymentGateway.getContext());
            // Get from Share Pref
            if(null!= upaymentGatewayAppPreferences){
                String ValueSaved= upaymentGatewayAppPreferences.getString(UpaymentGatewayAppUtils.KEY_SAVE_EVENT,"");
                // Convert to List
                if(!TextUtils.isEmpty(ValueSaved))
                    arrayList=  UpaymentGatewayHelper.convertToArray(ValueSaved);
                // Store Event
                arrayList.add(event_type);
                String toSave=  UpaymentGatewayHelper.convertToString(arrayList);
                upaymentGatewayAppPreferences.putString(UpaymentGatewayAppUtils.KEY_SAVE_EVENT,toSave);
                if(UpaymentGatewayAppUtils.INSTALL.equalsIgnoreCase(event_type) ||  UpaymentGatewayAppUtils.ORGANIC.equalsIgnoreCase(event_type)){
                    UpaymentGatewayAppUtils.EVENT_INSTALL=true;
                }
            }
        }else{
       if(UpaymentGatewayAppUtils.INSTALL.equalsIgnoreCase(event_type) ||  UpaymentGatewayAppUtils.ORGANIC.equalsIgnoreCase(event_type)){
                UpaymentGatewayAppUtils.EVENT_INSTALL=true;
            }
            Log.d("Context","is null");
        }
    }



    public static boolean checkExit(ArrayList<String> list ,String event){

        if(list!=null && list.size()>0 && !TextUtils.isEmpty(event)){
            for(int i= 0 ; i <list.size(); i++){

                if(list.get(i).equalsIgnoreCase(event))
                    return true ;

            }
        }


        return false;
    }



}
