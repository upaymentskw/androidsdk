package com.upayments.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.upayments.R;
import com.upayments.UPaymentCallBack;
import com.upayments.track.UpaymentGateway;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityWeb  extends AppCompatActivity {

    private boolean shouldOverride;
   private UPaymentCallBack uPaymentCallBack= null;
   private  String key_s_url=null;
   private String key_e_url=null;
   private String key_noti_url=null;
   private   WebView webView =null;
   private Boolean successPost= false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activty);
        uPaymentCallBack= (UpaymentGateway.UPaymentCallBack);

         webView = (WebView) findViewById(R.id.webView);

        Intent intent= getIntent();
        String urlLoad=  intent.getStringExtra("postDat");

         key_s_url=  intent.getStringExtra("successUrl");
         key_e_url=  intent.getStringExtra("errorUrl");
         key_noti_url=  intent.getStringExtra("notifyUrl");

         if(TextUtils.isEmpty(key_s_url)){
             key_s_url= null;
         }
        if(TextUtils.isEmpty(key_e_url)){
            key_e_url= null;
        }
        if(TextUtils.isEmpty(key_noti_url)){
            key_noti_url= null;
        }


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(urlLoad);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                shouldOverride = false;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Log.i("URL", url);
                        try {

                            if (url.contains(key_s_url)) {
                                shouldOverride = false;
                                String[] split = url.split("\\?");
                                String[] split1 = split[1].split("\\&");

                                Map<String, String> map = new HashMap<>();

                                for (int i = 0; i < split1.length; i++) {
                                    String[] split2 = split1[i].split("=");
                                    if (split2.length > 1) {
                                        map.put(split2[0].toLowerCase(),
                                                split2[1].replaceAll("%20", " "));
                                    }

                                }
                            String payMentId=    map.get("paymentid");
                            String Result=    map.get("result");
                                String PostDate=    map.get("postdate");
                                String TranID=    map.get("tranid");
                                String Ref=    map.get("ref");
                                String TrackID=    map.get("trackid");
                                String OrderID=    map.get("orderid");
                                String cust_ref=    map.get("ref");
                              //  String payment_type= map.get("payment_type");

                                PostUpayData postUpayData= new PostUpayData(payMentId,Result,PostDate,TranID
                                ,Ref,TrackID,OrderID,cust_ref,"");

                                if(!successPost) {
                                    uPaymentCallBack.callBackUpayment(postUpayData);
                                    successPost=true;
                                }

                             //   finish();
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {

                                        finish();
                                    }
                                }, 500);
                            } else if (url.contains(key_e_url)
                                    ) {
                                shouldOverride = true;

                                String[] split = url.split("\\?");
                                String[] split1 = split[1].split("\\&");

                                //Keys changed to lowercase. value as it is.
                                Map<String, String> map = new HashMap<>();

                                for (int i = 0; i < split1.length; i++) {
                                    String[] split2 = split1[i].split("=");
                                    if (split2.length > 1) {
                                        map.put(split2[0].toLowerCase(),
                                                split2[1].replaceAll("%20", " "));
                                    }

                                }

                                DateFormat out = null;
                                String dateconvert = null;
                                SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyy");
                                String dateString = map.get("postdate") +
                                        Calendar.getInstance().get(Calendar.YEAR);
                                Date date = formatter.parse(dateString);
                                out = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
                                dateconvert = out.format(date); // 27 Mar 2021

                                String payMentId=    map.get("paymentid");
                                String Result=    map.get("result");
                                String PostDate=    map.get("postdate");
                                String TranID=    map.get("tranid");
                                String Ref=    map.get("ref");
                                String TrackID=    map.get("trackid");
                                String OrderID=    map.get("orderid");
                                String cust_ref=    map.get("ref");
                              //  String payment_type= map.get("payment_type");

                                PostUpayData postUpayData= new PostUpayData(payMentId,Result,PostDate,TranID
                                        ,Ref,TrackID,OrderID,cust_ref, "");

                                if(!successPost) {
                                    uPaymentCallBack.callBackUpayment(postUpayData);
                                    successPost=true;
                                }


                                finish();
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {

                                        finish();
                                       // startActivity(paymentfailure);
                                    }
                                }, 500);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, 500);
                return shouldOverride;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.i("URL", url);

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {

                        try {
                            if (url.contains(key_s_url)) {

                                String[] split = url.split("\\?");
                                String[] split1 = split[1].split("\\&");

                                Map<String, String> map = new HashMap<>();

                                for (int i = 0; i < split1.length; i++) {
                                    String[] split2 = split1[i].split("=");
                                    if (split2.length > 1) {
                                        map.put(split2[0].toLowerCase(),
                                                split2[1].replaceAll("%20", " "));
                                    }

                                }

                                String payMentId=    map.get("paymentid");
                                String Result=    map.get("result");
                                String PostDate=    map.get("postdate");
                                String TranID=    map.get("tranid");
                                String Ref=    map.get("ref");
                                String TrackID=    map.get("trackid");
                                String OrderID=    map.get("orderid");
                                String cust_ref=    map.get("ref");
                               // String payment_type= map.get("payment_type");

                                PostUpayData postUpayData= new PostUpayData(payMentId,Result,PostDate,TranID
                                        ,Ref,TrackID,OrderID,cust_ref, "");


                                if(!successPost) {
                                    uPaymentCallBack.callBackUpayment(postUpayData);
                                    successPost=true;
                                }

                              //  finish();


                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {

                                       finish();
                                      //  startActivity(orderConfirmationIntent);
                                    }
                                }, 500);

                            } else if (url.contains(key_e_url)){
                                //Uri uri = Uri.parse(url.toLowerCase());

                                String[] split = url.split("\\?");
                                String[] split1 = split[1].split("\\&");

                                Map<String, String> map = new HashMap<>();

                                for (int i = 0; i < split1.length; i++) {
                                    String[] split2 = split1[i].split("=");
                                    if (split2.length > 1) {
                                        map.put(split2[0].toLowerCase(),
                                                split2[1].replaceAll("%20", " "));
                                    }

                                }

                                DateFormat out = null;
                                String dateconvert = null;
                                SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyy");
                                String dateString =
                                        map.get("postdate") + Calendar.getInstance().get(Calendar.YEAR);
                                Date date = formatter.parse(dateString);
                                out = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
                                dateconvert = out.format(date);
                                String payMentId=    map.get("paymentid");
                                String Result=    map.get("result");
                                String PostDate=    map.get("postdate");
                                String TranID=    map.get("tranid");
                                String Ref=    map.get("ref");
                                String TrackID=    map.get("trackid");
                                String OrderID=    map.get("orderid");
                                String cust_ref=    map.get("ref");
                              //  String payment_type= map.get("payment_type");

                                PostUpayData postUpayData= new PostUpayData(payMentId,Result,PostDate,TranID
                                        ,Ref,TrackID,OrderID,cust_ref, "");



                                if(!successPost) {
                                    uPaymentCallBack.callBackUpayment(postUpayData);
                                    successPost=true;
                                }

                               // finish();

                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {

                                        finish();
                                        //startActivity(paymentfailure);
                                    }
                                }, 500);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 500);

                return super.shouldInterceptRequest(view, url);
            }

            public void onLoadResource(WebView view, String url) {

            }

            public void onPageFinished(WebView view, String url) {
             //  finish();
            }

            @Override
            @TargetApi(Build.VERSION_CODES.M)
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                final Uri uri = request.getUrl();
                handleError(view, error.getErrorCode(), error.getDescription().toString(), uri);
            }

