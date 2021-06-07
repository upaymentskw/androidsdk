package com.upayments.track;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Adil on 10/03/2015.
 * Can be added to any class {@link UpaymentGatewayEvent}
 */
public class UpaymentGatewayCentricProperty {

    private final JSONObject mJson;

    UpaymentGatewayCentricProperty(Builder builder) {
        mJson = builder.mainJson;
    }

    public JSONObject getJson() {
        return mJson;
    }

    public static class Builder {
        private final JSONObject mainJson = new JSONObject();

        public Builder() {

        }

        public Builder set(String key, String... value) {
            JSONArray jsonArray = new JSONArray();
            for (String s : value) {
                jsonArray.put(s);
            }
            UpaymentGatewayJsnUtils.put(mainJson, key, jsonArray);
            return this;
        }

        public UpaymentGatewayCentricProperty build() {
            if (!mainJson.keys().hasNext()) {
                String adosisName = UpaymentGatewayCentricProperty.class.getSimpleName();
                UpaymentGatewayLog.w(adosisName + ":  you might want to provide at least one key to" +
                        " make " + adosisName + " valid");
            }
            return new UpaymentGatewayCentricProperty(this);
        }
    }


}
