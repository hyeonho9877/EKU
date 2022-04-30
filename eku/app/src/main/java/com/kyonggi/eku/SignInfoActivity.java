package com.kyonggi.eku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class SignInfoActivity extends AppCompatActivity {


    /*
     *
     * 제목
     * 회원가입 정보 작성해서 넘기는거
     * 기능
     *
     * */
    Button btn_back;
    Button btn_submit;
    EditText user_sign_name, user_sign_pw, user_sign_major, user_sign_email, user_sign_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_info);

        Intent intent = getIntent();
        String name;
        try {
            name = intent.getExtras().getString("OCR");
        } catch (Exception e)
        {
            name="";
        }
        //String name= "{\"name\":\"고일석\",\"department\":\"컴퓨터공학부\",\"studNo\":201611771}";
        String sign_name="";
        String sign_depart="";
        String sign_studNo="";


        try {
            JSONObject Array = new JSONObject(name);
                sign_name= Array.getString("name");
                sign_depart= Array.getString("department");
                sign_studNo= Array.getString("studNo");
            }
        catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

        btn_back = (Button) findViewById(R.id.btn_cancle);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        user_sign_name = (EditText) findViewById(R.id.user_sign_name);
        user_sign_email = (EditText) findViewById(R.id.user_sign_email);
        user_sign_pw = (EditText) findViewById(R.id.user_sign_pw);
        user_sign_major = (EditText) findViewById(R.id.user_sign_major);
        user_sign_no = (EditText) findViewById(R.id.user_sign_no);

        user_sign_name.setText(sign_name);
        user_sign_major.setText(sign_depart);
        user_sign_no.setText(sign_studNo);

        TextView textView = findViewById(R.id.textView17);
        textView.setVisibility(View.INVISIBLE);
        Button backButton = findViewById(R.id.btn_cancle2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((user_sign_email.length() != 0) && (user_sign_pw.length() != 0) && (user_sign_major.length() != 0) && (user_sign_no.length() != 0) && (user_sign_name.length() != 0)) {
                    String user_email = user_sign_email.getText().toString();
                    String user_pw = user_sign_pw.getText().toString();
                    String user_name = user_sign_name.getText().toString();
                    String user_major = user_sign_major.getText().toString();
                    String user_no = user_sign_no.getText().toString();


<<<<<<< HEAD
                    /*
                    Handler handler = new Handler(){
=======
                    Handler handler = new Handler(getMainLooper()){
>>>>>>> main
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                           // int e = Log.i("결과->", (String) msg.obj);
                        }


                    };

                    HashMap<String, Object> temp = new HashMap<>();
                    temp.put("studNo", user_no);
                    temp.put("password", user_pw);
                    temp.put("name", user_name);
                    temp.put("department", user_major);
                    temp.put("email", user_email);

                    try {
                        SendTool.request(SendTool.APPLICATION_JSON,"/signUp",temp,handler);
                    }
                    catch (IOException | NullPointerException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(getApplicationContext(), VerfityActivity.class);
                    startActivity(intent);
                     */

                } else {
                    textView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
