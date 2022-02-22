package com.example.welcome;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.upayments.UPaymentCallBack;
import com.upayments.activity.PostUpayData;
import com.upayments.track.UpaymentGateway;
import com.upayments.track.UpaymentGatewayEvent;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements UPaymentCallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Call this from Application class 
        UpaymentGateway.init(this,"","",true);



        Button login = findViewById(R.id.login);
        EditText tvMerchantId = findViewById(R.id.tvMerchantId) ;
        EditText  tvUserName = findViewById(R.id.tvUserName) ;
        EditText    password = findViewById(R.id.password) ;
        EditText  tvApiKey = findViewById(R.id.tvApiKey) ;
        EditText   tvOrderId = findViewById(R.id.tvOrderId) ;
        EditText    tvPrice = findViewById(R.id.tvPrice) ;
        EditText  tvCurrency_code = findViewById(R.id.tvCurrency_code) ;
        EditText   tvSuccessUrl = findViewById(R.id.tvSuccessUrl) ;
        EditText  tvErrorUrl = findViewById(R.id.tvErrorUrl) ;
        EditText  tvTestMode = findViewById(R.id.tvTestMode) ;
        EditText tvCustomerName = findViewById(R.id.tvCustomerName) ;
        EditText  tvCustomerEmail = findViewById(R.id.tvCustomerEmail) ;
        EditText   tvCustomerMobile = findViewById(R.id.tvCustomerMobile) ;
        EditText  tvPaymentGateway = findViewById(R.id.tvPaymentGateway) ;
        EditText   tvWhiteLabel = findViewById(R.id.tvWhiteLabel) ;
        EditText tvProductTitle = findViewById(R.id.tvProductTitle) ;
        EditText   tvProductName = findViewById(R.id.tvProductName) ;
        EditText  tvProductPrice = findViewById(R.id.tvProductPrice) ;
        EditText   tvProductQuantity = findViewById(R.id.tvProductQuantity) ;
        EditText   tvReference = findViewById(R.id.tvReference) ;
        EditText   tvNotifyUrl = findViewById(R.id.tvNotifyUrl) ;

        ArrayList<String> listProductName  = new ArrayList<String>();
        listProductName.add("product1");
        listProductName.add("product2");
        ArrayList<String> listProductPrice  = new ArrayList<String>();
        listProductPrice.add("12");
        listProductPrice.add("14");
        ArrayList<String> listProductQuantity  = new ArrayList<String>();
        listProductQuantity.add("2");
        listProductQuantity.add("4");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpaymentGatewayEvent analyticsEvent2  = new UpaymentGatewayEvent.Builder("1201")
                        .setMerchantId("1201")
                        .setUsername("test")
                        .setPassword("test")
                        .setApikey("jtest123")
                        .setOrderId("12345")
                        .setTotalPrice("10.000")
                        .setCurrencyCode("NA")
                        .setSuccessUrl("https://example.com/success.html")
                        .setErrorUrl("https://example.com/error.html")
                        .setTestMode("1")
                        .setCustomerName("ravi23")
                        .setCustomerEmail("ravi23@gmail.com")
                        .setCustomerMobile("99887766")
                        .setPaymentGateway("knet")
                        .setWhitelabled(true)
                        .setProductTitle("productTitle")
                        .setProductName(listProductName)
                        .setProductPrice(listProductPrice)
                        .setProductQty(listProductQuantity)
                        .setReference("Ref00001")
                        .setNotifyUrl("https://example.com/success.html")
                        .build();
                UpaymentGateway.getInstance().track(analyticsEvent2, MainActivity.this);
            }
        });



    }

    @Override
    public void callBackUpayment(PostUpayData postUpayData) {
        if (postUpayData != null) {
            String payId = postUpayData.getPayMentId();
            String Result = postUpayData.getResult();
            String auth = postUpayData.getAuth();

            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(MainActivity.this, "PaymentId :"+ payId +">Result :"+Result+"Auth value:"+ auth, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void errorPayUpayment(String data) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(MainActivity.this, "Failed :"+ data  , Toast.LENGTH_SHORT).show();
            }
        });
    }
}