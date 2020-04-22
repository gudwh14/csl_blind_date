package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InFormActivity extends AppCompatActivity {
    TextView inform_nameText;
    TextView inform_schoolText;
    TextView inform_myWritingText;
    TextView inform_applyedText;
    TextView inform_applyingText;
    TextView inform_certifyText;
    TextView inform_questionText;
    TextView inform_appText;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_form);
        // View 초기화

        inform_nameText = findViewById(R.id.inform_nameText);
        inform_schoolText = findViewById(R.id.inform_schoolText);
        inform_myWritingText = findViewById(R.id.inform_myWritingText);
        inform_applyedText = findViewById(R.id.inform_applyedText);
        inform_applyingText = findViewById(R.id.inform_applyingText);
        inform_certifyText = findViewById(R.id.inform_certifyText);
        inform_questionText = findViewById(R.id.inform_questionText);
        inform_appText = findViewById(R.id.inform_appText);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.inform_myWritingText :
                        break;
                    case R.id.inform_applyedText :
                        intent = new Intent(InFormActivity.this, ApplyActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.inform_applyingText :
                        intent = new Intent(InFormActivity.this, ApplyListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.inform_certifyText :
                        break;
                    case R.id.inform_questionText :
                        break;
                    case R.id.inform_appText :
                        break;
                    default:
                        break;
                }
            }
        };

        inform_myWritingText.setOnClickListener(onClickListener);
        inform_applyedText.setOnClickListener(onClickListener);
        inform_applyingText.setOnClickListener(onClickListener);
        inform_certifyText.setOnClickListener(onClickListener);
        inform_questionText.setOnClickListener(onClickListener);
        inform_appText.setOnClickListener(onClickListener);

    }
}
