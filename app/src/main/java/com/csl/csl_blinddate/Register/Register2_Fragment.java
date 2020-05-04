package com.csl.csl_blinddate.Register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.csl.csl_blinddate.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class Register2_Fragment extends Fragment {

    NumberPicker birthdayPicker;
    TextView register_ageText;
    public int age,year;

    public Register2_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register2, container, false);

        // 초기화
        age = 0;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        birthdayPicker = view.findViewById(R.id.birthdayPicker);
        birthdayPicker.setMinValue(year-30);
        birthdayPicker.setMaxValue(year-19);
        birthdayPicker.setWrapSelectorWheel(false);
        birthdayPicker.setValue(year-25);

        register_ageText = view.findViewById(R.id.register_ageText);

        birthdayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int newVal) {
                age = year - newVal+1;
                register_ageText.setText(age+"세");
            }
        });

        return view;
    }
}
