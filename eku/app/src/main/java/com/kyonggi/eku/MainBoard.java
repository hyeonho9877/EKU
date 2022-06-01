package com.kyonggi.eku;

import static com.kyonggi.eku.view.board.activity.ActivityBoard.BOARD_FREE;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.BOARD_INFO;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.INIT;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.kyonggi.eku.utils.SendTool;
import com.kyonggi.eku.view.board.activity.ActivityBoard;
import com.kyonggi.eku.view.signIn.ActivitySignIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;


/*
 * 강의동 낙서게시판
 * 낙서게시판임
 */


public class MainBoard extends AppCompatActivity {

    public int Minorcheck;
    String[] showBuilding = {"6강의동", "7강의동", "8강의동", "제2공학관", "종합강의동"};
    int[] minor = {61618, 61632, 61686, 61633, 61524};
    int[] building = {6,7,8,10,5};
    int buildingSelected = 5;
    AlertDialog buildingSelectDialog;
    long backKeyPressedTime;
    static TextView BuildingButton;
    GridListAdapter gAdapter;
    LinearLayout sc;
    MainItem mainitem;
    String name;;
    GridView gridView;
    int paramHeight;
    String checkmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);
        gridView = (GridView) findViewById(R.id.board_Memo);
        paramHeight = gridView.getLayoutParams().height;
        sc = (LinearLayout) findViewById(R.id.board_linear);
        Intent intent = getIntent();
        checkmap = intent.getStringExtra("NoMap");
        if(checkmap==null)
        {
            checkmap="O";
        }
       if (!checkmap.equals("O")) {
            TextView txt = (TextView) findViewById(R.id.MapLocation);
            txt.setText("현재 위치 : "+intent.getExtras().getString("GANG"));
            if (savedInstanceState == null) {
                MapFragment mapFragment = new MapFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.board_ImageView, mapFragment, "main")
                        .commit();
            }
        }

        final DrawerLayout drawerLayout = findViewById(R.id.board_drawerLayout);
        findViewById(R.id.board_Menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.board_navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                switch (id) {
                    case R.id.Announce:
                        intent = new Intent(getApplicationContext(), ActivityBoard.class);
                        intent.putExtra("mode", BOARD_INFO);
                        intent.putExtra("buildingNumber", name);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(() -> startActivity(intent), 260);
                        //finish();
                        break;
                    case R.id.Free:
                        intent = new Intent(getApplicationContext(), ActivityBoard.class);
                        intent.putExtra("mode", BOARD_FREE);
                        intent.putExtra("buildingNumber", name);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(() -> startActivity(intent), 260);
                        //finish();
                        break;
                    case R.id.lectureMain:
                        intent = new Intent(getApplicationContext(), LectureMain.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //intent.putExtra("GANG",name);
                        new Handler().postDelayed(() -> startActivity(intent), 260);
                        break;
                    case R.id.ToDo:
                        intent = new Intent(getApplicationContext(), TodoActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(() -> startActivity(intent), 260);
                        break;
                    case R.id.TimeTable:
                        intent = new Intent(getApplicationContext(), ScheduleTable.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(() -> startActivity(intent), 260);
                        break;
                    case R.id.Account:
                        intent = new Intent(getApplicationContext(), AccountActivity.class);
                        intent.putExtra("address", "MainBoard");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent);
                }
                return false;
            }
        });

        ImageButton button = findViewById(R.id.donanRun);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkmap==null)
                {
                    checkmap="";
                }
                if (checkmap.equals("O")){
                    Toast.makeText(getApplicationContext(), "비콘을 연결하셔야 합니다", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), DonanBagGi.class);
                startActivity(intent);
            }
        });

        BuildingButton = (TextView) findViewById(R.id.go_Donan);
        BuildingButton.setText("불러오는중");
        try {
            name = intent.getExtras().getString("GANG");
            Log.i("dd",name);
            for (int i=0;i< showBuilding.length;i++) {
                if (name.equals(showBuilding[i])) {
                    Minorcheck = minor[i];
                    buildingSelected = i;
                }
            }

        } catch (Exception e) {
                name = "8강의동";
                Minorcheck = 61686;
                buildingSelected = 2;
        }
        BuildingButton.setText(name);
        BuildingButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildingSelectDialog.show();
            }
        });
        buildingSelectDialog = new AlertDialog.Builder(MainBoard.this)
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
                        name = showBuilding[buildingSelected];
                        BuildingButton.setText(name);
                        Minorcheck = minor[buildingSelected];
                        sc.removeAllViews();
                        ViewMemo(Minorcheck);
                        PreviewCom(sc);
                        PreviewFree(sc);
                        PreviewLec(sc);
                    }
                })
                .setNegativeButton("취소", null)
                .create();



        ViewMemo(Minorcheck);

        sc = (LinearLayout) findViewById(R.id.board_linear);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        PreviewCom(sc);
        PreviewFree(sc);
        PreviewLec(sc);


        ImageButton imageButton = (ImageButton) findViewById(R.id.board_Write);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInformation userInfo = new UserInformation();
                String check = userInfo.sessionCheck(getBaseContext());
                if (check.equals("needLogin")) {
                    Intent intent = new Intent(getApplicationContext(), ActivitySignIn.class);
                    startActivity(intent);
                    //finish();
                } else if (check.equals("needVerify")) {
                    Intent intent = new Intent(getApplicationContext(), VerfityActivity.class);
                    startActivity(intent);
                    //finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), WriteBoard.class);
                    intent.putExtra("address", "MainBoard");
                    intent.putExtra("GANG",name);
                    intent.putExtra("checkMinor",Minorcheck);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void ViewMemo(int checkMinor){
        GridView gridView = (GridView) findViewById(R.id.board_Memo);
        gridView.setAdapter(null);
        gAdapter = new GridListAdapter();
        Handler handler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(@NonNull Message msg) {
                String responseResult = (String) msg.obj;
                Log.e(".", responseResult);
                JSONArray BoardArray = null;
                try {

                    BoardArray = new JSONArray(responseResult);
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
                int length = BoardArray.length();
                for (int i = 0; i < length; i++) {
                    try {
                        JSONObject BoardObject = BoardArray.getJSONObject(i);
                        int minor = BoardObject.getInt("minor");
                        if (minor == checkMinor) {
                            String content = BoardObject.getString("content");
                            String time = BoardObject.getString("writtenTime");
                            gAdapter.addItem(new ListItem(content, time));
                            gridView.setAdapter(gAdapter);
                        }
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }

                    ViewGroup.LayoutParams params = gridView.getLayoutParams();
                    params.height = paramHeight * ((length + 1) / 2);
                    gridView.setLayoutParams(params);
                }
            }
        };

        HashMap<String, Object> params = new HashMap<>();
        params.put("minor", String.valueOf(checkMinor));


        try {
            SendTool.requestForPost(SendTool.POST_PARAM, "/doodle/read", params, handler);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        sc.removeAllViews();
        ViewMemo(Minorcheck);
        PreviewCom(sc);
        PreviewFree(sc);
        PreviewLec(sc);


    }

    public void PreviewCom(LinearLayout sc) {
        ComminityItem[] listcom = new ComminityItem[3];
        Handler handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                try {
                String responseResult = ((ResponseBody) msg.obj).string();
                    JSONArray comArray = new JSONArray(responseResult);
                    for (int i = 0; i < comArray.length(); i++) {
                        JSONObject LectureObject = comArray.getJSONObject(i);
                        int id = LectureObject.getInt("id");
                        String title = LectureObject.getString("title");
                        listcom[i] = new ComminityItem(String.valueOf(id), title);
                    }
                    mainitem = new MainItem(getApplicationContext(), "공지게시판", listcom[0], listcom[1], listcom[2], name);
                    sc.addView(mainitem);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
        };

        HashMap<String, Object> temp = new HashMap<>();
        temp.put("lectureBuilding",building[buildingSelected]);
        try {
            SendTool.requestForJson("/board/info/preview", temp, handler);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void PreviewFree(LinearLayout sc) {
        FreeCommunityItem[] listFree = new FreeCommunityItem[3];
        Handler handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                try {
                    String responseResult = ((ResponseBody) msg.obj).string();
                    JSONArray comArray = new JSONArray(responseResult);
                    for (int i = 0; i < comArray.length(); i++) {
                        JSONObject FreeObject = comArray.getJSONObject(i);
                        int id = FreeObject.getInt("id");
                        String title = FreeObject.getString("title");
                        listFree[i] = new FreeCommunityItem(String.valueOf(id), title);
                    }
                    mainitem = new MainItem(getApplicationContext(), " 자유게시판", listFree[0], listFree[1], listFree[2], name);
                    sc.addView(mainitem);
                } catch (JSONException |IOException e) {
                    e.printStackTrace();
                }

            }
        };


        HashMap<String, Object> temp = new HashMap<>();
        try {
            SendTool.requestForJson("/board/free/preview", temp, handler);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    public void PreviewLec(LinearLayout sc) {
        Lecture[] listLecture = new Lecture[3];

        Handler handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                ResponseBody obj = (ResponseBody) msg.obj;
                try {
                String responseResult = obj.string();
                    JSONArray LectureArray = new JSONArray(responseResult);
                    for (int i = 0; i < LectureArray.length(); i++) {
                        JSONObject LectureObject = LectureArray.getJSONObject(i);
                        Gson a = new Gson();
                        Lecture lecture1 = a.fromJson(LectureObject.getString("lecture"), Lecture.class);
                        String title = lecture1.getLectureName();
                        String professor = lecture1.getProfessor();
                        String text = LectureObject.getString("content");
                        listLecture[i] = new Lecture(title, professor, text);
                        if (i == 2)
                            break;
                    }
                    mainitem = new MainItem(getApplicationContext(), "강의게시판", listLecture[0], listLecture[1], listLecture[2],name);
                    sc.addView(mainitem);
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

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "뒤로 가기 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            finish();
        }
    }
}