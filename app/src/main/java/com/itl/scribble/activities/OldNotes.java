package com.itl.scribble.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itl.scribble.OldNotesObj;
import com.itl.scribble.adapter.OldPostAdapter;
import com.itl.scribble.databinding.FragmentOldPostsBinding;
import com.itl.scribble.interfaces.ListItemClickListener;
import com.itl.scribble.viewmodel_layer.OldPostListVM;

import java.util.ArrayList;
import java.util.List;

public class OldNotes extends AppCompatActivity implements ListItemClickListener {

    List<OldNotesObj> oldScribbleList;
    TextView noPastScribble;
    RecyclerView mRecyclerView;
    OldPostAdapter mAdapter;
    OldPostListVM viewModel;
    FragmentOldPostsBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentOldPostsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(OldPostListVM.class);

        viewModel.getNoteList().observe(this, this::convertAndAddToAdapterList);
        getOldPosts();
    }

    private void convertAndAddToAdapterList(List<OldNotesObj> list) {
        List<OldNotesObj> convertedList = new ArrayList<>();
        Log.d("fueiowghio", "convertAndAddToAdapterList: " + list.toString());
        oldScribbleList = list;
//        oldScribbleList=convertedList;
        initializeRV();
    }


    public void getOldPosts() {
        viewModel.getNotesList();
    }

    private void initializeRV() {
        if (oldScribbleList.isEmpty()) {
            noPastScribble.setVisibility(View.VISIBLE);
            return;
        }
//        sortPostsAccordingToDate();

        mAdapter = new OldPostAdapter(oldScribbleList, this, this);
        binding.oldScribblesRv.setAdapter(mAdapter);
        binding.oldScribblesRv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onListItemClickListener(int position, OldNotesObj model) {
        Intent intent = new Intent(this, ShowPostsAct.class);
        intent.putExtra("data", model);
        startActivity(intent);
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getOldPosts();
    }
}
