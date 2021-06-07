package com.upayments.track;

import android.os.Handler;

import java.util.List;

/**
 * Created by Adil on 28/03/2015.
 */
class UpaymentGatewayPropTracker implements Runnable {

    private final Handler handler;
    private final UpaymentGatewayEvent properties;

    UpaymentGatewayPropTracker(UpaymentGatewayEvent properties, Handler ui) {
        this.properties = properties;
        this.handler = ui;
    }

    @Override
    public void run() {
        handler.removeMessages(UpaymentGateway.HANDLER_MESSAGE_RETRY);
        String propertiesToString = null;
        if(properties!=null){
            propertiesToString  = properties.getJson().toString();
        }

        if (!UpaymentGatewayConnectionHelper.isConnected(UpaymentGateway.getContext())) {
            UpaymentGatewayLog.d("-> no network access. Properties is being stored and will be sent later.", true);
           // UpaymentGatewayFileHelper.appendLine(propertiesToString); //ToDO Commented dont store to resend
            handler.sendEmptyMessageDelayed(UpaymentGateway.HANDLER_MESSAGE_RETRY, UpaymentGatewayConfig.NO_INTERNET_RETRY_DELAY_MILLIS);
            return;
        }

        List<String> storedProperties = UpaymentGatewayFileHelper.getLines();
        if (storedProperties.isEmpty()) {
            final boolean success = UpaymentGatewayHttpClient.postData("[" + propertiesToString + "]",false);
            if (!success) {
                UpaymentGatewayLog.d("-> synchronization failed. Will retry if no other pending track is found.");
               UpaymentGatewayFileHelper.appendLine(propertiesToString);
                if(propertiesToString==null){
                    return;
                }
                handler.sendEmptyMessageDelayed(UpaymentGateway.HANDLER_MESSAGE_RETRY,
                        UpaymentGatewayConfig.POST_FAILED_RETRY_DELAY_MILLIS);
            } else {
                UpaymentGatewayLog.d("-> properties tracked !", true);
            }
            return;
        }

//        UpaymentGatewayLog.d("-> " + storedProperties.size() + " stored properties found, current properties added to history to " +
//                "be sent with stored ones.");
    //    UpaymentGatewayFileHelper.appendLine(propertiesToString); //we treat the current properties as a stored properties (history)
// Todo Commented
       new AdosizAnalyticsStoredPropTracker(handler).run();//
    }

}
