package com.kyonggi.eku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class LectureMain extends AppCompatActivity {

    /*
     * 강의평가 메인
     *
     */
    ImageButton imageButton;
    ImageButton imageButton1;
    LinearLayout sc;
    String[] showBuilding = {"1강의동","2강의동","3강의동","4강의동","5강의동","6강의동","7강의동","8강의동","9강의동","제2공학관"};
    int buildingSelected = 0;
    int[] building = {1,2,3,4,5,6,7,8,9,0};
    AlertDialog buildingSelectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_main);

        final DrawerLayout drawerLayout = findViewById(R.id.Lecture_Main_drawerLayout);

        findViewById(R.id.Lecture_Main_Menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.Lecture_Main_navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                switch(id) {
                    case R.id.Home:
                        intent = new Intent(getApplicationContext(), MainBoard.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.Announce:
                        intent = new Intent(getApplicationContext(), MainCommunity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.Free:
                        intent = new Intent(getApplicationContext(), MainFreeCommunity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.lectureMain:
                        intent = new Intent(getApplicationContext(), LectureMain.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.ToDo:
                        intent = new Intent(getApplicationContext(), TodoActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.TimeTable:
                        intent = new Intent(getApplicationContext(), ScheduleTable.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return false;
            }
        });

        TextView BuildingButton = (TextView) findViewById(R.id.Lecture_Main_spinner);
        BuildingButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildingSelectDialog.show();
            }
        });
        buildingSelectDialog = new AlertDialog.Builder(LectureMain.this)
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

        imageButton = (ImageButton) findViewById(R.id.Lecture_Main_WriteButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LectureWrite.class);
                startActivity(intent);
                finish();
            }
        });

        EditText searchText = (EditText) findViewById(R.id.Lecture_Main_searchtext);

        Handler handler = new Handler() {
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 0:
                        String responseResult = (String) msg.obj;
                        Log.i("a",responseResult);
                        try {
                            JSONArray LectureArray = new JSONArray(responseResult);
                            for (int i = 0; i < LectureArray.length(); i++) {
                                JSONObject LectureObject = LectureArray.getJSONObject(i);

                                String title = LectureObject.getString("lectureName");
                                String professor = LectureObject.getString("profName");
                                String content = LectureObject.getString("content");
                                String rating = LectureObject.getString("star");
                                int LectureId = Integer.parseInt(LectureObject.getString("cid"));
                                write_Lecture(title, professor, rating, content, LectureId);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        };

        SendTool sendTool = new SendTool(handler);
        HashMap<String,String> temp = new HashMap<>();
        try {
            sendTool.request("http://115.85.182.126:8080/critic/read", "POST", temp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        imageButton1 = (ImageButton) findViewById(R.id.Lecture_Main_searchButton);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchText.getText().toString();
                sc = (LinearLayout) findViewById(R.id.Lecture_Main_scroll);
                sc.removeAllViews();
                Handler handler = new Handler() {
                    public void handleMessage(@NonNull Message msg) {
                        switch (msg.what) {
                            case 0:
                                String responseResult = (String) msg.obj;
                                try {
                                    JSONArray LectureArray = new JSONArray(responseResult);
                                    for (int i = 0; i < LectureArray.length(); i++) {
                                        JSONObject LectureObject = LectureArray.getJSONObject(i);

                                        String title = LectureObject.getString("lectureName");
                                        String professor = LectureObject.getString("profName");
                                        if (title.contains(search) || professor.contains(search)) {
                                            String content = LectureObject.getString("content");
                                            String rating = LectureObject.getString("star");
                                            int LectureId = Integer.parseInt(LectureObject.getString("cid"));
                                            write_Lecture(title, professor, rating, content, LectureId);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                        }
                    }
                };

                SendTool sendTool = new SendTool(handler);
                HashMap<String,String> temp = new HashMap<>();
                try {
                    sendTool.request("http://115.85.182.126:8080/critic/read", "POST", temp);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void write_Lecture(String Title, String professor, String rating, String content, int Lectureid) {
        sc = (LinearLayout) findViewById(R.id.Lecture_Main_scroll);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams linearLayoutParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        linearLayout.setId(Lectureid);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LectureDetail.class);
                intent.putExtra("Name", Title);
                intent.putExtra("Prof", professor);
                startActivity(intent);
                finish();
            }
        });

        TextView textView = new TextView(getApplicationContext());
        textView.setText(Title);
        textView.setGravity(Gravity.LEFT);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25.0f);
        textView.setLayoutParams(linearLayoutParams);
        textView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        linearLayout.addView(textView);


        TextView professorView = new TextView(getApplicationContext());
        professorView.setText(professor);
        professorView.setGravity(Gravity.RIGHT);
        professorView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17.0f);
        professorView.setLayoutParams(linearLayoutParams);
        professorView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        linearLayout.addView(professorView);

        RatingBar Rating = new RatingBar(getApplicationContext());
        Rating.setIsIndicator(true);
        Rating.setNumStars(5);
        Rating.setRating(Float.valueOf(rating));
        LinearLayout.LayoutParams centerParams = new LinearLayout.LayoutParams(linearLayoutParams.WRAP_CONTENT, linearLayoutParams.WRAP_CONTENT);
        centerParams.gravity = Gravity.CENTER;
        Rating.setLayoutParams(centerParams);
        linearLayout.addView(Rating);

        TextView contentView = new TextView(getApplicationContext());
        contentView.setText(content);
        contentView.setGravity(Gravity.LEFT);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17.0f);
        contentView.setLayoutParams(linearLayoutParams);
        contentView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        linearLayout.addView(contentView);

        sc.addView(linearLayout);
    }
}