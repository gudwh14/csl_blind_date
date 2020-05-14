package com.csl.csl_blinddate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.csl.csl_blinddate.RetrofitService.URL;

public class BoardWriteActivity extends AppCompatActivity {
    ImageButton boardWrite_CloseButton;
    MaterialButton boardWrite_writeButton;
    EditText boardWrite_titleText;
    EditText boardWrite_mainText;
    String board_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        // view init
        boardWrite_CloseButton = findViewById(R.id.BoardWrite_CloseButton);
        boardWrite_writeButton = findViewById(R.id.boardWrite_writeButton);
        boardWrite_titleText = findViewById(R.id.boardWrite_titleText);
        boardWrite_mainText = findViewById(R.id.boardWrite_mainText);
        boolean anonymous = false;
        board_title = getIntent().getStringExtra("title");

        if(!(board_title.equals("익명게시판"))) {
            anonymous = true;
        }

        // Button listener
        boardWrite_CloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        boardWrite_writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = boardWrite_titleText.getText().toString();
                String mainText = boardWrite_mainText.getText().toString();

                if(title.trim().equals("")) {
                    Toast.makeText(BoardWriteActivity.this,"제목을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }
                else if (mainText.trim().equals("")) {
                    Toast.makeText(BoardWriteActivity.this,"본문을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }
                else { // input 통신
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("userID",SplashActivity.userData.getUserID());
                    data.put("board_title",board_title);
                    data.put("title",title);
                    data.put("mainText",mainText);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    RetrofitService retrofitService = retrofit.create(RetrofitService.class);
                    Call<RetrofitRepo> call = retrofitService.BoardWrite(data);
                    call.enqueue(new Callback<RetrofitRepo>() {
                        @Override
                        public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                            RetrofitRepo repo = response.body();
                            if(repo.isSuccess()) {
                                Toast.makeText(getApplicationContext(),"작성 완료",Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                }
            }
        });
    }
}
