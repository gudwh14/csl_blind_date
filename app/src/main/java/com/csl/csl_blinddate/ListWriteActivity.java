package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.csl.csl_blinddate.Data.RetrofitRepo;
import com.csl.csl_blinddate.Data.UserData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

import static com.csl.csl_blinddate.RetrofitService.URL;

public class ListWriteActivity extends AppCompatActivity {
    ImageButton ListWrite_CloseButton;
    MaterialButton listWrite_okButton;
    EditText listWrite_commentText;
    ChipGroup memberChipGroup;
    ChipGroup traitChipGroup_1;
    ChipGroup traitChipGroup_2;
    CheckBox newbieCheckBox;
    Chip memberChip_2;
    Chip memberChip_3;
    Chip memberChip_4;
    int member,chip_temp;
    String comment;
    boolean newbie;
    LinearLayout newbie_layout;
    boolean[] trait;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_write);

        // 초기화
        member = 0;
        comment = "";
        newbie = false;
        trait = new boolean[8];
        Arrays.fill(trait,false);

        ListWrite_CloseButton = findViewById(R.id.ListWrite_CloseButton);
        listWrite_okButton = findViewById(R.id.listWrite_okButton);
        listWrite_commentText = findViewById(R.id.listWrite_commentText);
        newbieCheckBox = findViewById(R.id.newbieCheckBox);
        memberChipGroup = findViewById(R.id.memberChipGroup);
        traitChipGroup_1 = findViewById(R.id.traitChipGroup_1);
        traitChipGroup_2 = findViewById(R.id.traitChipGroup_2);

        memberChip_2 = findViewById(R.id.memberChip_2);
        memberChip_3 = findViewById(R.id.memberChip_3);
        memberChip_4 = findViewById(R.id.memberChip_4);

        newbie_layout = findViewById(R.id.newbie_layout);

        if (UserData.getInstance().getAge() != 20) {
            newbie_layout.setVisibility(View.INVISIBLE);
        }
        // CloseButton 클릭 리스너
        ListWrite_CloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // 종료
            }
        });

        // okButton 클릭 리스너
        listWrite_okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = 0;
                for (int i=0; i<trait.length; i++) {
                    if(trait[i]==true) {
                        temp++;
                    }
                }
                comment = listWrite_commentText.getText().toString();
                if(newbieCheckBox.isChecked()) {
                    newbie = true;
                }
                else {
                    newbie = false;
                }


                if(comment.trim().equals("")) {
                    Toast.makeText(ListWriteActivity.this,"코멘트를 입력해 주세요",Toast.LENGTH_SHORT).show();
                }
                else if (member == 0) {
                    Toast.makeText(ListWriteActivity.this,"인원 수를 정해주세요",Toast.LENGTH_SHORT).show();
                }
                else if(temp == 0) {
                    Toast.makeText(ListWriteActivity.this,"성향을 선택해주세요",Toast.LENGTH_SHORT).show();
                }
                else {
                    // 통신
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("userID",UserData.getInstance().getUserID());
                    data.put("comment",comment);
                    data.put("member",member);
                    data.put("trait0",trait[0]);
                    data.put("trait1",trait[1]);
                    data.put("trait2",trait[2]);
                    data.put("trait3",trait[3]);
                    data.put("trait4",trait[4]);
                    data.put("trait5",trait[5]);
                    data.put("trait6",trait[6]);
                    data.put("trait7",trait[7]);
                    data.put("newbie",newbie);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    RetrofitService retrofitService = retrofit.create(RetrofitService.class);
                    Call<RetrofitRepo> call = retrofitService.ListWriteData(data);
                    call.enqueue(new Callback<RetrofitRepo>() {
                        @Override
                        public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                            RetrofitRepo repo = response.body();
                            if(repo.isSuccess()) {
                                Toast.makeText(ListWriteActivity.this,"미팅이 등록되었습니다",Toast.LENGTH_SHORT).show();
                                trait = null;
                                finish();
                            }
                            else {
                                Toast.makeText(ListWriteActivity.this,"이미 미팅이 등록된 상태입니다",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RetrofitRepo> call, Throwable t){
                            t.printStackTrace();
                        }
                    });
                }
            }
        });


        // memberChipGroup Chip 터치 리스너
        memberChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                switch (i) {
                    case R.id.memberChip_2 :
                        member = 2;
                        Toast.makeText(ListWriteActivity.this,member+"명",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.memberChip_3:
                        member = 3;
                        break;
                    case R.id.memberChip_4 :
                        member = 4;
                        break;
                    default:
                        break;
                }
            }
        });


        for ( chip_temp = 0; chip_temp<traitChipGroup_1.getChildCount(); chip_temp++) {
            Chip chip = (Chip) traitChipGroup_1.getChildAt(chip_temp);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    switch (compoundButton.getId()) {
                        case R.id.trait_1 :
                            trait[0] = trait[0] == true ? false : true;
                            break;
                        case R.id.trait_2 :
                            trait[1] = trait[1] == true ? false : true;
                            break;
                        case R.id.trait_3 :
                            trait[2] = trait[2] == true ? false : true;
                            break;
                        case R.id.trait_4 :
                            trait[3] = trait[3] == true ? false : true;
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        for ( chip_temp = 0; chip_temp<traitChipGroup_2.getChildCount(); chip_temp++) {
            Chip chip = (Chip) traitChipGroup_2.getChildAt(chip_temp);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    switch (compoundButton.getId()) {
                        case R.id.trait_5 :
                            trait[4] = trait[4] == true ? false : true;
                            break;
                        case R.id.trait_6 :
                            trait[5] = trait[5] == true ? false : true;
                            break;
                        case R.id.trait_7 :
                            trait[6] = trait[6] == true ? false : true;
                            break;
                        case R.id.trait_8 :
                            trait[7] = trait[7] == true ? false : true;
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }
}
