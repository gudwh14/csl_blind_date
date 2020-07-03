package com.csl.csl_blinddate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.UserData;
import com.csl.csl_blinddate.Register.MailCertifyActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.csl.csl_blinddate.RetrofitService.URL;

public class InFormActivity extends AppCompatActivity {
    TextView inform_ageText;
    TextView inform_sexText;
    TextView inform_schoolText;
    LinearLayout inform_favoriteText;
    LinearLayout inform_myWritingText;
    LinearLayout inform_applyedText;
    LinearLayout inform_applyingText;
    LinearLayout inform_certifyText;
    TextView inform_openSourceText;
    TextView inform_appText;
    Intent intent;

    private int REQUEST_MAIL = 1;
    String userID  = UserData.getInstance().getUserID();
    UserData userData = UserData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_form);
        // toolbar init
        Toolbar toolbar = findViewById(R.id.inform_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("내 정보");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // View Binding

        inform_ageText = findViewById(R.id.inform_ageText);
        inform_sexText = findViewById(R.id.inform_sexText);
        inform_schoolText = findViewById(R.id.inform_schoolText);
        inform_favoriteText = findViewById(R.id.inform_favoriteText);
        inform_myWritingText = findViewById(R.id.inform_myWritingText);
        inform_applyedText = findViewById(R.id.inform_applyedText);
        inform_applyingText = findViewById(R.id.inform_applyingText);
        inform_certifyText = findViewById(R.id.inform_certifyText);
        inform_openSourceText = findViewById(R.id.inform_openSourceText);
        inform_appText = findViewById(R.id.inform_appText);

        if(userData.getGender().equals("M")) {
            inform_sexText.setText("남자");
        }
        else {
            inform_sexText.setText("여자");
        }
        inform_ageText.setText(userData.getAge()+"세");
        inform_schoolText.setText(userData.getSchool());


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.inform_favoriteText :
                        intent = new Intent(InFormActivity.this,CommonBoardActivity.class);
                        intent.putExtra("code",0);
                        startActivity(intent);
                        break;
                    case R.id.inform_myWritingText :
                        intent = new Intent(InFormActivity.this,CommonBoardActivity.class);
                        intent.putExtra("code",1);
                        startActivity(intent);
                        break;
                    case R.id.inform_applyedText :
                        intent = new Intent(InFormActivity.this, ApplyedListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.inform_applyingText :
                        intent = new Intent(InFormActivity.this, ApplyListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.inform_certifyText :
                        if(UserData.getInstance().isCertification()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(InFormActivity.this);
                            builder.setMessage("학교인증이 이미 완료 되었습니다")
                                    .setPositiveButton("확인",null)
                                    .create()
                                    .show();
                        }
                        else {
                            intent = new Intent(InFormActivity.this, MailCertifyActivity.class);
                            intent.putExtra("school", UserData.getInstance().getSchool());
                            intent.putExtra("mail", UserData.getInstance().getMail());
                            startActivityForResult(intent,REQUEST_MAIL);
                        }
                        break;
                    case R.id.inform_openSourceText :
                        intent = new Intent(InFormActivity.this, OpenSourceActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.inform_appText :
                        break;
                    default:
                        break;
                }
            }
        };

        inform_favoriteText.setOnClickListener(onClickListener);
        inform_myWritingText.setOnClickListener(onClickListener);
        inform_applyedText.setOnClickListener(onClickListener);
        inform_applyingText.setOnClickListener(onClickListener);
        inform_certifyText.setOnClickListener(onClickListener);
        inform_openSourceText.setOnClickListener(onClickListener);
        inform_appText.setOnClickListener(onClickListener);

    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_MAIL) {
            if(resultCode == RESULT_OK) {
                HashMap<String, Object> retrofitData = new HashMap<>();
                retrofitData.put("userID",userID);

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(URL)
                        .build();
                RetrofitService retrofitService = retrofit.create(RetrofitService.class);
                Call<RetrofitRepo> call = retrofitService.Certify(retrofitData);
                call.enqueue(new Callback<RetrofitRepo>() {
                    @Override
                    public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                        RetrofitRepo repo = response.body();
                        if(repo.isSuccess()) {
                            UserData.getInstance().setCertification(true);
                            AlertDialog.Builder builder = new AlertDialog.Builder(InFormActivity.this);
                            builder.setMessage("학교인증이 완료 되었습니다")
                                    .setPositiveButton("확인",null)
                                    .create()
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }
        //
    }

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
