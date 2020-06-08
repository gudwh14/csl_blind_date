package com.csl.csl_blinddate.Register;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.GenerateCertNumber;
import com.csl.csl_blinddate.R;
import com.csl.csl_blinddate.RetrofitService;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.csl.csl_blinddate.RetrofitService.URL;

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
    String input_mail,certNumber;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RetrofitService retrofitService = retrofit.create(RetrofitService.class);

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
                input_mail = mail_mailInputText.getText().toString();
                if(mail.trim().equals("")) {
                    Toast.makeText(MailCertifyActivity.this,"메일 주소를 입력해 주세요",Toast.LENGTH_SHORT) .show();
                }
                else {
                    mailSender(); // 인증번호 전송

                    View layout = getLayoutInflater().inflate(R.layout.certify_dialog, null); // dialog 에 inflate 할 view 가져오기
                    certify_editText_1 = layout.findViewById(R.id.certify_editText_1);
                    certify_editText_2 = layout.findViewById(R.id.certify_editText_2);
                    certify_editText_3 = layout.findViewById(R.id.certify_editText_3);
                    certify_editText_4 = layout.findViewById(R.id.certify_editText_4);
                    MaterialButton certify_okButton = layout.findViewById(R.id.certify_okButton);

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
                            if (!(certify_editText_4.getText().toString().equals(""))) {
                                keyboardHide();
                            }
                            certify_editText_4.clearFocus();
                        }
                    });


                    AlertDialog.Builder builder = new AlertDialog.Builder(MailCertifyActivity.this);
                    builder.setView(layout)
                            .setCancelable(false);

                    final Dialog dialog = builder.create();
                    dialog.show();
                    keyboardShow();

                    certify_okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            StringBuffer certTemp = new StringBuffer();
                            certTemp.append(certify_editText_1.getText().toString());
                            certTemp.append(certify_editText_2.getText().toString());
                            certTemp.append(certify_editText_3.getText().toString());
                            certTemp.append(certify_editText_4.getText().toString());

                            if(certTemp.toString().equals(certNumber)) {
                                Intent intent = new Intent();
                                intent.putExtra("certification",true);
                                Toast.makeText(MailCertifyActivity.this,"메일 인증이 완료되었습니다",Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK,intent);
                                dialog.dismiss();
                                finish();
                            }
                            else {
                                Toast.makeText(MailCertifyActivity.this,"인증번호가  일치하지 않습니다",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
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

    public void keyboardHide() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(certify_editText_4.getWindowToken(), 0);
    }

    public void mailSender() {
        GenerateCertNumber generateCertNumber = new GenerateCertNumber(); // 난수생성 클래스 선언
        certNumber = generateCertNumber.generate(); // 4자리 난수 생성
        HashMap<String, Object> data = new HashMap<>(); // HashMap 변수 선언
        data.put("address",input_mail+"@"+mail);
        data.put("certNumber",certNumber);

        Call<RetrofitRepo> call = retrofitService.MailSender(data); // retrofit call
        call.enqueue(new Callback<RetrofitRepo>() {
            @Override
            public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {

            }

            @Override
            public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                t.printStackTrace();
            }
        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
