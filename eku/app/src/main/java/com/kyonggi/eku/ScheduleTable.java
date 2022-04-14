package com.kyonggi.eku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ScheduleTable extends AppCompatActivity {

    /*
     *
     * 제목
     * 시간표
     * 기능
     * 모양만
     * */
    String[] items = {"1강의동","2강의동","3강의동","4강의동","5강의동","6강의동","7강의동","8강의동","9강의동","제2공학관"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_table);

        Spinner spinner = (Spinner) findViewById(R.id.schedule_Spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //선택
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //없음
            }
        });

        Button UpButton = (Button) findViewById(R.id.schedule_up_button);
        UpButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button DownButton = (Button) findViewById(R.id.schedule_down_button);
        DownButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button AddButton = (Button) findViewById(R.id.schedule_add_button);
        AddButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button DelButton = (Button) findViewById(R.id.schedule_delete_button);
        DelButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}