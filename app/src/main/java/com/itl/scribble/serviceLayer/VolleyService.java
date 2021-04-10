package com.itl.scribble.serviceLayer;

import android.content.Context;

import com.android.volley.BuildConfig;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class VolleyService {
    private static VolleyService mInstance;
    private RequestQueue mRequestQueue;
//    private Map<String, String> VcsHeader;

    //creates a new object
    private VolleyService(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
//        VcsHeader = new HashMap<>();
//        VcsHeader.put("clientcode", String.valueOf(BuildConfig.VERSION_CODE));
    }

    //Make sure that we are creating only one object in entire application
    public static synchronized VolleyService getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyService(context);
        }
        return mInstance;
    }

    //This gives the Request Queue. If not created then will create one
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

//    public Map<String, String> getVcsHeader() {
////        return VcsHeader;
//    }
}
