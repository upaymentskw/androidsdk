package com.upayments.track;

import org.json.JSONObject;

/**
 * Created by Adil on 10/11/2016
 */
public class UpaymentGatewayParams {

    private final JSONObject mJson;

    public UpaymentGatewayParams(Builder builder) {
        this.mJson = builder.mainJson;
    }

    public JSONObject getJson() {
        return mJson;
    }

    public static class Builder {
        private final JSONObject mainJson = new JSONObject();

        public Builder addParam(String key, String value) {
            UpaymentGatewayJsnUtils.put(mainJson, key, value);
            return this;
        }

        public Builder addParam(String key, int value) {
            UpaymentGatewayJsnUtils.put(mainJson, key, value);
            return this;
        }

        public UpaymentGatewayParams build() {
            return new UpaymentGatewayParams(this);
        }

    }


}
