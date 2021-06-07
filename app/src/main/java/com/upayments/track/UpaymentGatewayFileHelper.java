package com.upayments.track;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adil on 15/03/2015.
 */
class UpaymentGatewayFileHelper {

    private static final String FILENAME = "uPayment.txt";
    private static final String FILENAME_TEMP = "uPayment-temp.text";
    private static final String SEPARATOR = System.getProperty("line.separator");

    static List<String> getLines() {
        List<String> res = new ArrayList<>();
        try {
            InputStream is = UpaymentGateway.getContext().openFileInput(FILENAME);
            if (is != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    res.add(line);
                }
                is.close();
            }
        } catch (FileNotFoundException e1) {
            // no file because no properties has been stored yet
        } catch (IOException e) {
            UpaymentGatewayLog.e("Unable to get lines from file. Exception: " + e);
        }
        return res;
    }

    static boolean deleteLines(int numberOfLineToDelete) {
        File fileDir = UpaymentGateway.getContext().getFilesDir();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(UpaymentGateway.getContext().openFileInput
                    (FILENAME)));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(UpaymentGateway.getContext().openFileOutput
                    (FILENAME_TEMP, Context.MODE_APPEND)));

            int counter = -1;
            String line;
            while ((line = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                counter++;
                if (counter < numberOfLineToDelete) {
                    continue;
                }
                writer.write(line + SEPARATOR);
            }
            writer.close();
            reader.close();
        } catch (IOException e) {
            UpaymentGatewayLog.e("Unable to delete first line");
        }
        return new File(fileDir, FILENAME_TEMP).renameTo(new File(fileDir, FILENAME));
    }

    public static void appendLine(String jsonProperties) {
        OutputStreamWriter osw;
        try {
            osw = new OutputStreamWriter(UpaymentGateway.getContext().openFileOutput(FILENAME, Context.MODE_APPEND));
            osw.append(jsonProperties);
            osw.append(SEPARATOR);
            osw.close();
            UpaymentGatewayLog.d("-> properties stored.");
        } catch (IOException e) {
            UpaymentGatewayLog.e("Unable to store properties: " + jsonProperties);
        }
    }

}
