package com.csl.csl_blinddate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.UserData;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.csl.csl_blinddate.RetrofitService.URL;

public class BoardWriteActivity extends AppCompatActivity {
    private final int GET_GALLERY_IMAGE = 200;

    ImageButton boardWrite_CloseButton;
    MaterialButton boardWrite_writeButton;
    EditText boardWrite_titleText;
    EditText boardWrite_mainText;
    ImageView photo_upload;
    ImageView upload_imageView;
    String board_title;

    Uri selectedImageUri;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RetrofitService retrofitService = retrofit.create(RetrofitService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        // view init
        boardWrite_CloseButton = findViewById(R.id.BoardWrite_CloseButton);
        boardWrite_writeButton = findViewById(R.id.boardWrite_writeButton);
        boardWrite_titleText = findViewById(R.id.boardWrite_titleText);
        boardWrite_mainText = findViewById(R.id.boardWrite_mainText);
        photo_upload = findViewById(R.id.photo_upload);
        upload_imageView = findViewById(R.id.upload_imageView);

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
                    data.put("userID",UserData.getInstance().getUserID());
                    data.put("board_title",board_title);
                    data.put("title",title);
                    data.put("mainText",mainText);

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

        photo_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent,GET_GALLERY_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            selectedImageUri = data.getData();
            Log.i("Uri",selectedImageUri+"");
            upload_imageView.setImageURI(selectedImageUri);

        }
    }

    public void imageSend() {
        File file = new File()
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        String idx =pref.getString("idx","");
        RequestBody id = new MultipartBody.Builder()
                .addFormDataPart("idx",idx)
                .build();
        /*MultipartBody.Part = MultipartBody.Part.createFormData("upload_file",selectedImageUri, RequestBody.create(MediaType.parse("image/*")));

        Call<RetrofitRepo> call = retrofitService.uploadFile()*/
    }
}
