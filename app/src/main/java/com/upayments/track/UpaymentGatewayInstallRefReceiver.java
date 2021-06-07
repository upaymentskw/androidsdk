package com.upayments.track;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.BuildConfig;
import android.util.Log;


import com.upayments.BuildConfig;
import com.upayments.util.UpaymentGatewayAppUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Adil .
 * BroadcastReceiver for automatically storing Google Play Store referrer information as AdosizAnalytics Properties.
 * <p>You can use UpaymentGatewayInstallRefReceiver to capture and store referrer information,
 * and use that information to track how users from different sources are using your app.
 * To enable UpaymentGatewayInstallRefReceiver, add a clause like the following
 * to the &lt;application&gt; tag of your AndroidManifest.xml.</p>
 * <pre>
 * {@code
 * <receiver android:name="com.adosis.mobo.track.UpaymentGatewayInstallRefReceiver"
 *           android:exported="true">
 *     <intent-filter>
 *         <action android:name="com.android.vending.INSTALL_REFERRER" />
 *     </intent-filter>
 * </receiver>
 * }
 * </pre>
 * <p>If you plan to use multiple install refer, please refer to this workaround: &nbsp;https://mixpanel
 * .com/help/questions/articles/how-can-i-use-multiple-install-trackers-with-the-android-library&nbsp;</p>
 * <p>Once you've added the &lt;receiver&gt; tag to your manifest,
 * the first call to {@link com.upayments( UpaymentGatewayEvent UpaymentGatewayEvent )}
 * will include the user's Google Play Referrer as metadata. In addition, if
 * you include utm parameters in your link to Google Play, they will be parsed and
 * provided as individual properties in your track calls.</p>
 * <p>UpaymentGatewayInstallRefReceiver looks for any of the following parameters. All are optional.</p>
 * <ul>
 * <li>utm_source: often represents the source of your traffic (for example, a search engine or an ad)</li>
 * <li>utm_medium: indicates whether the link was sent via email, on facebook, or pay per click</li>
 * <li>utm_term: indicates the keyword or search term associated with the link</li>
 * <li>utm_content: indicates the particular content associated with the link (for example,
 * which email message was sent)</li>
 * <li>utm_campaign: the name of the marketing campaign associated with the link.</li>
 * </ul>
 * <p>Whether or not the utm parameters are present, the UpaymentGatewayInstallRefReceiver will
 * also create a "referrer" super property with the complete referrer string.</p>
 */
public class UpaymentGatewayInstallRefReceiver extends BroadcastReceiver {

    private Context mcontext;
    private  String source=null;
    String medium=null;

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle extras = intent.getExtras();
        mcontext=context;
        Map<String, String> newPrefs=null;


        if (null == extras) {
            return;
        }
        final String referrer = extras.getString("referrer");
        UpaymentGatewayAppUtils.  refererUrlSave= referrer;
        if (null == referrer) {
            return;
        }
        Log.d("referer",referrer);
            UpaymentGatewayLog.d("Install Referrer received");

        String PACKAGE_NAME;

    }
//
//




    private String find(Matcher matcher) {
        if (matcher.find()) {
            final String encoded = matcher.group(2);
            if (null != encoded) {
                try {
                    return URLDecoder.decode(encoded, "UTF-8");
                } catch (final UnsupportedEncodingException e) {
                    UpaymentGatewayLog.e("Could not decode a parameter into UTF-8");
                }
            }
        }
        return null;
    }

    private final Pattern UTM_SOURCE_PATTERN = Pattern.compile("(^|&)utm_source=([^&#=]*)([#&]|$)");
    private final Pattern UTM_MEDIUM_PATTERN = Pattern.compile("(^|&)utm_medium=([^&#=]*)([#&]|$)");
    private final Pattern UTM_CAMPAIGN_PATTERN = Pattern.compile("(^|&)utm_campaign=([^&#=]*)([#&]|$)");
    private final Pattern UTM_CONTENT_PATTERN = Pattern.compile("(^|&)utm_content=([^&#=]*)([#&]|$)");
    private final Pattern UTM_TERM_PATTERN = Pattern.compile("(^|&)utm_term=([^&#=]*)([#&]|$)");
}
