package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class ListWriteActivity extends AppCompatActivity {
    ImageButton ListWrite_CloseButton;
    ChipGroup memberChipGroup;
    Chip memberChip_2;
    Chip memberChip_3;
    Chip memberChip_4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_write);

        // 초기화
        ListWrite_CloseButton = findViewById(R.id.ListWrite_CloseButton);
        memberChipGroup = findViewById(R.id.memberChipGroup);
        memberChip_2 = findViewById(R.id.memberChip_2);
        memberChip_3 = findViewById(R.id.memberChip_3);
        memberChip_4 = findViewById(R.id.memberChip_4);
        // CloseButton 클릭 리스너
        ListWrite_CloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // 종료
            }
        });

        // memberChipGroup Chip 터치 리스너
        memberChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                switch (i) {
                    case R.id.memberChip_2 :

                        break;
                    case R.id.memberChip_3:

                        break;
                    case R.id.memberChip_4 :

                        break;
                    default:
                        break;
                }
            }
        });

    }
}
