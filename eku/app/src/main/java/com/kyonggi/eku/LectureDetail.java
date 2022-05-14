package com.kyonggi.eku;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.kyonggi.eku.utils.SendTool;
import com.kyonggi.eku.view.signIn.ActivitySignIn;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class LectureDetail extends AppCompatActivity {

    /**
     * 강의평가 세부화면?
     */
    String[] showBuilding = {"1강의동","2강의동","3강의동","4강의동","5강의동","6강의동","7강의동","8강의동","9강의동","제2공학관"};
    int buildingSelected = 0;
    int[] building = {1,2,3,4,5,6,7,8,9,0};
    AlertDialog buildingSelectDialog;
    float total;
    int count;
    long backKeyPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_detail);
/*
        TextView BuildingButton = (TextView) findViewById(R.id.lecture_detail_spinner);
        BuildingButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildingSelectDialog.show();
            }
        });
        buildingSelectDialog = new AlertDialog.Builder(LectureDetail.this)
                .setSingleChoiceItems(showBuilding, buildingSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        buildingSelected = i;
                    }
                })
                .setTitle("강의동")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BuildingButton.setText(showBuilding[buildingSelected]);
                    }
                })
                .setNegativeButton("취소", null)
                .create();
*/
        Intent intent = getIntent();
        String title = intent.getStringExtra("Name");
        String professor = intent.getStringExtra("Prof");

        Handler handler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {

                String responseResult = (String) msg.obj;
                try {
                    JSONObject LectureObject = new JSONObject(responseResult);
                    Gson a = new Gson();
                    Critics[] critics = a.fromJson(LectureObject.getString("critics"), Critics[].class);
                    count = critics.length;
                    for (int i = 0; i < count; i++){
                        int cid = critics[i].getcid();
                        String content = critics[i].getContent();
                        float star = critics[i].getStar();
                        total += star;
                        String grade = critics[i].getGrade();
                        detail_Lecture(cid,content,star,grade);
                    }
                    RatingBar ratingStar = findViewById(R.id.lecture_detail_rating);
                    total = total/count;
                    ratingStar.setRating(total);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        HashMap<String, Object> temp = new HashMap<>();
        Lecture lecture = new Lecture(title, professor);
        temp.put("lecture",lecture);

        try {
            SendTool.request(SendTool.APPLICATION_JSON,"/critic/search/specific", temp, handler);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        TextView textView = (TextView)findViewById(R.id.lecture_detail_name);
        textView.setText(title);

        textView = (TextView)findViewById(R.id.lecture_detail_professor);
        textView.setText(professor);

        ImageButton WriteButton = (ImageButton) findViewById(R.id.lecture_detail_WriteButton);
        WriteButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInformation info = new UserInformation(getApplicationContext());
                if (!info.fromPhoneVerify(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(), ActivitySignIn.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(),LectureDetailWrite.class);
                    intent.putExtra("lectureName",title);
                    intent.putExtra("professor",professor);
                    startActivity(intent);
                    finish();
                }
            }
        });
/*
        Button closeButton = (Button) findViewById(R.id.lecture_detail_CloseButton);
        closeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LectureMain.class);
                startActivity(intent);
                finish();
            }
        });
*/

    }

    public void detail_Lecture(int Lid, String content,float star,String grade){
        LinearLayout sc = (LinearLayout)findViewById(R.id.lecture_detail_scroll);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        String writer="고지웅";
        LectureItem lectureitem = new LectureItem(getApplicationContext(), content,grade,star,writer);
        sc.addView(lectureitem);
        /*
        LinearLayout.LayoutParams linearLayoutParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LectureDetail.class);
                intent.putExtra("key",Lid);
                // Toast.makeText(getApplicationContext(),String.valueOf(Lid), Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();;

            }
        });


        TextView textView = new TextView(getApplicationContext());
        textView.setText(content);
        textView.setGravity(Gravity.LEFT);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17.0f);
        textView.setLayoutParams(linearLayoutParams);
        textView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        linearLayout.addView(textView);


        TextView scoreView = new TextView(getApplicationContext());
        scoreView.setText(grade);
        scoreView.setGravity(Gravity.LEFT);
        scoreView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17.0f);
        scoreView.setLayoutParams(linearLayoutParams);
        scoreView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        linearLayout.addView(scoreView);

        TextView contentView = new TextView(getApplicationContext());
        contentView.setText(String.valueOf(star));
        contentView.setGravity(Gravity.RIGHT);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17.0f);
        contentView.setLayoutParams(linearLayoutParams);
        contentView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        linearLayout.addView(contentView);

        linearLayout.setBackgroundColor(Color.GRAY);
        sc.addView(linearLayout);
        */
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            Intent intent = new Intent(getApplicationContext(),LectureMain.class);
            startActivity(intent);
            finish();
            //Toast.makeText(this, "뒤로 가기 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            finish();
        }
    }


}
