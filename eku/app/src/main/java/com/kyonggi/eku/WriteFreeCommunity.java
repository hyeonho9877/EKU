package com.kyonggi.eku;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.kyonggi.eku.view.signIn.ActivitySignIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class WriteFreeCommunity extends AppCompatActivity implements View.OnClickListener {

    /*
     *
     * 제목
     * 자유게시판 작성
     * 기능
     * ㅈㄱㄴ
     * */

    EditText et_title;
    EditText et_content;

    Button btn_save;
    Button btn_cancle;

    String writer_id    = "201713924";
    String department   = "소프트웨어공학과";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_free_community);


        et_title    = findViewById(R.id.write_free_title);
        et_content  = findViewById(R.id.write_free_content);
        btn_save    = findViewById(R.id.write_free_save);
        btn_cancle  = findViewById(R.id.write_free_close);

        btn_save.setOnClickListener(this::onClick);
        btn_cancle.setOnClickListener(this::onClick);

    }


    @Override
    public void onClick(View view) {
        UserInformation info = new UserInformation(getApplicationContext());

        if (!info.fromPhoneVerify(getApplicationContext())) {
            Intent intent = new Intent(getApplicationContext(), ActivitySignIn.class);
            startActivity(intent);
        } else {
            switch (view.getId()){
                case R.id.write_free_save:
                    addBoard();
                    finish();
                    break;
                case R.id.write_free_close:
                    finish();
                    break;
            }
        }
    }


    public void addBoard(){
        // volley 큐 선언 및 생성
        RequestQueue queue = Volley.newRequestQueue(this);
        // Body에 담을 JSON Object 생성 및 선언
        JSONObject jsonBodyObj = new JSONObject();
        try{
            String title    = et_title.getText().toString();
            String content  = et_content.getText().toString();
            jsonBodyObj.put("writerNo", writer_id);
            jsonBodyObj.put("department", department);
            jsonBodyObj.put("title", title);
            jsonBodyObj.put("content", content);

        }catch (JSONException e){
            e.printStackTrace();
        }
        // body String 선언
        final String requestBody = String.valueOf(jsonBodyObj.toString());

        // Server 주소
        String url = "https://www.eku.kro.kr/board/free/write";
        // VOLLEY 라이브러리를 이용하여 Server에 JSON Array 요청
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Request에 대한 reponse 받음
                        Log.d("---","---");
                        Log.w("//===========//","================================================");
                        Log.d("","\n"+"[FREE_COMMUNITY_BOARD > getRequestVolleyPOST_BODY_JSON() 메소드 : Volley POST_BODY_JSON 요청 응답]");
                        Log.d("","\n"+"["+"응답 전체 - "+String.valueOf(response.toString())+"]");
                        Log.w("//===========//","================================================");
                        Log.d("---","---");
                    }
                },
                // Response Error 출력시,
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("---","---");
                        Log.e("//===========//","================================================");
                        Log.d("","\n"+"[A_Main > getRequestVolleyPOST_BODY_JSON() 메소드 : Volley POST_BODY_JSON 요청 실패]");
                        Log.d("","\n"+"["+"에러 코드 - "+String.valueOf(error.toString())+"]");
                        Log.e("//===========//","================================================");
                        Log.d("---","---");
                    }
                }
        ){
            // Header Request 선언
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
            // Body Request 선언
            @Override
            public byte[] getBody() {
                try {
                    if (requestBody != null && requestBody.length()>0 && !requestBody.equals("")){
                        return requestBody.getBytes("utf-8");
                    }
                    else {
                        return null;
                    }
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }
        };

        // 이전 결과가 있더도 새로 요청하여 응답을 보여줌 여부
        // False
        request.setShouldCache(false);
        // Volley Request 큐에 request 삽입.
        queue.add(request);
    }
}