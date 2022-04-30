package com.kyonggi.eku;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class WriteFreeCommunity extends AppCompatActivity {

    /*
    *
    * 제목
    * 자유게시판 작성
    * 기능
    * ㅈㄱㄴ
    * */
    ActivityResultLauncher<Intent> activityResultLauncher;
    EditText write_free_title = findViewById(R.id.write_free_title);
    EditText write_free_content = findViewById(R.id.write_free_content);

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

                String title = write_free_title.getText().toString();
                String content = write_free_content.getText().toString();


                /*
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd hh-mm");
                String time = timeFormat.format(date);
                PreferenceManagers.setString(getApplicationContext(), "FreeCommunity_time" + count, time);
                */

                Handler handler = new Handler(){
                  public void handleMessage(@NonNull Message msg){
                      switch (msg.what){
                          case 0:
                              String responseResult=(String)msg.obj;
                              Toast.makeText(getApplicationContext(),responseResult,Toast.LENGTH_SHORT).show();
                      }
                  }
                };

                HashMap<String,Object> sended = new HashMap<>();
                sended.put("studNo",201713924);
                sended.put("department","컴퓨터공학부");
                sended.put("title",title);
                sended.put("content",content);


                try {
                    SendTool.request(SendTool.APPLICATION_JSON,"/board/free/write",sended,handler);
                } catch (IOException e) {
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
