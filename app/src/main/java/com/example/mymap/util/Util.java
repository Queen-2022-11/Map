package com.example.mymap.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Util {
    public static final String ParkUrl="https://developer.nps.gov/api/v1/parks?parkcode=wa&api_key=n5RHPlEZWdczAKMJHgQVUeFhxZscPitIE8FMqPWB";
    public static String getParksUrl(String stateCode) {
        return "https://developer.nps.gov/api/v1/parks?parkcode="+stateCode+"&api_key=n5RHPlEZWdczAKMJHgQVUeFhxZscPitIE8FMqPWB";
    }
    public static void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

}

