package com.kyonggi.eku;


import android.content.Intent;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LectureWrite extends AppCompatActivity {

    //작성 화면
    ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_write);
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        EditText titleview = (EditText)findViewById(R.id.lecture_write_NameText);
        EditText professorview = (EditText)findViewById(R.id.lecture_write_ProfessorText);
        EditText contentview = (EditText)findViewById(R.id.lecture_write_ContentText);
        RatingBar ratingview = (RatingBar)findViewById(R.id.lecture_write_ratingBar);
        RadioGroup scoreGroup = findViewById(R.id.lecture_write_radioGroup);
        RadioButton scoreview = findViewById(scoreGroup.getCheckedRadioButtonId());
        Button saveButton = (Button) findViewById(R.id.lecture_write_SaveButton);
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleview.getText().toString();
                String professor = professorview.getText().toString();
                String content = contentview.getText().toString();
                String rating = String.valueOf(ratingview.getRating());
                String score = "B";//scoreview.getText().toString();

                String user_email = "jiyko7@kyonggi.ac.kr";
                try {
                    //  String page = "http://10.0.2.2:8080/JSPBook/ch05/response4.jsp";
                    String page = "http://49.174.169.48:13883/critic/apply";
                    // URL 객체 생성
                    URL url = new URL(page);
                    // 연결 객체 생성
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    // Post 파라미터
                    String params = "email=" + user_email
                            + "&content=" + content
                            + "&profName=" + professor
                            + "&lectureName=" + title
                            + "&grade=" + score;
                    // 결과값 저장 문자열
                    final StringBuilder sb = new StringBuilder();
                    // 연결되면

                    Log.i("tag", "conn 연결");
                    // 응답 타임아웃 설정
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setConnectTimeout(10000);
                    // POST 요청방식
                    conn.setRequestMethod("POST");
                    // 포스트 파라미터 전달
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(params.getBytes("utf-8"));
                    // url에 접속 성공하면 (200)
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        // 결과 값 읽어오는 부분
                        BufferedReader br = new BufferedReader(new InputStreamReader(
                                conn.getInputStream(), "utf-8"
                        ));
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        // 버퍼리더 종료
                        br.close();
                        Log.i("tag", "결과 문자열 :" + sb.toString());
                        // 응답 Json 타입일 경우
                        //JSONArray jsonResponse = new JSONArray(sb.toString());
                        //Log.i("tag", "확인 jsonArray : " + jsonResponse);

                        conn.disconnect();
                    }
                } catch (Exception e) {
                    Log.i("tag", "error :" + e);
                }

                Intent intent = new Intent(getApplicationContext(), LectureMain.class);
                startActivity(intent);
            }
        });

        Button closeButton = (Button) findViewById(R.id.lecture_write_CloseButton);
        closeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LectureMain.class);
                startActivity(intent);
                finish();
            }
        });


    }
}