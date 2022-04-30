package com.kyonggi.eku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class WriteBoard extends AppCompatActivity {
    /*
     *
     * 제목
     * 메인게시판(강의동 게시판 낙서) 작성
     * 기능
     * 낙서하는 기능
     * */
    //작성 화면
    ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_board);
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                    }
                }
        );

<<<<<<< HEAD

=======
        Handler handler = new Handler() {
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 0:
                        String responseResult = (String) msg.obj;
                        Log.i("a", "무" + responseResult);
                }
            }
        };
>>>>>>> main


        Button saveButton = (Button) findViewById(R.id.memo_save);
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainBoard.class);

                EditText text = findViewById(R.id.memo_write);
                String memoText = text.getText().toString();

                /*
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String time = timeFormat.format(date);
                Log.i("a",time);
                temp.put("writtenTime", time);
                */


                HashMap<String, Object> temp = new HashMap<>();
                temp.put("content", memoText);
                temp.put("minor","61686");


<<<<<<< HEAD

=======
                try {
                    SendTool.request(SendTool.APPLICATION_JSON, "/doodle/write",temp,handler);
                }
                catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                }
>>>>>>> main

                activityResultLauncher.launch(intent);
                finish();

            }
        });
        Button closeButton = (Button) findViewById(R.id.memo_close);
        closeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainBoard.class);
                startActivity(intent);
                finish();
            }
        });


    }
}

