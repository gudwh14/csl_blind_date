package com.csl.csl_blinddate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class SchoolListActivity extends AppCompatActivity {
    EditText school_editText;
    ListView school_listView;
    ArrayAdapter<String> arrayAdapter;
    List<String> list;
    public String school;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);

        // 초기화
        school = "";
        school_editText = findViewById(R.id.school_editText);
        school_listView = findViewById(R.id.school_listView);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        list = new ArrayList<String>();

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

        school_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                school = arrayAdapter.getItem(i);
                Intent intent = new Intent();
                intent.putExtra("school",school);
                setResult(RESULT_OK,intent);
                finish();
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
                arrayAdapter.add(excelload);
                list.add(excelload);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } finally {
            school_listView.setAdapter(arrayAdapter);
            workbook.close();
        }
    }

    public void search(String charText) {
        arrayAdapter.clear();

        if(charText.length() == 0 ) {
            arrayAdapter.addAll(list);
        }
        else {
            int size = list.size();
            for(int i = 0; i < size; i++) {
                if(list.get(i).toLowerCase().contains(charText)) {
                    arrayAdapter.add(list.get(i));
                }
            }
        }
        arrayAdapter.notifyDataSetChanged();
    }


}
