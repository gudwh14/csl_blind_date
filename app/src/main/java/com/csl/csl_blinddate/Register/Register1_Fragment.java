package com.csl.csl_blinddate.Register;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.csl.csl_blinddate.R;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class Register1_Fragment extends Fragment {

    ImageButton register_manButton;
    ImageButton register_womanButton;
    public String gender;

    public Register1_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register1, container, false);

        // 초기화
        gender = "";
        register_manButton = view.findViewById(R.id.register_manButton);
        register_womanButton = view.findViewById(R.id.register_womanButton);

        //
        register_manButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_manButton.setBackgroundColor(Color.parseColor("#f1f1f1"));
                register_womanButton.setBackgroundColor(Color.parseColor("#00FF0000"));
                gender = "M";
            }
        });

        register_womanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_womanButton.setBackgroundColor(Color.parseColor("#f1f1f1"));
                register_manButton.setBackgroundColor(Color.parseColor("#00FF0000"));
                gender = "F";
            }
        });

        return view;
    }
}
