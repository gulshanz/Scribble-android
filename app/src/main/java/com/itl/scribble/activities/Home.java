package com.itl.scribble.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;

import com.android.volley.VolleyError;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.itl.scribble.NetworkManager;
import com.itl.scribble.OldNotesObj;
import com.itl.scribble.R;
import com.itl.scribble.databinding.ActivityHomeBinding;
import com.itl.scribble.dialogs.HomeButtonDialog;
import com.itl.scribble.helperClasses.Keys;
import com.itl.scribble.interfaces.I_NetworkResponse;
import com.itl.scribble.serviceLayer.SharedPrefService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity implements I_NetworkResponse {

    ActivityHomeBinding binding;
    SharedPrefService prefService;
    private NetworkManager networkManager;
    HashMap<Long, OldNotesObj> notesMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        prefService = SharedPrefService.getInstance(this);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logOut();
            default:
                return false;
        }
    }

    public void logOut() {
        removeDataFromSharedPref();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    private void removeDataFromSharedPref() {
        prefService.clearSharedPref();
    }


    private void init() {
        networkManager = new NetworkManager(getApplication(), this);

        notesMap = new HashMap<>();
        binding.circleIvProfile.setOnClickListener(view ->
                startActivity(new Intent(this, Profiles.class)));

        binding.textOldScribble.setOnClickListener(view ->
                startActivity(new Intent(this, OldNotes.class)));

        binding.cardDateCard.setOnClickListener(view ->
                new HomeButtonDialog().show(getSupportFragmentManager(), "tempTag"));

        binding.calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Log.d("iowgrji", "i=" + i + ", i1=" + i1 + "," + "i2:" + i2);
                int year=i;
                DecimalFormat df=new DecimalFormat("00");
                String month=df.format(i1+1);
                String day=df.format(i2);
                String dateString= (year+""+month+""+day);
                Long key=Long.parseLong(dateString);
                OldNotesObj note=notesMap.get(key);
                if (note!=null){
                    Intent intent = new Intent(Home.this, ShowPostsAct.class);
                    intent.putExtra("data", note);
                    startActivity(intent);
                }else{
                    Snackbar.make(findViewById(android.R.id.content),"There is no data on" +
                            " selected date", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        });
        getNotesList();
    }

    public void getNotesList() {
        String URL = Keys.SOCKET_URL + Keys.URL_NOTELIST;
        networkManager.Volley_GetRequest(URL, null, Keys.TAG_GET_NOTE_LIST, Keys.TagForAPI_GET_NOTE_LIST);
    }

    @Override
    public void getNetworkSuccessResponse(String TAG, String successResponse, String TAGFORApi) {
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
                            addMappingToHashMap(list);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void addMappingToHashMap(List<OldNotesObj> list) {
        for (int i = 0; i < list.size(); i++) {
            String dateString=list.get(i).getCreated_on().replace("-","");
            Long key= Long.valueOf(dateString);
            notesMap.put(key, list.get(i));
            Log.d("fiewjofw", list.get(i).getCreated_on());
        }
    }

    @Override
    public void getNetworkFailResponse(String TAG, VolleyError successResponse, String TAGFORApi) {

    }
}