//            @SuppressWarnings("deprecation")
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
//                final Uri uri = Uri.parse(failingUrl);
//                handleError(view, errorCode, description, uri);
//            }

        });

    }

    private void handleError(WebView view, int errorCode, String description, Uri uri) {

        String message = null;
        if (errorCode == WebViewClient.ERROR_AUTHENTICATION) {
            message = "User authentication failed on server";
        } else if (errorCode == WebViewClient.ERROR_TIMEOUT) {
            message = "The server is taking too much time to communicate. Try again later.";
        } else if (errorCode == WebViewClient.ERROR_TOO_MANY_REQUESTS) {
            message = "Too many requests during this load";
        } else if (errorCode == WebViewClient.ERROR_UNKNOWN) {
            message = "Generic error";
        } else if (errorCode == WebViewClient.ERROR_BAD_URL) {
            message = "Check entered URL..";
        } else if (errorCode == WebViewClient.ERROR_CONNECT) {
            message = "Failed to connect to the server";
        } else if (errorCode == WebViewClient.ERROR_FAILED_SSL_HANDSHAKE) {
            message = "Failed to perform SSL handshake";
        } else if (errorCode == WebViewClient.ERROR_HOST_LOOKUP) {
            message = "Server or proxy hostname lookup failed";
        } else if (errorCode == WebViewClient.ERROR_PROXY_AUTHENTICATION) {
            message = "User authentication failed on proxy";
        } else if (errorCode == WebViewClient.ERROR_REDIRECT_LOOP) {
            message = "Too many redirects";
        } else if (errorCode == WebViewClient.ERROR_UNSUPPORTED_AUTH_SCHEME) {
            message = "Unsupported authentication scheme (not basic or digest)";
        } else if (errorCode == WebViewClient.ERROR_UNSUPPORTED_SCHEME) {
            message = "unsupported scheme";
        } else if (errorCode == WebViewClient.ERROR_FILE) {
            message = "Generic file error";
        } else if (errorCode == WebViewClient.ERROR_FILE_NOT_FOUND) {
            message = "File not found";
        } else if (errorCode == WebViewClient.ERROR_IO) {
            message = "The server failed to communicate. Try again later.";
        }
        if (message != null) {
           // Toast.makeText(getActivity(), "" + message, Toast.LENGTH_LONG).show();
            String payMentId=   "cancel";
            String Result=    "CANCELED";
            String PostDate=    "";
            String TranID=    "";
            String Ref=    "";
            String TrackID=   "";
            String OrderID=    "";
            String cust_ref=    "";
            String payment_type= "";

            PostUpayData postUpayData= new PostUpayData(payMentId,Result,PostDate,TranID
                    ,Ref,TrackID,OrderID,cust_ref, payment_type);

            uPaymentCallBack.callBackUpayment(postUpayData);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        String payMentId=   "cancel";
//        String Result=    "CANCELED";
//        String PostDate=    "";
//        String TranID=    "";
//        String Ref=    "";
//        String TrackID=   "";
//        String OrderID=    "";
//        String cust_ref=    "";
//
//        PostUpayData postUpayData= new PostUpayData(payMentId,Result,PostDate,TranID
//                ,Ref,TrackID,OrderID,cust_ref);
//
//        uPaymentCallBack.callBackUpayment(postUpayData);
       // finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
           // this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
