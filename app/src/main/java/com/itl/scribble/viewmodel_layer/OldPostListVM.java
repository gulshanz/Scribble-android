package com.itl.scribble.viewmodel_layer;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.itl.scribble.NetworkManager;
import com.itl.scribble.OldNotesObj;
import com.itl.scribble.helperClasses.Keys;
import com.itl.scribble.interfaces.I_NetworkResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OldPostListVM extends AndroidViewModel implements I_NetworkResponse {

    private NetworkManager networkManager;
    private MutableLiveData<Boolean> progressLiveData;
    private MutableLiveData<List<OldNotesObj>> noteList;

    public MutableLiveData<Boolean> getProgressLiveData() {
        return progressLiveData;
    }

    public MutableLiveData<List<OldNotesObj>> getNoteList() {
        return noteList;
    }

    public OldPostListVM(@NonNull Application application) {
        super(application);
        init();
    }

    private void init() {
        networkManager = new NetworkManager(getApplication(), this);
        progressLiveData = new MutableLiveData<>();
        noteList = new MutableLiveData<>();
    }

    public void getNotesList() {
        String URL = Keys.SOCKET_URL + Keys.URL_NOTELIST;
        networkManager.Volley_GetRequest(URL, null, Keys.TAG_GET_NOTE_LIST, Keys.TagForAPI_GET_NOTE_LIST);
    }

    @Override
    public void getNetworkSuccessResponse(String TAG, String successResponse, String TAGFORApi) {
        Log.d("feiwofgiow", "getNetworkSuccessResponse: " + successResponse);
        if (TAG.equals(Keys.TAG_GET_NOTE_LIST)) {
            try {
                JSONObject rootObj = new JSONObject(successResponse);
                JSONArray jsonArray = rootObj.optJSONArray("data");
                if (jsonArray != null) {

                    if (jsonArray.length() > 0) {
                        List<OldNotesObj> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String written_data = object.getString("written_data");
                            String created_on = object.getString("created_on");
                            int id = object.getInt("id");
                            int user_id = object.getInt("user_profile_id");
                            OldNotesObj notesObj = new OldNotesObj(id, user_id, written_data, created_on);
                            list.add(notesObj);
                        }
                        noteList.setValue(list);
                        Log.d("jiewgjiowj", "getNetworkSuccessResponse: " + noteList.getValue());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getNetworkFailResponse(String TAG, VolleyError successResponse, String TAGFORApi) {
        if (TAG.equals(Keys.TAG_GET_NOTE_LIST)) {
            Log.d("feiwofgiow", "failResponse: " + successResponse);
        }
    }
}
