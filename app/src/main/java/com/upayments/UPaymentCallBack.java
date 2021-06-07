package com.upayments;

import com.upayments.activity.PostUpayData;

public interface UPaymentCallBack {

   void callBackUpayment(PostUpayData postUpayData);
   void errorPayUpayment(String data);
}
