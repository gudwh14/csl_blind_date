package com.csl.csl_blinddate.Register;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csl.csl_blinddate.R;
import com.csl.csl_blinddate.SchoolListActivity;
import com.google.android.material.button.MaterialButton;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Register3_Fragment extends Fragment {
    public String school,mail;
    public boolean certification;
    TextView register_schoolText;
    TextView register_certifyText;
    TextView register_certificationText;

    MaterialButton register_schoolButton;
    private int REQUEST_SCHOOL = 1;
    private int REQUEST_MAIL = 2;

    public Register3_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register3, container, false);
        // 초기화
        certification = false;
        school = mail = "";
        register_schoolButton = view.findViewById(R.id.register_schoolButton);
        register_schoolText = view.findViewById(R.id.register_schoolText);
        register_certifyText = view.findViewById(R.id.register_certifyText);
        register_certificationText = view.findViewById(R.id.register_certificationText);
        //
        register_schoolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SchoolListActivity.class);
                startActivityForResult(intent,REQUEST_SCHOOL);
            }
        });

        register_certifyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MailCertifyActivity.class);
                intent.putExtra("school",school);
                intent.putExtra("mail",mail);
                startActivityForResult(intent,REQUEST_MAIL);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_SCHOOL) {
            if(resultCode == RESULT_OK) {
                certification = false;
                school = data.getStringExtra("school");
                mail = data.getStringExtra("mail");
                register_schoolText.setText(school);
                register_certificationText.setText("인증 안됨");
                register_certificationText.setVisibility(View.VISIBLE);
                register_certifyText.setVisibility(View.VISIBLE);
            }
        }
        else if(requestCode == REQUEST_MAIL) {
            if(resultCode == RESULT_OK) {
                certification = data.getBooleanExtra("certification",false);
                register_certificationText.setText("인증됨");
            }
        }
    }
}
