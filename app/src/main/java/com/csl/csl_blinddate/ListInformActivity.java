package com.csl.csl_blinddate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.UserData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.csl.csl_blinddate.RetrofitService.URL;

public class ListInformActivity extends AppCompatActivity {

    int id;
    ImageButton listInForm_CloseButton;
    TextView listInForm_commentText;
    Chip inform_trait_1,inform_trait_2,inform_trait_3,inform_trait_4,inform_trait_5,inform_trait_6,inform_trait_7,inform_trait_8;
    EditText listInForm_applyText;
    MaterialButton listInForm_applyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_inform);

        // view 초기화
        id = getIntent().getIntExtra("id",0);
        listInForm_CloseButton = findViewById(R.id.listInForm_CloseButton);
        listInForm_commentText = findViewById(R.id.listInForm_commentText);
        listInForm_applyText = findViewById(R.id.listInForm_applyText);
        listInForm_applyButton = findViewById(R.id.listInForm_applyButton);
        inform_trait_1 = findViewById(R.id.inform_trait_1);
        inform_trait_2 = findViewById(R.id.inform_trait_2);
        inform_trait_3 = findViewById(R.id.inform_trait_3);
        inform_trait_4 = findViewById(R.id.inform_trait_4);
        inform_trait_5 = findViewById(R.id.inform_trait_5);
        inform_trait_6 = findViewById(R.id.inform_trait_6);
        inform_trait_7 = findViewById(R.id.inform_trait_7);
        inform_trait_8 = findViewById(R.id.inform_trait_8);

        inform_trait_1.setVisibility(View.INVISIBLE);
        inform_trait_2.setVisibility(View.INVISIBLE);
        inform_trait_3.setVisibility(View.INVISIBLE);
        inform_trait_4.setVisibility(View.INVISIBLE);
        inform_trait_5.setVisibility(View.INVISIBLE);
        inform_trait_6.setVisibility(View.INVISIBLE);
        inform_trait_7.setVisibility(View.INVISIBLE);
        inform_trait_8.setVisibility(View.INVISIBLE);


        //  클릭 리스너

        listInForm_CloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // applyButton

        listInForm_applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String applyText = listInForm_applyText.getText().toString();
                if(applyText.trim().equals("")) {
                    Toast.makeText(ListInformActivity.this,"코멘트를 작성해 주세요",Toast.LENGTH_SHORT).show();
                }
                else {
                    // 통신
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("userID",SplashActivity.userData.getUserID());
                    data.put("id",id);
                    data.put("comment",applyText);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    RetrofitService retrofitService = retrofit.create(RetrofitService.class);
                    Call<RetrofitRepo> call = retrofitService.ListApply(data);
                    call.enqueue(new Callback<RetrofitRepo>() {
                        @Override
                        public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                            RetrofitRepo repo = response.body();

                            if(repo.isSuccess()) {
                                final AlertDialog.Builder builder = new AlertDialog.Builder(ListInformActivity.this);
                                builder.setTitle("안내")
                                        .setCancelable(false)
                                        .setMessage("미팅 신청이 정상적으로 완료되었습니다.\n\n내 정보 > 미팅 보낸신청목록에서 신청현황을 확인하실수있습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finish();
                                            }
                                        })
                                        .create()
                                        .show();
                            }
                            else {
                                Toast.makeText(ListInformActivity.this,SplashActivity.userData.getUserID(),Toast.LENGTH_SHORT).show();
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

        refresh();

    }

    public void refresh() {
        HashMap<String,Object> data = new HashMap<>();
        data.put("id",id);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<RetrofitRepo> call = retrofitService.ListInFormRefresh(data);
        call.enqueue(new Callback<RetrofitRepo>() {
            @Override
            public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                RetrofitRepo repo = response.body();
                if(repo.isSuccess()) {
                    listInForm_commentText.setText(repo.getComment());
                    boolean[] trait = repo.getTrait();
                    ArrayList<String> arrayList = new ArrayList<>();
                    for(int temp = 0; temp < trait.length; temp++) {
                        if(trait[temp]) {
                            switch (temp) {
                                case 0 :
                                    arrayList.add("잘 놀아요");
                                    break;
                                case 1 :
                                    arrayList.add("낯 가려요");
                                    break;
                                case 2 :
                                    arrayList.add("술 잘마셔요");
                                    break;
                                case 3 :
                                    arrayList.add("게임 잘해요");
                                    break;
                                case 4 :
                                    arrayList.add("성향5");
                                    break;
                                case 5 :
                                    arrayList.add("성향6");
                                    break;
                                case 6 :
                                    arrayList.add("성향7");
                                    break;
                                case 7 :
                                    arrayList.add("성향8");
                                    break;
                                default:
                                    break;
                            }
                        }
                    }

                    for(int temp = 0; temp < arrayList.size(); temp++) {
                        if(inform_trait_1.getText().toString().equals("Item")) {
                            inform_trait_1.setText(arrayList.get(temp));
                            inform_trait_1.setVisibility(View.VISIBLE);
                        }
                        else if(inform_trait_2.getText().toString().equals("Item")) {
                            inform_trait_2.setText(arrayList.get(temp));
                            inform_trait_2.setVisibility(View.VISIBLE);
                        }
                        else if(inform_trait_3.getText().toString().equals("Item")) {
                            inform_trait_3.setText(arrayList.get(temp));
                            inform_trait_3.setVisibility(View.VISIBLE);
                        }
                        else if(inform_trait_4.getText().toString().equals("Item")) {
                            inform_trait_4.setText(arrayList.get(temp));
                            inform_trait_4.setVisibility(View.VISIBLE);
                        }
                        else if(inform_trait_5.getText().toString().equals("Item")) {
                            inform_trait_5.setText(arrayList.get(temp));
                            inform_trait_5.setVisibility(View.VISIBLE);
                        }
                        else if(inform_trait_6.getText().toString().equals("Item")) {
                            inform_trait_6.setText(arrayList.get(temp));
                            inform_trait_6.setVisibility(View.VISIBLE);
                        }
                        else if(inform_trait_7.getText().toString().equals("Item")) {
                            inform_trait_7.setText(arrayList.get(temp));
                            inform_trait_7.setVisibility(View.VISIBLE);
                        }
                        else if(inform_trait_8.getText().toString().equals("Item")) {
                            inform_trait_8.setText(arrayList.get(temp));
                            inform_trait_8.setVisibility(View.VISIBLE);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<RetrofitRepo> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }
}
