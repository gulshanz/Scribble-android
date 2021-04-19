package com.itl.scribble.dialogs;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.itl.scribble.R;
import com.itl.scribble.activities.CreateOrEditNote;
import com.itl.scribble.activities.Home;
import com.itl.scribble.databinding.DialogButtonDialogBinding;

public class HomeButtonDialog extends DialogFragment {
    private DialogButtonDialogBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog() == null || getDialog().getWindow() == null)
            dismiss();
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLUE));
        binding = DialogButtonDialogBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        binding.buttonWrite.setOnClickListener(view ->
                startActivity(new Intent(getContext(), CreateOrEditNote.class)));
    }
}
