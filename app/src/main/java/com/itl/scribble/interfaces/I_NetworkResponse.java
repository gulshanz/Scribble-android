package com.itl.scribble.interfaces;

import com.android.volley.VolleyError;

public interface I_NetworkResponse {

    void getNetworkSuccessResponse(String TAG, String successResponse, String TAGFORApi);

    void getNetworkFailResponse(String TAG, VolleyError successResponse, String TAGFORApi);

}
