package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.upayments.UPaymentCallBack;
import com.upayments.activity.PostUpayData;
import com.upayments.track.UpaymentGateway;
import com.upayments.track.UpaymentGatewayEvent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UPaymentCallBack {

    Button login= null;
    private EditText tvMerchantId,tvUserName,password,tvApiKey,tvOrderId,tvPrice,tvCurrency_code,tvSuccessUrl;
    private EditText tvErrorUrl,tvTestMode,tvCustomerName,tvCustomerEmail,tvCustomerMobile,tvPaymentGateway;
    private EditText tvWhiteLabel,tvProductTitle,tvProductName,tvProductPrice,tvProductQuantity,tvReference,tvNotifyUrl;

    private int add= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login = (Button)findViewById(R.id.login);
        tvMerchantId=  (EditText)findViewById(R.id.tvMerchantId);
        tvUserName=  (EditText)findViewById(R.id.tvUserName);
        password=  (EditText)findViewById(R.id.password);
        tvApiKey=  (EditText)findViewById(R.id.tvApiKey);

        tvOrderId=  (EditText)findViewById(R.id.tvOrderId);
        tvPrice=  (EditText)findViewById(R.id.tvPrice);
        tvCurrency_code= (EditText)findViewById(R.id.tvCurrency_code);
        tvSuccessUrl= (EditText)findViewById(R.id.tvSuccessUrl);
        tvErrorUrl= (EditText)findViewById(R.id.tvErrorUrl);

        tvTestMode= (EditText)findViewById(R.id.tvTestMode);
        tvCustomerName= (EditText)findViewById(R.id.tvCustomerName);
        tvCustomerEmail= (EditText)findViewById(R.id.tvCustomerEmail);
        tvCustomerMobile= (EditText)findViewById(R.id.tvCustomerMobile);
        tvPaymentGateway= (EditText)findViewById(R.id.tvPaymentGateway);
        tvWhiteLabel= (EditText)findViewById(R.id.tvWhiteLabel);
        tvProductTitle= (EditText)findViewById(R.id.tvProductTitle);
        tvProductName= (EditText)findViewById(R.id.tvProductName);
        tvProductPrice= (EditText)findViewById(R.id.tvProductPrice);
        tvProductQuantity= (EditText)findViewById(R.id.tvProductQuantity);
        tvReference = (EditText)findViewById(R.id.tvReference);
        tvNotifyUrl = (EditText)findViewById(R.id.tvNotifyUrl);




        List<String> listProductName = new ArrayList<String>();
        listProductName.add("product1");
        listProductName.add("product2");

        List<String> listProductPrice = new ArrayList<String>();
        listProductPrice.add("12");
        listProductPrice.add("14");

        List<String> listProductQuantity = new ArrayList<String>();
        listProductQuantity.add("2");
        listProductQuantity.add("4");

        // Call this from Application class one time dont call again again
       UpaymentGateway.init(this,"","",true);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // add =add+1;

//                UpaymentGatewayEvent analyticsEvent2 = new UpaymentGatewayEvent.Builder("1201")
//                        .setMerchantId("1201")
//                        .setUsername("test")
//                        .setPassword("test")
//                        .setApikey("jtest123")
//                        .setOrderId("12345")
//                        .setTotalPrice("3.000")
//                        .setCurrencyCode("NA")
//                        .setSuccessUrl("https://example.com/success.html")
//                        .setErrorUrl("https://example.com/error.html")
//                        .setTestMode("1")
//                        .setCustomerName("test23")
//                        .setCustomerEmail("test23@gmail.com")
//                        .setCustomerMobile("12345678")
//                        .setPaymentGateway("knet")
//                        .setWhitelabled(true)
//                        .setProductTitle("productTitle")
//                        .setProductName(listProductName)
//                        .setProductPrice(listProductPrice)
//                        .setProductQty(listProductQuantity)
//                        .setReference("Ref00001")
//                        .setNotifyUrl("https://example.com/success.html")
//                        .build();
//                UpaymentGateway.getInstance().track(analyticsEvent2, MainActivity.this);

                String mMerchantID= tvMerchantId.getText().toString();

                String mUserName= tvUserName.getText().toString();

                String mpassword = password.getText().toString();

                String mapiKey = tvApiKey.getText().toString();

                String mOrderId = tvOrderId.getText().toString();
                String mPrice = tvPrice.getText().toString();
                String mCurrency_code = tvCurrency_code.getText().toString();
                String mSuccessUrl = tvSuccessUrl.getText().toString();
                String mErrorUrl = tvErrorUrl.getText().toString();
                String mTestMode = tvTestMode.getText().toString();
                String mCustomerName = tvCustomerName.getText().toString();
                String mCustomerEmail = tvCustomerEmail.getText().toString();

                String mCustomerMobile = tvCustomerMobile.getText().toString();
                String mPaymentGateway = tvPaymentGateway.getText().toString();
                String mWhiteLabel = tvWhiteLabel.getText().toString();
                String mProductTitle = tvProductTitle.getText().toString();

                String mProductName = tvProductName.getText().toString();
                String mProductPrice = tvProductPrice.getText().toString();
                String mProductQuantity = tvProductQuantity.getText().toString();
                String mReference = tvReference.getText().toString();
                String mNotifyUrl = tvNotifyUrl.getText().toString();

               //
                UpaymentGatewayEvent analyticsEvent3 = new UpaymentGatewayEvent.Builder(mMerchantID)

                        .setMerchantId(mMerchantID)
                        .setUsername(mUserName)
                        .setPassword(mpassword)
                        .setApikey(mapiKey)

                        .setOrderId(mOrderId)
                        .setTotalPrice(mPrice)
                        .setCurrencyCode(mCurrency_code)
                        .setSuccessUrl(mSuccessUrl)
                        .setErrorUrl(mErrorUrl)
                        .setTestMode(mTestMode)
                        .setCustomerName(mCustomerName)
                        .setCustomerEmail(mCustomerEmail)
                        .setCustomerMobile(mCustomerMobile)
                        .setPaymentGateway(mPaymentGateway)
                        .setWhitelabled(true)
                        .setProductTitle(mProductTitle)
                        .setProductName(listProductName)
                        .setProductPrice(listProductPrice)
                        .setProductQty(listProductQuantity)
                        .setReference(mReference)
                        .setNotifyUrl(mNotifyUrl)
                        .build();
                UpaymentGateway.getInstance().track(analyticsEvent3, MainActivity.this);
            }
        });

    }

    @Override
    public void callBackUpayment(PostUpayData postUpayData) {
        if(postUpayData!=null){
          String payId=  postUpayData.getPayMentId();
            String Result=  postUpayData.getResult();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(MainActivity.this, "PaymentId : "+payId + " >Result :"+Result, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void errorPayUpayment(String data) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(MainActivity.this, "Failed : "+data , Toast.LENGTH_SHORT).show();
            }
        });
    }
}