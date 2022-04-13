package com.kyonggi.eku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;


public class cameraTest extends AppCompatActivity {

    /**
     * SendTool 더미코드
     * SendTool 사용법이 적혀있습니다 ^^
     */
    Button btnCamera;

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_json);
        Button button = (Button) findViewById(R.id.buttonTest);
        TextView textView =(TextView) findViewById(R.id.textView9);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler(){
                    public void handleMessage(@NonNull Message msg){
                        switch(msg.what){
                            case 0:
                                String responseResult=(String)msg.obj;
                                Log.i("Response : ", responseResult);
                                textView.setText(responseResult);
                        }
                    }
                };
                SendTool sendTool = new SendTool(handler);
                HashMap<String,String> temp = new HashMap<>();
                temp.put("email","yas5@kyonggi.ac.kr");
                temp.put("password","dkssud");


                try {
                    sendTool.request("http://115.85.182.126:8080/signIn","POST",temp);
                } catch (IOException e) {
                    Toast.makeText(getBaseContext(),"서버에러!",Toast.LENGTH_SHORT);
                    e.printStackTrace();
                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(),"JSON에러!",Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }

            }
        });

    }



}