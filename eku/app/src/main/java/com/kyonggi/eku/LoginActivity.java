package com.kyonggi.eku;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button btn_sign;
    Button btn_login;
    EditText user_email, user_pw;
    private Map<String,String>map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        user_email = (EditText) findViewById(R.id.user_email);
        user_pw = (EditText)  findViewById(R.id.user_pw);   // 8자 이상, 특수문자 포함


        btn_sign = (Button) findViewById(R.id.btn_photo_sign);
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignPhotoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String std_email = user_email.getText().toString();
                String std_pw = user_pw.getText().toString();

                int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
                if(status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {

                    // 프로그래스바 보이게 처리
                    findViewById(R.id.cpb).setVisibility(View.VISIBLE);


                    // HttpUrlConnection
                    final Thread th = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //  String page = "http://10.0.2.2:8080/JSPBook/ch05/response4.jsp";
                                String page= "http://49.174.169.48:13883";
                                // URL 객체 생성
                                URL url = new URL(page);
                                // 연결 객체 생성
                                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                                // Post 파라미터
                                String params = "param=" + std_email
                                        + "&param2=" + std_pw;
                                // 결과값 저장 문자열
                                final StringBuilder sb = new StringBuilder();
                                // 연결되면
                                if(conn != null) {
                                    Log.i("tag", "conn 연결");
                                    // 응답 타임아웃 설정
                                    conn.setRequestProperty("Accept", "application/json");
                                    conn.setConnectTimeout(10000);
                                    // POST 요청방식
                                    conn.setRequestMethod("GET");
                                    // 포스트 파라미터 전달
                                    conn.getOutputStream().write(params.getBytes("utf-8"));
                                    // url에 접속 성공하면 (200)
                                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
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
                                        Log.i("tag", "결과 문자열 :" +sb.toString());
                                        // 응답 Json 타입일 경우
                                        //JSONArray jsonResponse = new JSONArray(sb.toString());
                                        //Log.i("tag", "확인 jsonArray : " + jsonResponse);

                                    }else {

                                        // runOnUiThread 기본
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                // 프로그래스바 안보이게 처리
                                                findViewById(R.id.cpb).setVisibility(View.GONE);
                                                Toast.makeText(getApplicationContext(), "네트워크 문제 발생", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    // 연결 끊기
                                    conn.disconnect();
                                }

                                //백그라운드 스레드에서는 메인화면을 변경 할 수 없음
                                // runOnUiThread(메인 스레드영역)
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 프로그래스바 안보이게 처리
                                        findViewById(R.id.cpb).setVisibility(View.GONE);
                                    }
                                });

                            }catch (Exception e) {
                                Log.i("tag", "error :" + e);
                            }
                        }
                    });
                    th.start();

                }else {
                    Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        // 비밀번호 8자 이상시 활성화되는 버튼 이벤트
        user_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btn_login.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String inputPw = user_pw.getText().toString();
                if(inputPw.length() >= 8){
                    btn_login.setVisibility(View.VISIBLE);
                }   else {
                    btn_login.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                /*
                if (editable.length() >= 8) {
                    btn_login.setClickable(true);
                    btn_login.setBackgroundColor(Color.BLUE);
                    //btn_login.setTextColor(Color.WHITE);
                } else {
                    btn_login.setClickable(false);
                    btn_login.setBackgroundColor(Color.GRAY);
                    //btn_login.setTextColor(Color.BLACK);
                }
                 */
            }
        });

    }
}
