package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class Fragment3 extends Fragment {

    private static final String ARG_NAME = "name";

    // Interface to communicate checkbox state change to the Host Activity
    public interface OnFragment3CheckboxListener {
        void onCheckboxChanged(boolean isChecked);
    }

    private OnFragment3CheckboxListener listener;

    public Fragment3() {
        // Required empty public constructor
    }

    // Factory method — passes the user's name via Bundle
    public static Fragment3 newInstance(String name) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment3CheckboxListener) {
            listener = (OnFragment3CheckboxListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnFragment3CheckboxListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textViewName = view.findViewById(R.id.textViewName);
        CheckBox checkBoxConfirm = view.findViewById(R.id.checkBoxConfirm);
        Button buttonFinish = view.findViewById(R.id.buttonFinish);

        // Display the name received from the Host Activity via Bundle
        if (getArguments() != null) {
            String name = getArguments().getString(ARG_NAME);
            textViewName.setText("Hello, " + name + "!");
        }

        // Button is initially disabled
        buttonFinish.setEnabled(false);

        // Listen to checkbox state changes
        checkBoxConfirm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                buttonFinish.setEnabled(true);
                buttonFinish.setText("Finish");
            } else {
                buttonFinish.setEnabled(false);
                buttonFinish.setText("Continue");
            }
            // Notify the Host Activity via interface
            listener.onCheckboxChanged(isChecked);
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
