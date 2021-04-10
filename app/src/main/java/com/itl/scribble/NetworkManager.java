package com.itl.scribble;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.itl.scribble.helperClasses.Keys;
import com.itl.scribble.helperClasses.Strings;
import com.itl.scribble.interfaces.I_NetworkResponse;
import com.itl.scribble.serviceLayer.SharedPrefService;
import com.itl.scribble.serviceLayer.VolleyService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetworkManager {
    public final RequestQueue requestQueue;
    public final I_NetworkResponse mNetworkResponse;
    public static final int VOLLEY_MAX_RETRIES = 1;
    private SharedPrefService prefService;


    private static final float VOLLEY_BACKOFF_MULTIPLIERS = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;

    private static DefaultRetryPolicy networkPolicy;

    public NetworkManager(Context ApplicationContext, I_NetworkResponse responseCallBack) {
        requestQueue = VolleyService.getInstance(ApplicationContext).getRequestQueue();
        mNetworkResponse = responseCallBack;
        prefService = SharedPrefService.getInstance(ApplicationContext);
        createNetworkPolicy();
    }

    public void createNetworkPolicy() {
        if (networkPolicy == null) {
            networkPolicy = new DefaultRetryPolicy(Keys.getTIMEOUT(),
                    VOLLEY_MAX_RETRIES,
                    VOLLEY_BACKOFF_MULTIPLIERS);
        }
    }


    public HashMap<String, String> getHeader() {
        HashMap<String, String> headerObj = new HashMap<>();
        headerObj.put("Content-Type", "application/json");
        headerObj.put("Authorization", "Token " + prefService.getStringValue(Strings.TOKEN));
        return headerObj;
    }


    public void Volley_JsonObjectRequest(String URL, final HashMap<String, String> headerObj, JSONObject reqObj, final String TAG, String TAG_FOR_API) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, URL, reqObj,
                response -> mNetworkResponse.getNetworkSuccessResponse(
                        TAG, response.toString(), TAG_FOR_API),
                error -> mNetworkResponse.getNetworkFailResponse(
                        TAG, error, TAG_FOR_API)) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headerObj == null)
                    return getHeader();
                return headerObj;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                return super.parseNetworkResponse(response);
            }
        };
        jsonObjectRequest.setRetryPolicy(networkPolicy);
        jsonObjectRequest.setTag(TAG);
        requestQueue.add(jsonObjectRequest);
    }


    public void Volley_JsonObjectRequestWithoutHeader(String URL, final HashMap<String, String> headerObj, JSONObject reqObj, final String TAG, String TAG_FOR_API) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, URL, reqObj,
                response -> mNetworkResponse.getNetworkSuccessResponse(
                        TAG, response.toString(), TAG_FOR_API),
                error -> mNetworkResponse.getNetworkFailResponse(
                        TAG, error, TAG_FOR_API));
        jsonObjectRequest.setRetryPolicy(networkPolicy);
        jsonObjectRequest.setTag(TAG);
        requestQueue.add(jsonObjectRequest);
    }


    public void Volley_GetRequest(String URL, final HashMap<String, String> headerObj, String TAG
            , String TAG_FOR_API) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                response -> {
                    mNetworkResponse.getNetworkSuccessResponse(TAG, response, TAG_FOR_API);
                }, error -> {
            mNetworkResponse.getNetworkFailResponse(TAG, error, TAG_FOR_API);
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headerObj==null)
                    return getHeader();
                return headerObj;
            }
        };
        stringRequest.setRetryPolicy(networkPolicy);
        stringRequest.setTag(TAG);
        requestQueue.add(stringRequest);
    }
}
