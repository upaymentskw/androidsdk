package com.upayments.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebViewClientImpl extends WebViewClient {

    private Context activity = null;
    private Context mContext;
    private AttributeSet attrs;
    private int styleAttr;
    private View view;

    public WebViewClientImpl(Context context) {
        super();
        this.mContext=context;

    }

    public WebViewClientImpl(Context context, AttributeSet attrs) {
        super();
        this.mContext=context;
        this.attrs=attrs;
    }

    public WebViewClientImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super();
        this.mContext=context;
        this.attrs=attrs;
        this.styleAttr=defStyleAttr;
    }



    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if(url.indexOf("journaldev.com") > -1 ) return false;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
        return true;
    }

}