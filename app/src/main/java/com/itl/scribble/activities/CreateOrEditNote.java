package com.itl.scribble.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.itl.scribble.R;
import com.itl.scribble.helperClasses.Keys;
import com.itl.scribble.viewmodel_layer.CreateOrEditNoteVM;

import java.util.ArrayList;
import java.util.Locale;

public class CreateOrEditNote extends AppCompatActivity {
    private static final String TAG = "HomeTAG";
    CreateOrEditNoteVM viewModel;
    private SpeechRecognizer speechRecognizer;
    Intent speechRecognizerIntent;
    MaterialButton micBtn;
    EditText todaysData;
    ProgressBar progressBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        init();
    }

    private void init() {
        todaysData = findViewById(R.id.todaysDataEt);
        micBtn = findViewById(R.id.imageButton_mic);
        progressBar = findViewById(R.id.progressBarTodayData);
        viewModel = new ViewModelProvider(this).get(CreateOrEditNoteVM.class);
        viewModel.hitGetNoteForTodayAPI();
        viewModel.getNoteOfTheDay().observe(this, noteObserver);
        viewModel.getProgressLiveData().observe(this, progressObserver);
        todaysData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("ioqwefji", charSequence + "");

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        addSpokenContentsToET(this);
        micBtn.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                speechRecognizer.startListening(speechRecognizerIntent);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                speechRecognizer.stopListening();
            }
            return false;
        });

    }

    public void addSpokenContentsToET(FragmentActivity activity) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            viewModel.checkPermission(activity);
        }
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(activity);
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                makeToast("Listening...");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String recognizedData = data.get(0);
                if (viewModel.getNoteOfTheDay().getValue() != null) {
                    viewModel.getNoteOfTheDay().setValue(viewModel.getNoteOfTheDay().getValue() + " " + recognizedData);
                } else {
                    viewModel.getNoteOfTheDay().setValue(recognizedData);
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
    }

    private void makeToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.postUpdatedWrittenDataAPI(todaysData.getText().toString());
    }


    private final Observer<Boolean> progressObserver = loading -> {
        if (loading != null)
            setVisibilityOfProgressBar(loading);
    };

    private final Observer noteObserver = note -> {
        if (note != null) {
            todaysData.setText(note.toString());
        }
    };

    private void setVisibilityOfProgressBar(Boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }


}
