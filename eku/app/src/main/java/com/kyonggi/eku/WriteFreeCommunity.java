package com.kyonggi.eku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.HashMap;

public class WriteFreeCommunity extends AppCompatActivity {

    /*
    *
    * 제목
    * 자유게시판 작성
    * 기능
    * ㅈㄱㄴ
    * */
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_free_community);
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        Button saveButton = (Button) findViewById(R.id.write_free_save);
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainFreeCommunity.class);
                // Toast.makeText(getApplicationContext(),String.valueOf(count), Toast.LENGTH_SHORT).show();



                String building = "";
                String temp = "";
                CheckBox building0 = findViewById(R.id.free_building0);
                temp = building0.isChecked() ? "1" : "0";
                building += temp;
                CheckBox building1 = findViewById(R.id.free_building1);
                temp = building1.isChecked() ? "1" : "0";
                building += temp;
                CheckBox building2 = findViewById(R.id.free_building2);
                temp = building2.isChecked() ? "1" : "0";
                building += temp;
                CheckBox building3 = findViewById(R.id.free_building3);
                temp = building3.isChecked() ? "1" : "0";
                building += temp;
                CheckBox building4 = findViewById(R.id.free_building4);
                temp = building4.isChecked() ? "1" : "0";
                building += temp;
                CheckBox building5 = findViewById(R.id.free_building5);
                temp = building5.isChecked() ? "1" : "0";
                building += temp;
                CheckBox building6 = findViewById(R.id.free_building6);
                temp = building6.isChecked() ? "1" : "0";
                building += temp;
                CheckBox building7 = findViewById(R.id.free_building7);
                temp = building7.isChecked() ? "1" : "0";
                building += temp;
                CheckBox building8 = findViewById(R.id.free_building8);
                temp = building8.isChecked() ? "1" : "0";
                building += temp;
                CheckBox building9 = findViewById(R.id.free_building9);
                temp = building9.isChecked() ? "1" : "0";
                building += temp;

                /*
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd hh-mm");
                String time = timeFormat.format(date);
                PreferenceManagers.setString(getApplicationContext(), "FreeCommunity_time" + count, time);
                */




                Handler handler = new Handler(){
                    public void handleMessage(@NonNull Message msg){
                        switch (msg.what) {
                            case 0:
                                String responseResult=(String)msg.obj;

                                Toast.makeText(getApplicationContext(), responseResult, Toast.LENGTH_LONG).show();
                                EditText estudNo = findViewById(R.id.write_free_title);
                                EditText edepartment =findViewById(R.id.write_free_content);

                        }
                    }
                };


                HashMap<String,Object> temp2 = new HashMap<>();
                temp2.put("studNo","201713924");
                temp2.put("department","컴퓨터공학과");
                temp2.put("title","test제목");
                temp2.put("content","test내용");


                try {
                    SendTool.request(SendTool.APPLICATION_JSON, "/free/write",temp2,handler);
                }
                catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                }

                activityResultLauncher.launch(intent);
                finish();
            }
        });
        Button closeButton = (Button) findViewById(R.id.write_free_close);
        closeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainFreeCommunity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}