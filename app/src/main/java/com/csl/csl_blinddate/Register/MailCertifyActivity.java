package com.csl.csl_blinddate.Register;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.csl.csl_blinddate.R;
import com.google.android.material.button.MaterialButton;

public class MailCertifyActivity extends AppCompatActivity {
    String school,mail;
    TextView mail_schoolText;
    TextView mail_mailText;
    EditText mail_mailInputText;
    MaterialButton mail_sendButton;

    EditText certify_editText_1;
    EditText certify_editText_2;
    EditText certify_editText_3;
    EditText certify_editText_4;

    boolean certification = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_certify);

        // 학교,메일주소 인텐트 값 가져오기
        school = getIntent().getStringExtra("school");
        mail = getIntent().getStringExtra("mail");

        // toolbar
        Toolbar toolbar = findViewById(R.id.mail_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);// 툴바 타이틀 메시지
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // view binding
        mail_schoolText = findViewById(R.id.mail_schoolText);
        mail_mailText = findViewById(R.id.mail_mailText);
        mail_mailInputText = findViewById(R.id.mail_mailInputText);
        mail_sendButton = findViewById(R.id.mail_sendButton);

        // view Init
        mail_mailText.setText("@"+mail);
        mail_schoolText.setText(school);

        mail_sendButton.setOnClickListener(new View.OnClickListener() { // 인증번호 요청 버튼 눌를시
            @Override
            public void onClick(View view) {
                View layout = getLayoutInflater().inflate(R.layout.certify_dialog,null);
                certify_editText_1 = layout.findViewById(R.id.certify_editText_1);
                certify_editText_2 = layout.findViewById(R.id.certify_editText_2);
                certify_editText_3 = layout.findViewById(R.id.certify_editText_3);
                certify_editText_4 = layout.findViewById(R.id.certify_editText_4);

                certify_editText_1.setOnTouchListener(onTouchListener);
                certify_editText_2.setOnTouchListener(onTouchListener);
                certify_editText_3.setOnTouchListener(onTouchListener);
                certify_editText_4.setOnTouchListener(onTouchListener);

                certify_editText_1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        certify_editText_2.requestFocus();
                    }
                });
                certify_editText_2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        certify_editText_3.requestFocus();
                    }
                });
                certify_editText_3.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        certify_editText_4.requestFocus();
                    }
                });

                certify_editText_4.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if(!(certify_editText_4.getText().toString().equals("")) ) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(certify_editText_4.getWindowToken(), 0);
                        }
                        certify_editText_4.clearFocus();
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(MailCertifyActivity.this);
                builder.setView(layout)
                        .setCancelable(false);

                Dialog dialog = builder.create();
                dialog.show();
                keyboardShow();
            }
        });


    }

    @Override // 화면 밖 터치시 dismiss 방지
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);
        if(!dialogBounds.contains((int)ev.getX(), (int)ev.getY())){
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void keyboardShow() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN :
                    certify_editText_1.setText("");
                    certify_editText_2.setText("");
                    certify_editText_3.setText("");
                    certify_editText_4.setText("");
                    break;
            }
            certify_editText_1.requestFocus();
            return false;
        }
    };
}
