package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.csl.csl_blinddate.Adapter.SchoolAdapter;
import com.csl.csl_blinddate.Data.SchoolData;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class SchoolListActivity extends AppCompatActivity {
    EditText school_editText;
    RecyclerView school_listView;

    SchoolAdapter schoolAdapter;
    List<SchoolData> list;
    public String school;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);

        // 초기화
        school = "";
        school_editText = findViewById(R.id.school_editText);
        school_listView = findViewById(R.id.school_listView);


        // 리사이클러뷰 바인딩, 초기화
        schoolAdapter = new SchoolAdapter();
        list = new ArrayList<SchoolData>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SchoolListActivity.this);
        school_listView.setLayoutManager(linearLayoutManager);

        Excel();

        school_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = school_editText.getText().toString();
                search(text);
            }
        });

    }

    public void Excel() {
        Workbook workbook = null;
        Sheet sheet = null;
        try {
            InputStream inputStream = getBaseContext().getResources().getAssets().open("list.xls");
            workbook = Workbook.getWorkbook(inputStream);
            sheet = workbook.getSheet(0);
            int MaxColumn = 1, RowStart = 0, RowEnd = sheet.getColumn(MaxColumn - 1).length -1, ColumnStart = 0, ColumnEnd = sheet.getRow(2).length - 1;
            for(int row = RowStart;row <= RowEnd;row++) {
                String excelload = sheet.getCell(ColumnStart, row).getContents();
                String mail = sheet.getCell(1, row).getContents();
                SchoolData schoolData = new SchoolData(excelload,mail);
                schoolAdapter.addItem(schoolData);
                list.add(schoolData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } finally {
            school_listView.setAdapter(schoolAdapter);
            workbook.close();
        }
    }

    public void search(String charText) {
        schoolAdapter.clear();

        if(charText.length() == 0 ) {
            schoolAdapter.addAll(list);
        }
        else {
            int size = list.size();
            for(int i = 0; i < size; i++) {
                if(list.get(i).getSchool().toLowerCase().contains(charText)) {
                    schoolAdapter.addItem(list.get(i));
                }
            }
        }
        schoolAdapter.notifyDataSetChanged();
    }


}
