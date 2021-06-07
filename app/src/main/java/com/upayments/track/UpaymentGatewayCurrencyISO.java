package com.upayments.track;

/**
* Created by Adil on 12/03/2015.
*/
public enum UpaymentGatewayCurrencyISO {
    KWD("KWD"),
    GBP("GBP"),
    EUR("EUR");
    final String value;

    UpaymentGatewayCurrencyISO(String s) {
        this.value = s;
    }
}
