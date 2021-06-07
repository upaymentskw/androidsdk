package com.upayments.track;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Adil on 09/03/2021.
 * Can be added to any class {@link UpaymentGatewayEvent}
 */
public class UpaymentGatewayAction {

    private static final String KEY_REF = "ref";
    private static final String KEY_IN = "in";
    private static final String KEY_OUT = "out";

    private JSONObject mJson;

    private UpaymentGatewayAction(Builder builder) {
        mJson = builder.mainJson;
    }

    JSONObject getJson() {
        return mJson;
    }

    public static class Builder {
        private final JSONObject mainJson = new JSONObject();
        private final JSONArray outsJson = new JSONArray();

        public Builder setReference(String ref) {
            UpaymentGatewayJsnUtils.put(mainJson, KEY_REF, ref);
            return this;
        }

        public Builder setIn(String in) {
            UpaymentGatewayJsnUtils.put(mainJson, KEY_IN, in);
            return this;
        }

        public Builder addOut(String... outs) {
            for (String out : outs) {
                outsJson.put(out);
            }
            return this;
        }

        public UpaymentGatewayAction build() {
            if (outsJson.length() != 0) {
                UpaymentGatewayJsnUtils.put(mainJson, KEY_OUT, outsJson);
            }
            if (!mainJson.has(KEY_IN) && !mainJson.has(KEY_OUT)) {
                UpaymentGatewayLog.w(UpaymentGatewayAction.class.getSimpleName() + ": one of setIn() or addOut() method must be called to create " +
                        "a valid AdosizAnalyticsAction.");
            }
            return new UpaymentGatewayAction(this);
        }
    }


}
