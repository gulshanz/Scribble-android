package com.itl.scribble.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.itl.scribble.databinding.FragmentProfileBinding;
import com.itl.scribble.serviceLayer.SharedPrefService;

public class Profiles extends AppCompatActivity {

    FragmentProfileBinding binding;
    SharedPrefService prefService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=FragmentProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        prefService = SharedPrefService.getInstance(this);

        binding.btnLogout.setOnClickListener(view1 -> {
            removeDataFromSharedPref();
            Intent intent = new Intent(Profiles.this, Login.class);
            startActivity(intent);
            finish();
        });
    }
    private void removeDataFromSharedPref() {
        prefService.clearSharedPref();
    }
}
