package com.upayments.track;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.text.TextUtils;

//import com.adosizanalytics.mobo.BuildConfig;
//import com.adosizanalytics.mobo.BuildConfig;

//import com.upayments.BuildConfig;
import com.upayments.BuildConfig;
import com.upayments.util.UpaymentGatewayAppUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


/**
 * Created by Adil on 22/1/2017.
 */
public class UpaymentGatewayEvent {

    // merchant_id
    public static final String KEY_MERCHANT_ID="merchant_id";
    //username
    public static final String KEY_USERNAME="username";

    //password
    public static final String KEY_PASSWORD="password";

    //api_key
    public static final String KEY_API_KEY="api_key";

    //order_id
    public static final String KEY_ORDER_ID="order_id";

    //total_price
    public static final String KEY_TOTAL_PRICE="total_price";

    //CurrencyCode
    public static final String KEY_CURRENCY_CODE="CurrencyCode";

    //success_url
    public static final String KEY_S_URL="success_url";

    //error_url
    public static final String KEY_E_URL="error_url";

    //test_mode
    public static final String KEY_TEST_MODE="test_mode";

    //CstFName
    public static final String KEY_CNAME="CstFName";

    //CstEmail
    public static final String KEY_CEMAIL="CstEmail";

    //CstMobile
    public static final String KEY_CMOBILE="CstMobile";

    //payment_gateway
    public static final String KEY_PAYMENTGATEWAY="payment_gateway";

   // whitelabled
   public static final String KEY_WHITE_ENABLED="whitelabled";

   //ProductTitle
   public static final String KEY_PRODUCT_TITLE="ProductTitle";

    //ProductName
    public static final String KEY_PRODUCT_NAME="ProductName";

    //ProductPrice
    public static final String KEY_PRODUCT_PRICE="ProductPrice";

    //ProductQty
    public static final String KEY_PRODUCT_QUANTITY="ProductQty";


    //Reference
    public static final String KEY_REFERENCE="Reference";

    //notifyURL
    public static final String KEY_NOTIFYURL="notifyURL";




    public static final String KEY_USER_ID="user_id";
    public static final String KEY_APP_VERSION_NAME = "ea-app_version";
    public static final String KEY_APP_VERSION_CODE = "app_version";

    // Pagination keys

    public static final String KEY_PAGINATION_PATH = "pagination";
    public static final String KEY_PAGINATION_EMAIL = "uPayment_email";
    public static final String KEY_PAGINATION_UID = "uPayment_uid";
    public static final String KEY_PAGINATION_PROFILE = "uPayment_profile";
    public static final String KEY_PAGINATION_GROUP = "uPayment_pagegroup";
    public static final String KEY_PAGINATION_ACTION = "uPayment_action";
    public static final String KEY_PAGINATION_PROPERTY = "uPayment_property";
    public static final String KEY_PAGINATION_NEW_CUSTOMER = "uPayment_newcustomer";
    public static final String KEY_SDK_VERSION = "sdk_version";
    public static final String KEY_CARRIER="carrier";
    public static final String KEYHELP="&";
    public static final String KEY_VALUE="VALUE";
    JSONObject mJProperties;
    private JSONObject mJInterior;
    private JSONObject mJPagination;

    /**
     * Use {@link Builder} instead
     */
    protected UpaymentGatewayEvent(Builder builder) {
        mJInterior = builder.AInterior;
        mJPagination = builder.APagination;
        mJProperties = builder.AProperties;
    }

    protected JSONObject getJson() {
        return UpaymentGatewayJsnUtils.merge(mJInterior, mJPagination, mJProperties);
    }

