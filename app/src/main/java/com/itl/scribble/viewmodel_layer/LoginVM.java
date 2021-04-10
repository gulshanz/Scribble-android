package com.itl.scribble.viewmodel_layer;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.itl.scribble.NetworkManager;
import com.itl.scribble.helperClasses.Keys;
import com.itl.scribble.helperClasses.Strings;
import com.itl.scribble.interfaces.I_NetworkResponse;
import com.itl.scribble.serviceLayer.SharedPrefService;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;

public class LoginVM extends AndroidViewModel implements I_NetworkResponse {
    private NetworkManager networkManager;
    private MutableLiveData<Boolean> isLoggedIn;
    private MutableLiveData<Boolean> progressLiveData;
    private MutableLiveData<Boolean> changeIntent;

    /**
     * Gets the shared Preferences
     */
    private SharedPrefService prefService;


    public LoginVM(@NonNull Application application) {
        super(application);
        init();
    }

    private void init() {
        networkManager = new NetworkManager(getApplication(), this);
        progressLiveData = new MutableLiveData<>();
        prefService = SharedPrefService.getInstance(getApplication());
        progressLiveData.setValue(false);
        chechIfIsLoggedIn();
        changeIntent = new MutableLiveData<>();
        changeIntent.setValue(false);
    }


    public void login(String email, String password) {
        progressLiveData.setValue(true);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        networkManager.Volley_JsonObjectRequestWithoutHeader(Keys.SOCKET_URL
                + Keys.URL_LOGIN, null, jsonObject, Keys.TAG_LOGIN, Keys.TAG_LOGIN);

    }

    public MutableLiveData<Boolean> getProgressLiveData() {
        return progressLiveData;
    }


    public MutableLiveData<Boolean> getChangeIntent() {
        return changeIntent;
    }

    @Override
    public void getNetworkSuccessResponse(String TAG, String successResponse, String TAGFORApi) {
        Log.d("jifoegjwpo", "getNetworkSuccessResponse: " + successResponse);
        if (TAG.equals(Keys.TAG_LOGIN)) {
            try {
                JSONObject jsonObject = new JSONObject(successResponse);
                jsonObject.optString("token");
                String token = jsonObject.optString("token");
                saveLoginStatusToSharedPref();
                prefService.putStringValue(Strings.TOKEN, token);
                progressLiveData.setValue(false);
                isLoggedIn.setValue(true);
                changeIntent.setValue(true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getNetworkFailResponse(String TAG, VolleyError successResponse, String TAGFORApi) {
        Log.d("jifoegjwpo", "getNetworkSuccessResponse: " + successResponse);
        if (TAG.equals(Keys.TAG_LOGIN)) {
            progressLiveData.setValue(false);
        }
    }

    public boolean chechIfIsLoggedIn() {
        isLoggedIn = new MutableLiveData<>();
        isLoggedIn.setValue(prefService.getLoginStatus());
        return isLoggedIn.getValue();
    }

    public void signIn(String email, String password) {
//        networkManager.Volley_JsonObjectRequest();
    }

    public void saveLoginStatusToSharedPref() {
        prefService.saveLoginStatus();
    }

    public boolean isLoggedIn() {
        return prefService.getLoginStatus();
    }
}



