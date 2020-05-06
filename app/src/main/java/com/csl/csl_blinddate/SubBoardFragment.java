package com.csl.csl_blinddate;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubBoardFragment extends Fragment {
    TextView sub_boardText_1;
    TextView sub_boardText_2;
    TextView sub_boardText_3;
    TextView sub_boardText_4;
    TextView sub_boardText_5;

    public SubBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_board, container, false);
        sub_boardText_1 = view.findViewById(R.id.sub_boardText_1);
        sub_boardText_2 = view.findViewById(R.id.sub_boardText_2);
        sub_boardText_3 = view.findViewById(R.id.sub_boardText_3);
        sub_boardText_4 = view.findViewById(R.id.sub_boardText_4);
        sub_boardText_5 = view.findViewById(R.id.sub_boardText_5);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),BoardActivity.class);
                switch (view.getId()) {
                    case R.id.sub_boardText_1 :
                        intent.putExtra("title",sub_boardText_1.getText().toString().trim());
                        break;
                    case R.id.sub_boardText_2 :
                        intent.putExtra("title",sub_boardText_2.getText().toString().trim());
                        break;
                    case R.id.sub_boardText_3 :
                        intent.putExtra("title",sub_boardText_3.getText().toString().trim());
                        break;
                    case R.id.sub_boardText_4 :
                        intent.putExtra("title",sub_boardText_4.getText().toString().trim());
                        break;
                    case R.id.sub_boardText_5 :
                        intent.putExtra("title",sub_boardText_5.getText().toString().trim());
                        break;
                    default:
                        break;
                }
                getContext().startActivity(intent);

            }
        };

        sub_boardText_1.setOnClickListener(onClickListener);
        sub_boardText_2.setOnClickListener(onClickListener);
        sub_boardText_3.setOnClickListener(onClickListener);
        sub_boardText_4.setOnClickListener(onClickListener);
        sub_boardText_5.setOnClickListener(onClickListener);


        return view;
    }
}
