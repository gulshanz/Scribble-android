package com.itl.scribble.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itl.scribble.R;
import com.itl.scribble.UserDataModal;
import com.itl.scribble.databinding.ActivitySignupBinding;
import com.itl.scribble.viewmodel_layer.SignupVM;

public class Signup extends AppCompatActivity {
    private static final String TAG = "SignupTAG";
    ActivitySignupBinding binding;
    SignupVM viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Signup");
        init();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(SignupVM.class);

        viewModel.getProgressLiveData().observe(this, progressObserver);
        viewModel.getChangeIntent().observe(this, intentObserver);
        binding.testLinkToSignin.setOnClickListener(view -> onBackPressed());
        binding.btnSubmit.setOnClickListener(view -> validateAndCallAPI());
    }

    private void validateAndCallAPI() {
        String name = binding.textFieldName.getEditText().getText().toString();
        String email = binding.textFieldEmail.getEditText().getText().toString();
        String password = binding.textFieldPassword.getEditText().getText().toString();

        if (name.isEmpty()) {
            binding.textFieldName.setError("This field can't be empty");
            return;
        }
        if (email.isEmpty()) {
            binding.textFieldEmail.setError("This field can't be empty");
            return;

        }
        if (password.isEmpty()) {
            binding.textFieldPassword.setError("This field can't be empty");
            return;
        }

        viewModel.hitSignupAPI(name, email, password);

    }

    private final Observer<Boolean> progressObserver = loading -> {
        if (loading != null) {
            setVisibilityOfProgressBar(loading);
        }
    };

    private final Observer<Boolean> intentObserver = changeIntent -> {
        if (changeIntent != null) {
            if (changeIntent)
                onBackPressed();
        }
    };

    private void setVisibilityOfProgressBar(Boolean loading) {
        binding.progressCircular.setVisibility(loading ? View.VISIBLE : View.INVISIBLE);
    }


}