package com.example.myapplication;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Constants {
    private static final String DEBUG_URL = "http://10.0.2.2:8000/";
    public static final String BASE_URL = "http://blank-dictionary.herokuapp.com/";
    public static final String DOWNLOAD_URL_PART = "api/";
    public static final String STATUS_URL_PART = "status/";
    public static final String DICT_LIST_URL_PART = "all/";



    public static final String CODE = "Az39dB0n!23";

    //
    public static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static String authDigest(String Code) {

        byte[] data1 = CODE.getBytes();
        String output = "";

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            data1 = md.digest(data1);

            output = String.format("%02x", new BigInteger(1, data1));

        }  catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return output;
    }
}
