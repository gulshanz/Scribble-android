package com.itl.scribble.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.itl.scribble.HomeDrawer;
import com.itl.scribble.R;
import com.itl.scribble.helperClasses.Keys;
import com.itl.scribble.viewmodel_layer.LoginVM;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    private static final String TAG = "LoginTAG";
    private LoginVM viewModel;

    TextInputLayout emailInput, passwordInput;
    Button submit;
    TextView linkToSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

//        init();
        viewModel = new ViewModelProvider(this).get(LoginVM.class);
        viewModel.getProgressLiveData().observe(this, progressObserver);
        if (viewModel.chechIfIsLoggedIn()) {
            Intent intent = new Intent(this, Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
            return;
        }

        emailInput = findViewById(R.id.textInputLayout_email);
        passwordInput = findViewById(R.id.textInputLayout_password);
        submit = findViewById(R.id.submitLoginBtn);
        linkToSignup = findViewById(R.id.linkToSingup);

        Log.d(TAG, "onCreate: " + isLoggedIn());
        submit.setOnClickListener(view -> {
            String emailString = emailInput.getEditText().getText().toString();
            String passwordString = passwordInput.getEditText().getText().toString();
            if (emailString.isEmpty() || emailString == null) {
                emailInput.setError("Please enter a valid email");
                return;
            }
            if (passwordString.isEmpty() || passwordString == null) {
                passwordInput.setError("Please enter a password");
                return;
            }

            viewModel.login(emailString, passwordString);
            viewModel.getChangeIntent().observe(this, changeIntent);

        });
        linkToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });
    }

    private void setVisibilityOfProgressBar(Boolean loading) {
        ProgressBar progressBar = findViewById(R.id.progress_circular);
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    private final Observer<Boolean> progressObserver = loading -> {
        if (loading != null)
            setVisibilityOfProgressBar(loading);
    };

    private final Observer<Boolean> changeIntent = aBoolean -> {
        if (aBoolean){
            startActivity(new Intent(this,Home.class));
            finish();
        }
    };

    private void login(String emailString, String passwordString) {
        JSONObject jsonObject = new JSONObject();
        HashMap map = new HashMap();
        map.put("temp", "temp");
        try {
            jsonObject.put("email", emailString);
            jsonObject.put("password", passwordString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = Keys.SOCKET_URL + Keys.URL_LOGIN;
        JsonObjectRequest postReq = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("irogj", "onResponse: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("jeiwgr", "onErrorResponse: " + error);
            }
        });
        queue.add(postReq);
    }

    private void init() {
        boolean isLoggedIn = viewModel.chechIfIsLoggedIn();
    }


    private void updateUI(FirebaseUser user) {
        saveLoginStatusToSharedPref();
        startActivity(new Intent(Login.this, HomeDrawer.class));
        finish();
    }

    private void saveLoginStatusToSharedPref() {
        SharedPreferences sharedpreferences = getSharedPreferences("loginStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.apply();
    }

    private boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("loginStatus", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }


}