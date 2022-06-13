package com.kyonggi.eku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.kyonggi.eku.presenter.lecture.LecturePresenter;
import com.kyonggi.eku.utils.SendTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;

public class LectureDetail extends AppCompatActivity {

    /**
     * 강의평가 세부화면?
     */

    float total;
    int count;
    private LecturePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_detail);

        presenter = new LecturePresenter(this, this);

        Intent intent = getIntent();
        String title = intent.getStringExtra("Name");
        String professor = intent.getStringExtra("Prof");

        Handler handler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                try {
                    String responseResult = ((ResponseBody) msg.obj).string();
                    JSONObject LectureObject = new JSONObject(responseResult);
                    Gson a = new Gson();
                    Critics[] critics = a.fromJson(LectureObject.getString("critics"), Critics[].class);
                    count = critics.length;
                    for (int i = (count-1); i >= 0; i--){
                        int cid = critics[i].getcid();
                        String content = critics[i].getContent();
                        float star = critics[i].getStar();
                        String studNo = critics[i].getStudNo();
                        String department = critics[i].getDepartment();
                        String writer = studNo + " " + department;
                        total += star;
                        String grade = critics[i].getGrade();
                        detail_Lecture(cid,content,star,grade, writer);
                    }
                    RatingBar ratingStar = findViewById(R.id.lecture_detail_rating);
                    total = total/count;
                    ratingStar.setRating(total);
                } catch (JSONException |IOException e) {
                    e.printStackTrace();
                }

            }
        };

        HashMap<String, Object> temp = new HashMap<>();
        Lecture lecture = new Lecture(title, professor);
        temp.put("lecture",lecture);

        try {
            SendTool.requestForPost(SendTool.APPLICATION_JSON,"/critic/search/specific", temp, handler);
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
                    presenter.signIn();
                } else {
                    Intent intent = new Intent(getApplicationContext(),LectureDetailWrite.class);
                    intent.putExtra("lectureName",title);
                    intent.putExtra("professor",professor);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void detail_Lecture(int Lid, String content,float star,String grade, String writer){
        LinearLayout sc = (LinearLayout)findViewById(R.id.lecture_detail_scroll);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LectureItem lectureitem = new LectureItem(getApplicationContext(), content,grade,star,writer);
        sc.addView(lectureitem);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LectureMain.class);
        startActivity(intent);
        finish();
    }
}
