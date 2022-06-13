package com.kyonggi.eku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.kyonggi.eku.presenter.lecture.LecturePresenter;
import com.kyonggi.eku.utils.SendTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;

public class LectureMain extends AppCompatActivity {

    /*
     * 강의평가 메인
     *
     */

    ImageButton imageButton1;
    LinearLayout sc;
    private LecturePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_main);


        presenter = new LecturePresenter(this, this);

        SwipeRefreshLayout swipe = findViewById(R.id.Lecture_Main_Swipe);
        swipe.setOnRefreshListener(
                () -> {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sc = (LinearLayout) findViewById(R.id.Lecture_Main_scroll);
                            sc.removeAllViews();
                            LoadMain();
                            swipe.setRefreshing(false);
                        }
                    }, 500);
                });


        EditText searchText = (EditText) findViewById(R.id.Lecture_Main_searchtext);
        LoadMain();


        imageButton1 = (ImageButton) findViewById(R.id.Lecture_Main_searchButton);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchText.getText().toString();
                if (search.equals(""))
                    return;
                sc = (LinearLayout) findViewById(R.id.Lecture_Main_scroll);
                sc.removeAllViews();
                Handler handler = new Handler(getMainLooper()){
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        try {
                            String responseResult = ((ResponseBody) msg.obj).string();
                            JSONArray LectureArray = new JSONArray(responseResult);
                            for (int i = 0; i < LectureArray.length(); i++) {
                                JSONObject LectureObject = LectureArray.getJSONObject(i);
                                String title = LectureObject.getString("lectureName");
                                String professor = LectureObject.getString("professor");
                                String rating = String.valueOf(LectureObject.getDouble("star"));
                                search_Lecture(title, professor, rating);
                            }

                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                };

                HashMap<String, Object> temp = new HashMap<>();
                temp.put("keyword",search);
                try {
                    SendTool.requestForJson("/critic/search", temp, handler);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void search_Lecture(String Title, String professor, String rating) {
        sc = (LinearLayout) findViewById(R.id.Lecture_Main_scroll);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LectureItem lectureitem = new LectureItem(getApplicationContext(), Title,professor,rating);
        lectureitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LectureDetail.class);
                intent.putExtra("Name", Title);
                intent.putExtra("Prof", professor);
                startActivity(intent);
                finish();
            }
        });
        sc.addView(lectureitem);
    }


    public void write_Lecture(String Title, String professor, String rating, String writer, int Lectureid) {
        sc = (LinearLayout) findViewById(R.id.Lecture_Main_scroll);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LectureItem lectureitem = new LectureItem(getApplicationContext(), Title,professor,rating,writer);
        lectureitem.setId(Lectureid);
        lectureitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LectureDetail.class);
                intent.putExtra("Name", Title);
                intent.putExtra("Prof", professor);
                startActivity(intent);
                finish();
            }
        });
        sc.addView(lectureitem);
    }

    public void LoadMain(){
        Handler handler =  new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                try {
                    String responseResult = ((ResponseBody) msg.obj).string();
                    JSONArray LectureArray = new JSONArray(responseResult);
                    for (int i = 0; i < LectureArray.length(); i++) {
                        JSONObject LectureObject = LectureArray.getJSONObject(i);
                        String rating = LectureObject.getString("star");
                        int LectureId = Integer.parseInt(LectureObject.getString("cid"));
                        String studNo = LectureObject.getString("studNo");
                        String department = LectureObject.getString("department");
                        String writer = studNo + " " + department;
                        Gson a = new Gson();
                        Lecture lecture1 = a.fromJson(LectureObject.getString("lecture"), Lecture.class);
                        String title = lecture1.getLectureName();
                        String professor = lecture1.getProfessor();

                        write_Lecture(title, professor, rating, writer, LectureId);
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }

            }
        };

        HashMap<String, Object> temp = new HashMap<>();
        try {
            SendTool.requestForJson("/critic/read", temp, handler);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}