package com.itl.scribble.viewmodel_layer;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.itl.scribble.NetworkManager;
import com.itl.scribble.helperClasses.Keys;
import com.itl.scribble.interfaces.I_NetworkResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupVM extends AndroidViewModel implements I_NetworkResponse {
    private MutableLiveData<Boolean> progressLiveData;
    private MutableLiveData<Boolean> changeIntent;
    NetworkManager networkManager;

    public MutableLiveData<Boolean> getProgressLiveData() {
        return progressLiveData;
    }

    public MutableLiveData<Boolean> getChangeIntent() {
        return changeIntent;
    }

    public SignupVM(@NonNull Application application) {
        super(application);
        init();
    }

    private void init() {
        progressLiveData = new MutableLiveData<>();
        changeIntent = new MutableLiveData<>();
        progressLiveData.setValue(false);
        changeIntent.setValue(false);
        networkManager = new NetworkManager(getApplication(), this);
    }


    public void hitSignupAPI(String name, String email, String password) {
        progressLiveData.setValue(true);
        String URL = Keys.SOCKET_URL + Keys.URL_REGISTRATION;
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("email", email);
            jsonObject.put("name", name);
            jsonObject.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("wioeghwioe", "hitSignupAPI: " + jsonObject);
        networkManager.Volley_JsonObjectRequestWithoutHeader(URL, null, jsonObject, Keys.TAG_POST_REGISTER, Keys.TagForAPIRegister);
    }

    @Override
    public void getNetworkSuccessResponse(String TAG, String successResponse, String TAGFORApi) {
        if (TAG.equals(Keys.TAG_POST_REGISTER)) {
            Log.d(TAG, "getNetworkSuccessResponse: " + successResponse);
            progressLiveData.setValue(false);
            if (successResponse.contains("message")) {
                makeToast("Created Successfully");
                changeIntent.setValue(true);
            }
        }
    }

    private void makeToast(String s) {
        Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getNetworkFailResponse(String TAG, VolleyError successResponse, String TAGFORApi) {
        if (TAG.equals(Keys.TAG_POST_REGISTER)) {
            progressLiveData.setValue(false);
            if (successResponse.toString().contains("message")) {
                changeIntent.setValue(true);
                makeToast("Failed to create");
            }
        }
    }
}
