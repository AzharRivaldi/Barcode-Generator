package com.azhar.barcodegenerator.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.azhar.barcodegenerator.R;

public class CustomDialogFragment extends DialogFragment {

    private TextView mActionOk, mActionCancel;
    private EditText editTextNewContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_dialog, container, false);

        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionOk = view.findViewById(R.id.action_ok);
        editTextNewContent = view.findViewById(R.id.editTextNewContent);

        if (getArguments() != null) {
            if (getArguments().containsKey("content")) {
                editTextNewContent.setText(getArguments().getString("content"));
            }
        }

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }
}
