package com.csl.csl_blinddate.Register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.csl.csl_blinddate.R;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class Register4_Fragment extends Fragment {
    public EditText register_userID;

    public Register4_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_register4, container, false);
        // 초기화
        register_userID = view.findViewById(R.id.register_userID);
        register_userID.setFilters(new InputFilter[] {filterKoEnNum});

        return view;
    }

    protected InputFilter filterKoEnNum = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎ가-힣]+$");

            if (!ps.matcher(source).matches()) {
                Toast.makeText(getContext(),"영어,한글,숫자만 입력 가능합니다",Toast.LENGTH_SHORT).show();
                return "";
            }
            return null;
        }
    };

}
