package com.itl.scribble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.itl.scribble.activities.Login;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new java.util.Timer().schedule(
                new java.util.TimerTask(){

                    @Override
                    public void run() {
                        Intent intent=new Intent(SplashScreen.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                },2000
        );
    }
}