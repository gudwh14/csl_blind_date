package com.csl.csl_blinddate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    String image_path = "";
    String filename = "";
    String resize_path = "";

    Context context = BoardWriteActivity.this;

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
                    imageSend();


                    HashMap<String, Object> data = new HashMap<>();
                    data.put("userID",UserData.getInstance().getUserID());
                    data.put("board_title",board_title);
                    data.put("title",title);
                    data.put("mainText",mainText);
                    data.put("image_path",filename);

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
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
            if(cursor != null) {
                startManagingCursor(cursor);
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                image_path = cursor.getString(columnIndex);
                cursor.close();
            }


            Log.i("Uri",image_path);
            upload_imageView.setImageURI(selectedImageUri);

            Bitmap bitmap = resize(context,selectedImageUri,500);
            resize_path = saveBitmapToJpeg(context,bitmap,"resize_image");

        }
        
    }

    public void imageSend() {
        filename = image_path.substring(image_path.lastIndexOf("/")+1);
        File file = new File(resize_path);


        MultipartBody.Part body = MultipartBody.Part.createFormData("upload_file",filename, RequestBody.create(MediaType.parse("image/*"),file));
        //RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");


        Call<RetrofitRepo> call = retrofitService.uploadFile(body);
        call.enqueue(new Callback<RetrofitRepo>() {
            @Override
            public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                RetrofitRepo repo = response.body();
                if(repo.isSuccess()) {
                    Toast.makeText(getApplicationContext(),"이미지 업로드성공",Toast.LENGTH_SHORT).show();
                }
                else {
                }
            }

            @Override
            public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private Bitmap resize(Context context,Uri uri,int resize){ // Bitmap 리사이징 함수
        Bitmap resizeBitmap=null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options); // 1번

            int width = options.outWidth;
            int height = options.outHeight;
            int samplesize = 1;

            while (true) {//2번
                if (width / 2 < resize || height / 2 < resize)
                    break;
                width /= 2;
                height /= 2;
                samplesize *= 2;
            }

            options.inSampleSize = samplesize;
            Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options); //3번
            resizeBitmap=bitmap;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return resizeBitmap;
    }

    public static String saveBitmapToJpeg(Context context,Bitmap bitmap, String name){ // 비트맵 File로 저장

        File storage = context.getCacheDir(); // 이 부분이 임시파일 저장 경로

        String fileName = name + ".jpg";  // 파일이름은 마음대로!

        File tempFile = new File(storage,fileName);

        try{
            tempFile.createNewFile();  // 파일을 생성해주고

            FileOutputStream out = new FileOutputStream(tempFile);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 90 , out);  // 넘거 받은 bitmap을 jpeg(손실압축)으로 저장해줌

            out.close(); // 마무리로 닫아줍니다.

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempFile.getAbsolutePath();   // 임시파일 저장경로를 리턴해주면 끝!
    }

}
