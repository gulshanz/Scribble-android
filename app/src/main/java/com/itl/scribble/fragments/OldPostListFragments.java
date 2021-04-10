package com.itl.scribble.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itl.scribble.databinding.FragmentOldPostsBinding;
import com.itl.scribble.interfaces.ListItemClickListener;
import com.itl.scribble.OldNotesObj;
import com.itl.scribble.adapter.OldPostAdapter;
import com.itl.scribble.activities.ShowPostsAct;
import com.itl.scribble.viewmodel_layer.OldPostListVM;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class OldPostListFragments extends Fragment implements ListItemClickListener {

    private static final String TAG = "OldTAG";

    List<OldNotesObj> oldScribbleList;
    TextView noPastScribble;
    RecyclerView mRecyclerView;
    OldPostAdapter mAdapter;
    OldPostListVM viewModel;
    FragmentOldPostsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOldPostsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    private void init() {
        if (getActivity() != null) {
            viewModel = new ViewModelProvider(getActivity()).get(OldPostListVM.class);

            viewModel.getNoteList().observe(getActivity(), this::convertAndAddToAdapterList);
            getOldPosts();
        }
    }

    private void convertAndAddToAdapterList(List<OldNotesObj> list) {
        List<OldNotesObj> convertedList=new ArrayList<>();
        Log.d("fueiowghio", "convertAndAddToAdapterList: "+list.toString());
        oldScribbleList=list;
//        oldScribbleList=convertedList;
        initializeRV();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

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

        mAdapter = new OldPostAdapter(oldScribbleList, getContext(), this);
        binding.oldScribblesRv.setAdapter(mAdapter);
        binding.oldScribblesRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void sortPostsAccordingToDate() {
        Collections.sort(oldScribbleList, new Comparator<OldNotesObj>() {
            @Override
            public int compare(OldNotesObj model, OldNotesObj t1) {
                try {
                    Date date1 = new SimpleDateFormat("dd-MM-yy").parse(model.getCreated_on());
                    Date date2 = new SimpleDateFormat("dd-MM-yy").parse(t1.getCreated_on());
                    return date2.compareTo(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    @Override
    public void onListItemClickListener(int position, OldNotesObj model) {
        Intent intent = new Intent(getContext(), ShowPostsAct.class);
        intent.putExtra("data", model);
        startActivity(intent);
    }

    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getOldPosts();
    }
}