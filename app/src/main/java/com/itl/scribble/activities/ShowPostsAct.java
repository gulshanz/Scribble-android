package com.itl.scribble.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.itl.scribble.OldNotesObj;
import com.itl.scribble.R;

public class ShowPostsAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_posts);
        Intent intent=getIntent();
        Bundle model=intent.getExtras();
        OldNotesObj data= (OldNotesObj) model.getSerializable("data");
        showOnScreen(data);
    }

    private void showOnScreen(OldNotesObj data) {
        TextView title=findViewById(R.id.postTitle);
        TextView content=findViewById(R.id.postContent);
        title.setText(data.getCreated_on());
        content.setText(data.getWritten_data());
    }
}