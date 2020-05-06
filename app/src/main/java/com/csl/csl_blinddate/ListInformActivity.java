package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.google.android.material.chip.Chip;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_inform);

        // view 초기화
        id = getIntent().getIntExtra("id",0);
        listInForm_CloseButton = findViewById(R.id.listInForm_CloseButton);
        listInForm_commentText = findViewById(R.id.listInForm_commentText);
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


        // 클릭 리스너

        listInForm_CloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void refresh() {
        HashMap<String,Object> data = new HashMap<>();
        data.put("id",id);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<RetrofitRepo> call = retrofitService.ListApplyRefresh(data);
        call.enqueue(new Callback<RetrofitRepo>() {
            @Override
            public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                RetrofitRepo repo = response.body();
                if(repo.isSuccess()) {
                    listInForm_commentText.setText(repo.getComment());
                    boolean[] trait = repo.getTrait();
                    for(int temp = 0; temp < trait.length; temp++) {
                        if(trait[temp]) {
                            switch (temp) {
                                case 0 :

                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RetrofitRepo> call, Throwable t) {

            }
        });


    }
}
