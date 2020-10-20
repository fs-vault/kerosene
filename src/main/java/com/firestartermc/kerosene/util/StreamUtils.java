package com.firestartermc.kerosene.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class StreamUtils {

    private StreamUtils() {
    }

    public static String streamToString(InputStream is) {
        if (is == null) {
            return null;
        }

        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str).append(System.getProperty("line.separator"));
            }
            return sb.toString();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
