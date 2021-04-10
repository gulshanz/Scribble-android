package com.itl.scribble.viewmodel_layer;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
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
import java.util.HashMap;
import java.util.Locale;

public class CreateOrEditNoteVM extends AndroidViewModel implements I_NetworkResponse {
    private NetworkManager networkManager;
    private MutableLiveData<Boolean> progressLiveData;
    private MutableLiveData<Boolean> changeIntent;
    private SharedPrefService prefService;
    private MutableLiveData<String> noteOfTheDay;
    private int RecordAudioRequestCode = 12;


    public CreateOrEditNoteVM(@NonNull Application application) {
        super(application);
        init();
    }

    private void init() {
        networkManager = new NetworkManager(getApplication(), this);
        progressLiveData = new MutableLiveData<>();
        prefService = SharedPrefService.getInstance(getApplication());
        noteOfTheDay = new MutableLiveData<>();
    }


    public void hitGetNoteForTodayAPI() {
        progressLiveData.setValue(true);
        String URL = Keys.SOCKET_URL + Keys.NOTE_OF_DAY;
        networkManager.Volley_GetRequest(URL, getHeader(), Keys.TAG_GET_NOTE_FOR_A_DAY, Keys.TAG_PROFILES_NOTEFORDAY);
    }

    public void postUpdatedWrittenDataAPI(String writtenData) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("written_data", writtenData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = Keys.SOCKET_URL + Keys.NOTE_OF_DAY;
        networkManager.Volley_JsonObjectRequest(URL, getHeader(), jsonObject, Keys.TAG_POST_NOTE_FOR_A_DAY, Keys.TagForAPI_POST_NOTE_FOR_DAY);
    }

    public HashMap<String, String> getHeader() {
        HashMap<String, String> headerObj = new HashMap<>();
        headerObj.put("Content-Type", "application/json");
        headerObj.put("Authorization", "Token " + prefService.getStringValue(Strings.TOKEN));
        return headerObj;
    }


    public MutableLiveData<Boolean> getProgressLiveData() {
        return progressLiveData;
    }

    @Override
    public void getNetworkSuccessResponse(String TAG, String successResponse, String TAGFORApi) {
        if (TAG.equals(Keys.TAG_GET_NOTE_FOR_A_DAY)) {
            try {
                progressLiveData.setValue(false);
                JSONObject rootObj = new JSONObject(successResponse);
                if (rootObj.opt("message") instanceof JSONObject) {
                    JSONObject dataObj = rootObj.getJSONObject("message");
                    String writtenData = dataObj.optString("written_data");
                    noteOfTheDay.setValue(writtenData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (TAG.equals(Keys.TAG_POST_NOTE_FOR_A_DAY)) {
            try {
                JSONObject jsonObject = new JSONObject(successResponse);
                makeToast("Successfully updated");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void makeToast(String message) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
    }

    public MutableLiveData<String> getNoteOfTheDay() {
        return noteOfTheDay;
    }

    @Override
    public void getNetworkFailResponse(String TAG, VolleyError successResponse, String TAGFORApi) {
        if (TAG.equals(Keys.TAG_GET_NOTE_FOR_A_DAY)) {
            progressLiveData.setValue(false);
        }
    }


    public void checkPermission(FragmentActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity, new String[]
                    {Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
        }
    }
}
