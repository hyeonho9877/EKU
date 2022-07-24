package com.kyonggi.eku;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.kyonggi.eku.utils.SendTool;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;

public class LectureDetailWrite extends AppCompatActivity {

    private static final String TAG = "LectureDetail";
    /**
     * 강의 평가 상세 작성 기능
     */
    //작성 화면
    String[] showgrade = {"A+", "A", "B+", "B", "C+", "C", "D+", "D", "F"};
    int gradeSelected = 10;
    String[] grade = {"AP", "A", "BP", "B", "CP", "C", "DP", "D", "F"};
    ActivityResultLauncher<Intent> activityResultLauncher;
    AlertDialog gradeSelectDialog;
    String intentLetureName, intentProfessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_write);
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                    }
                }
        );
        Intent intent = getIntent();


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


        EditText titleview = (EditText) findViewById(R.id.lecture_write_NameText);
        intentLetureName = intent.getStringExtra("lectureName");
        titleview.setText(intentLetureName);
        titleview.setClickable(false);
        titleview.setFocusable(false);
        EditText professorview = (EditText) findViewById(R.id.lecture_write_ProfessorText);
        intentProfessor = intent.getStringExtra("professor");
        professorview.setText(intentProfessor);
        professorview.setClickable(false);
        professorview.setFocusable(false);
        EditText contentview = (EditText) findViewById(R.id.lecture_write_ContentText);
        RatingBar ratingview = (RatingBar) findViewById(R.id.lecture_write_ratingBar);
        Button saveButton = (Button) findViewById(R.id.lecture_write_SaveButton);
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleview.getText().toString();
                String professor = professorview.getText().toString();
                String content = contentview.getText().toString();
                float rating = ratingview.getRating();
                if (title.equals("") || professor.equals("") ||
                        content.equals("") || rating == 0 || gradeSelected == 10) {
                    Toast.makeText(getApplicationContext(), "항목을 모두 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                String score = grade[gradeSelected];


                HashMap<String, Object> temp = new HashMap<>();
                UserInformation info = new UserInformation(getApplicationContext());
                String stduNoText = info.fromPhoneStudentNo(getApplicationContext());
                temp.put("studNo", Integer.parseInt(stduNoText));
                temp.put("content", content);
                temp.put("grade", score);
                temp.put("star", rating);
                Lecture lecture = new Lecture(title, professor);
                temp.put("lecture", lecture);

                try {
                    SendTool.requestForPost(SendTool.APPLICATION_JSON, "/critic/apply", temp, handler);
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplicationContext(), LectureDetail.class);
                intent.putExtra("Name", intentLetureName);
                intent.putExtra("Prof", intentProfessor);
                startActivity(intent);
                finish();
            }
        });

        Button closeButton = (Button) findViewById(R.id.lecture_write_CloseButton);
        closeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LectureMain.class);
                startActivity(intent);
                finish();
            }
        });

        TextView GradeButton = (TextView) findViewById(R.id.gradeselect);
        GradeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                gradeSelectDialog.show();
            }
        });
        gradeSelectDialog = new AlertDialog.Builder(LectureDetailWrite.this)
                .setSingleChoiceItems(showgrade, gradeSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gradeSelected = i;
                    }
                })
                .setTitle("강의동")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GradeButton.setText(showgrade[gradeSelected]);
                    }
                })
                .setNegativeButton("취소", null)
                .create();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LectureDetail.class);
        intent.putExtra("Name", intentLetureName);
        intent.putExtra("Prof", intentProfessor);
        startActivity(intent);
        finish();
    }
}
