package com.kyonggi.eku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ScheduleTable extends AppCompatActivity {

    /*
     *
     * 제목
     * 시간표
     * 기능
     * 모양만
     * */
    String[] showBuilding = {"1강의동","2강의동","3강의동","4강의동","5강의동","6강의동","7강의동","8강의동","9강의동","제2공학관"};
    int buildingSelected = 0;
    int[] building = {1,2,3,4,5,6,7,8,9,0};
    AlertDialog buildingSelectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_table);

        TextView BuildingButton = (TextView) findViewById(R.id.lecture_detail_spinner);
        BuildingButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildingSelectDialog.show();
            }
        });
        buildingSelectDialog = new AlertDialog.Builder(ScheduleTable.this)
                .setSingleChoiceItems(showBuilding, buildingSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        buildingSelected = i;
                    }
                })
                .setTitle("강의동")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BuildingButton.setText(showBuilding[buildingSelected]);
                    }
                })
                .setNegativeButton("취소", null)
                .create();

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