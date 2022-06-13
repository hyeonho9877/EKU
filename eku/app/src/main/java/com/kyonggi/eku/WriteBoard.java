package com.kyonggi.eku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kyonggi.eku.utils.SendTool;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;


public class WriteBoard extends AppCompatActivity {
    private static final String TAG = "WriteBoard";
    /*
     *
     * 제목
     * 메인게시판(강의동 게시판 낙서) 작성
     * 기능
     * 낙서하는 기능
     * */
    //작성 화면
    int Minor;
    String Name;
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

        Intent intent= getIntent();
        Minor = intent.getIntExtra("checkMinor",61686);
        Name = intent.getStringExtra("GANG");


        Handler handler = new Handler() {
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 0:
                        try {
                            String responseResult = ((ResponseBody) msg.obj).string();
                        } catch (IOException e) {
                            Log.e(TAG, "handleMessage: ");
                        }
                }
            }
        };


        Button saveButton = (Button) findViewById(R.id.memo_save);
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainBoard.class);
                intent.putExtra("GANG",Name);
                EditText text = findViewById(R.id.memo_write);
                String memoText = text.getText().toString();
                if(!memoText.equals("")){
                    HashMap<String, Object> temp = new HashMap<>();
                    temp.put("content", memoText);
                    temp.put("minor", Minor);


                    try {
                        SendTool.requestForPost(SendTool.APPLICATION_JSON, "/doodle/write", temp, handler);
                    } catch (IOException | NullPointerException e) {
                        e.printStackTrace();
                    }
                }
                activityResultLauncher.launch(intent);
                finish();
            }
        });
        Button closeButton = (Button) findViewById(R.id.memo_close);
        closeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainBoard.class);
                intent.putExtra("GANG",Name);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainBoard.class);
        startActivity(intent);
        finish();
    }
}

