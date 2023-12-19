package com.example.mymap.controller;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Appcontroller extends Application {
    private static Appcontroller instance;
    private RequestQueue requestQueue;

    public static synchronized Appcontroller getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue==null){
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }
    public <T> void addrequestqueue(Request <T> req){
        getRequestQueue().add(req);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
}
