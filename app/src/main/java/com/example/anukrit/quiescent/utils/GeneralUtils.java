package com.example.anukrit.quiescent.utils;


import android.content.Context;

import android.widget.Toast;

import com.example.anukrit.quiescent.BuildConfig;


public class GeneralUtils {
    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }



    public static void debugToast(Context context, String message) {
        if (BuildConfig.DEBUG)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void toastLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    public static boolean isNull(String str) {
        return str == null || str.isEmpty() || str.equals("null");
    }


}
