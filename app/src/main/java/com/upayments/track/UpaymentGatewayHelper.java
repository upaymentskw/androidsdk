package com.upayments.track;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Adil on 07/03/2015.
 */
class UpaymentGatewayHelper {

    private static final String HOST_REGEX = "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*" +
            "([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";

    static boolean isPermissionGranted(Context context, String permission) {
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    static boolean isHostValid(String domain) {
        final Matcher contentMatcher = Pattern.compile(HOST_REGEX).matcher(domain);
        return contentMatcher.find();
    }

    public static CharSequence toString(InputStream is) {
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            StringBuilder total = new StringBuilder(is.available());
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            return total;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertToString(ArrayList<String> list) {

        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (String s : list)
        {
            sb.append(delim);
            sb.append(s);;
            delim = ",";
        }
        return sb.toString();
    }

    public static ArrayList<String> convertToArray(String string) {

        ArrayList<String> list = new ArrayList<String>(Arrays.asList(string.split(",")));
        return list;
    }

    public static boolean checkExit(ArrayList<String> list ,String event){

        if(list!=null && list.size()>0 && !TextUtils.isEmpty(event)){
            for(int i= 0 ; i <list.size(); i++){

                if(list.get(i).equalsIgnoreCase(event))
                    return true ;

            }
        }


        return false;
    }


}
