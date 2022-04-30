package com.kyonggi.eku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_detail);

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

        Intent intent = getIntent();
        String title = intent.getStringExtra("Name");


        String professor = intent.getStringExtra("Prof");

        Handler handler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {

                String responseResult = (String) msg.obj;
                Log.i("z", responseResult);
                try {
                    JSONObject LectureObject = new JSONObject(responseResult);
                    Gson a = new Gson();

                    ////여기까지 했음~~~~~~~~~~~~~~~

                    Lecture lecture1 = a.fromJson(LectureObject.getString("lecture"), Lecture.class);
                    String title = lecture1.getLectureName();
                    String professor = lecture1.getLectureName();
    /*
                    for (int i = 0; i < LectureArray.length(); i++) {
                        JSONObject LectureObject = LectureArray.getJSONObject(i);
                        Gson a = new Gson();
                        Lecture lecture1 = a.fromJson(LectureObject.getString("lecture"), Lecture.class);
                        String title = lecture1.getLectureName();
                        String professor = lecture1.getLectureName();
                        }
                        */
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

        //밑에 전부 수정
        RatingBar ratingStar = findViewById(R.id.lecture_detail_rating);
        Float rating = PreferenceManagers.getFloat(getApplicationContext(),"rating" );
        ratingStar.setRating(rating);

        Button closeButton = (Button) findViewById(R.id.lecture_detail_CloseButton);
        closeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        detail_Lecture(0);
    }

    public void detail_Lecture(int Lid){
        LinearLayout sc = (LinearLayout)findViewById(R.id.lecture_detail_scroll);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams linearLayoutParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        /*
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                int Lid = view.getId();
                intent.putExtra("key",Lid);
                // Toast.makeText(getApplicationContext(),String.valueOf(Lid), Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();;

            }
        });
         */

        TextView textView = new TextView(getApplicationContext());
        textView.setText("작성자 : " + PreferenceManagers.getString(getApplicationContext(), "writer"+Lid));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17.0f);
        textView.setLayoutParams(linearLayoutParams);
        textView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        linearLayout.addView(textView);


        TextView scoreView = new TextView(getApplicationContext());
        scoreView.setText("학점 : " + PreferenceManagers.getString(getApplicationContext(), "score"+Lid));
        scoreView.setGravity(Gravity.RIGHT);
        scoreView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17.0f);
        scoreView.setLayoutParams(linearLayoutParams);
        scoreView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        linearLayout.addView(scoreView);

        TextView contentView = new TextView(getApplicationContext());
        contentView.setText(PreferenceManagers.getString(getApplicationContext(), "content"+Lid));
        contentView.setGravity(Gravity.LEFT);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17.0f);
        contentView.setLayoutParams(linearLayoutParams);
        contentView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        linearLayout.addView(contentView);

        linearLayout.setBackgroundColor(Color.GRAY);
        sc.addView(linearLayout);


    }


}