package com.itl.scribble.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.itl.scribble.activities.Login;
import com.itl.scribble.databinding.FragmentProfileBinding;
import com.itl.scribble.serviceLayer.SharedPrefService;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    SharedPrefService prefService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefService = SharedPrefService.getInstance(getContext());

        binding.btnLogout.setOnClickListener(view1 -> {
            removeDataFromSharedPref();
            Intent intent = new Intent(getContext(), Login.class);
            startActivity(intent);
            getActivity().finish();
        });
    }


    private void removeDataFromSharedPref() {
        prefService.clearSharedPref();
    }
}