    public static class Builder<T extends Builder> {
        protected final JSONObject AProperties = new JSONObject();
        protected final JSONObject AInterior = new JSONObject();
        protected final JSONObject APagination = new JSONObject();
        protected  String mNumber;
        protected  String fcmId="";
        protected  String event="";
        StringBuilder stringBuilder =null;
     //   public Builder(String path,String fcmId,String valNumber) {
        public Builder(String merchantId) {
            if (!merchantId.startsWith("/")) {
                this.event=merchantId;
               // this.fcmId=fcmId;
              //  this.mNumber=valNumber;
                merchantId = "/" + merchantId;

            }
            initInternalParams();
           // stringBuilder = new StringBuilder();
            UpaymentGatewayJsnUtils.put(APagination, KEY_PAGINATION_PATH, merchantId);
        }
        private void initInternalParams() {
            try {
                stringBuilder = new StringBuilder();

                setVersionCodeAndVersionName();

                UpaymentGatewayJsnUtils.put(AInterior,KEY_VALUE,stringBuilder.toString());
                // ToDo comment
                UpaymentGatewayLog.d(stringBuilder.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        private void setVersionCodeAndVersionName() {
            Context context = UpaymentGateway.getContext();
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                UpaymentGatewayJsnUtils.put(AInterior, KEY_APP_VERSION_NAME, packageInfo.versionName);

                stringBuilder.append(URLEncoder.encode(KEY_APP_VERSION_CODE,"UTF-8")+"="+(URLEncoder.encode(String.valueOf(packageInfo.versionCode),"UTF-8")+KEYHELP));
                UpaymentGatewayJsnUtils.put(AInterior, KEY_APP_VERSION_CODE, String.valueOf(packageInfo.versionCode));
            } catch (Exception e) {
                UpaymentGatewayLog.e("Error while getting package manager / package info. Error : " +
                        (e == null ? "null" : e.getMessage()));
            }
        }

        public T set(String key, String value) {
            UpaymentGatewayJsnUtils.put(AProperties, key, value);
            return (T) this;
        }

        protected T set(String key, int value) {
            UpaymentGatewayJsnUtils.put(AProperties, key, value);
            return (T) this;
        }


        public T setNewCustomer(boolean newCustomer) {
            if (newCustomer) {
                UpaymentGatewayJsnUtils.put(APagination, KEY_PAGINATION_NEW_CUSTOMER, String.valueOf(1));
            }
            return (T) this;
        }

        public T setEmail(String email) {
            if (TextUtils.isEmpty(email)) {
                UpaymentGatewayLog.w("Email is empty");
            }
            UpaymentGatewayJsnUtils.put(APagination, KEY_PAGINATION_EMAIL, email);
            return (T) this;
        }

        public T setEvent(String event){

            if(TextUtils.isEmpty(event)){
                UpaymentGatewayLog.w("Event is empty");
            }
            stringBuilder.append(KEY_MERCHANT_ID+"="+event+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_MERCHANT_ID,event);

            return (T) this;
        }


        public T setOrderValue(String orderValue){

            if(TextUtils.isEmpty(orderValue)){
                UpaymentGatewayLog.w("OrderId is empty");
            }
            stringBuilder.append(UpaymentGatewayAppUtils.KEY_ORDER_VALUE+"="+orderValue+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior, UpaymentGatewayAppUtils.KEY_ORDER_VALUE,orderValue);

            return (T) this;
        }

//        public T setUserId(String userId){
//
//            if(TextUtils.isEmpty(userId)){
//                UpaymentGatewayLog.w("User Id is empty");
//            }
//            stringBuilder.append(KEY_USER_ID+"="+userId+KEYHELP);
//            UpaymentGatewayJsnUtils.put(AInterior,KEY_USER_ID,userId);
//
//            return (T)this;
//        }

        public T setUID(String uid) {
            if (!TextUtils.isEmpty(uid)) {
                UpaymentGatewayJsnUtils.put(APagination, KEY_PAGINATION_UID, uid);
            } else {
                UpaymentGatewayLog.w("UID is empty, should not be.");
            }
            return (T) this;
        }

        public T setProfile(String profile) {
            UpaymentGatewayJsnUtils.put(APagination, KEY_PAGINATION_PROFILE, profile);
            return (T) this;
        }

        public T setPageGroup(String group) {
            UpaymentGatewayJsnUtils.put(APagination, KEY_PAGINATION_GROUP, group);
            return (T) this;
        }

        public T setAction(UpaymentGatewayAction adosisAction) {
            UpaymentGatewayJsnUtils.put(APagination, KEY_PAGINATION_ACTION, adosisAction.getJson());
            return (T) this;
        }

        public T setProperty(UpaymentGatewayCentricProperty property) {
            UpaymentGatewayJsnUtils.put(APagination, KEY_PAGINATION_PROPERTY, property.getJson());
            return (T) this;
        }

        public UpaymentGatewayEvent build() {
            UpaymentGatewayEvent res = new UpaymentGatewayEvent(this);
            return res;
        }

        public Builder<Builder> setMerchantId(String merchantId) {
            if(TextUtils.isEmpty(merchantId)){
                UpaymentGatewayLog.w("merchantId is empty");
            }
            stringBuilder.append(KEY_MERCHANT_ID+"="+merchantId+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_MERCHANT_ID,merchantId);

            return (T) this;
        }

        public Builder<Builder> setUsername(String userName) {

            if(TextUtils.isEmpty(userName)){
                UpaymentGatewayLog.w("userName is empty");
            }
            stringBuilder.append(KEY_USERNAME+"="+userName+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_USERNAME,userName);

            return (T) this;
        }

        public Builder<Builder> setPassword(String password) {
            if(TextUtils.isEmpty(password)){
                UpaymentGatewayLog.w("password is empty");
            }
            stringBuilder.append(KEY_PASSWORD+"="+password+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_PASSWORD,password);

            return (T) this;
        }

        public Builder<Builder> setApikey(String apiKey) {

            if(TextUtils.isEmpty(apiKey)){
                UpaymentGatewayLog.w("apiKey is empty");
            }
            stringBuilder.append(KEY_API_KEY+"="+apiKey+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_API_KEY,apiKey);

            return (T) this;
        }

        public Builder<Builder> setOrderId(String orderId) {
            if(TextUtils.isEmpty(orderId)){
                UpaymentGatewayLog.w("orderId is empty");
            }
            stringBuilder.append(KEY_ORDER_ID+"="+orderId+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_ORDER_ID,orderId);

            return (T) this;
        }

        public Builder<Builder> setTotalPrice(String total_price) {

            if(TextUtils.isEmpty(total_price)){
                UpaymentGatewayLog.w("total_price is empty");
            }
            stringBuilder.append(KEY_TOTAL_PRICE+"="+total_price+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_TOTAL_PRICE,total_price);

            return (T) this;
        }

        public Builder<Builder> setCurrencyCode(String cCode) {
            if(TextUtils.isEmpty(cCode)){
                UpaymentGatewayLog.w("cCode is empty");
            }
            stringBuilder.append(KEY_CURRENCY_CODE+"="+cCode+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_CURRENCY_CODE,cCode);

            return (T) this;

        }

        public Builder<Builder> setSuccessUrl(String successUrl) {

            if(TextUtils.isEmpty(successUrl)){
                UpaymentGatewayLog.w("cCode is empty");
            }
            stringBuilder.append(KEY_S_URL+"="+successUrl+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_S_URL,successUrl);

            return (T) this;
        }

        public Builder<Builder> setErrorUrl(String successUrl) {

            if(TextUtils.isEmpty(successUrl)){
                UpaymentGatewayLog.w("cCode is empty");
            }
            stringBuilder.append(KEY_E_URL+"="+successUrl+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_E_URL,successUrl);

            return (T) this;
        }

        public Builder<Builder> setTestMode(String testMode) {

            if(TextUtils.isEmpty(testMode)){
                UpaymentGatewayLog.w("testMode is empty");
            }
            stringBuilder.append(KEY_TEST_MODE+"="+testMode+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_TEST_MODE,testMode);

            return (T) this;
        }

        public Builder<Builder> setCustomerName(String CstFName) {
            if(TextUtils.isEmpty(CstFName)){
                UpaymentGatewayLog.w("CstFName is empty");
            }
            stringBuilder.append(KEY_CNAME+"="+CstFName+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_CNAME,CstFName);

            return (T) this;

        }

        public Builder<Builder> setCustomerEmail(String CstFEMail) {
            if(TextUtils.isEmpty(CstFEMail)){
                UpaymentGatewayLog.w("CstFEMail is empty");
            }
            stringBuilder.append(KEY_CEMAIL+"="+CstFEMail+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_CEMAIL,CstFEMail);

            return (T) this;
        }



        public Builder<Builder> setCustomerMobile(String CstMobile) {
            if(TextUtils.isEmpty(CstMobile)){
                UpaymentGatewayLog.w("CstMobile is empty");
            }
            stringBuilder.append(KEY_CMOBILE+"="+CstMobile+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_CMOBILE,CstMobile);

            return (T) this;
        }


        public Builder<Builder> setPaymentGateway(String payment_gateway) {

            if(TextUtils.isEmpty(payment_gateway)){
                UpaymentGatewayLog.w("payment_gateway is empty");
            }
            stringBuilder.append(KEY_PAYMENTGATEWAY+"="+payment_gateway+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_PAYMENTGATEWAY,payment_gateway);

            return (T) this;

        }

        public Builder<Builder> setWhitelabled(Boolean whitelabled) {

            if(whitelabled==null){
                UpaymentGatewayLog.w("whitelabled is empty");
            }
            stringBuilder.append(KEY_WHITE_ENABLED+"="+whitelabled+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_WHITE_ENABLED,whitelabled);

            return (T) this;
        }

        public Builder<Builder> setReference(String reference) {


            if(TextUtils.isEmpty(reference)){
                UpaymentGatewayLog.w("reference is empty");
            }
            stringBuilder.append(KEY_REFERENCE+"="+reference+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_REFERENCE,reference);

            return (T) this;
        }

        public Builder<Builder> setNotifyUrl(String notifyUrl) {

            if(TextUtils.isEmpty(notifyUrl)){
                UpaymentGatewayLog.w("notifyUrl is empty");
            }
            stringBuilder.append(KEY_NOTIFYURL+"="+notifyUrl+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_NOTIFYURL,notifyUrl);

            return (T) this;
        }

        public Builder<Builder> setProductTitle(String productTitle) {

            if(TextUtils.isEmpty(productTitle)){
                UpaymentGatewayLog.w("productTitle is empty");
            }
            stringBuilder.append(KEY_PRODUCT_TITLE+"="+productTitle+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_PRODUCT_TITLE,productTitle);

            return (T) this;
        }

        public Builder<Builder> setProductName(List<String> productNameList) {
            String productName=null;
            if(productNameList!=null && productNameList.size()>0){
                productName = TextUtils.join(", ", productNameList);

            }
            if(TextUtils.isEmpty(productName)){
                UpaymentGatewayLog.w("productName is empty");
            }
            stringBuilder.append(KEY_PRODUCT_NAME+"="+productName+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_PRODUCT_NAME,productName);

            return (T) this;
        }

        public Builder<Builder> setProductPrice(List<String> productPriceList) {

            String productPrice=null;
            if(productPriceList!=null && productPriceList.size()>0){
                productPrice = TextUtils.join(", ", productPriceList);

            }

            if(TextUtils.isEmpty(productPrice)){
                UpaymentGatewayLog.w("productPrice is empty");
            }
            stringBuilder.append(KEY_PRODUCT_PRICE+"="+productPrice+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_PRODUCT_PRICE,productPrice);

            return (T) this;
        }

        public Builder<Builder> setProductQty(List<String> listProductQuantity) {
            String productQuantity=null;
            if(listProductQuantity!=null && listProductQuantity.size()>0){
                productQuantity = TextUtils.join(", ", listProductQuantity);

            }

            if(TextUtils.isEmpty(productQuantity)){
                UpaymentGatewayLog.w("productQuantity is empty");
            }
            stringBuilder.append(KEY_PRODUCT_QUANTITY+"="+productQuantity+KEYHELP);
            UpaymentGatewayJsnUtils.put(AInterior,KEY_PRODUCT_QUANTITY,productQuantity);

            return (T) this;
        }
    }


}
