package com.kyonggi.eku;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;


public class WriteBoard extends AppCompatActivity {
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

        Button saveButton = (Button) findViewById(R.id.memo_save);
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainBoard.class);
                int count = PreferenceManagers.getInt(getApplicationContext(), "board_Count");
                count++;

                PreferenceManagers.setInt(getApplicationContext(), "board_Count", count);

                EditText text = findViewById(R.id.memo_write);
                String memoText = text.getText().toString();
                PreferenceManagers.setString(getApplicationContext(), "board" + count, memoText);

                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd hh-mm");
                String time = timeFormat.format(date);
                PreferenceManagers.setString(getApplicationContext(), "time" + count, time);

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

