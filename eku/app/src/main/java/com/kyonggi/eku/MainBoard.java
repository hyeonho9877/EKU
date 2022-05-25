package com.kyonggi.eku;

import static com.kyonggi.eku.view.board.activity.ActivityBoard.BOARD_FREE;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.BOARD_INFO;

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


/*
 * 강의동 낙서게시판
 * 낙서게시판임
 */


public class MainBoard extends AppCompatActivity {

    public static final int Minorcheck = 61686;
    String[] showBuilding = {"6강의동", "7강의동", "8강의동", "제2공학관", "종합강의동"};
    int[] minor = {61618, 61632, 61686, 61633, 61524};
    int buildingSelected = 5;
    int[] building = {6, 7, 8, 9, 0};
    AlertDialog buildingSelectDialog;
    long backKeyPressedTime;
    static TextView BuildingButton;
    GridListAdapter gAdapter;
    LinearLayout sc;
    MainItem mainitem;
    private String buildingNumber;
    int now_building = 8;
    String name;
    GridView gridView;
    int paramHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);
        buildingNumber = String.valueOf(now_building);
        gridView = (GridView) findViewById(R.id.board_Memo);
        paramHeight = gridView.getLayoutParams().height;
/*
        if (savedInstanceState == null) {
            MapFragment mapFragment = new MapFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.board_ImageView, mapFragment, "main")
                    .commit();
        }
 */



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
                        intent.putExtra("buildingNumber", buildingNumber);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(() -> startActivity(intent), 260);
                        break;
                    case R.id.Free:
                        intent = new Intent(getApplicationContext(), ActivityBoard.class);
                        intent.putExtra("mode", BOARD_FREE);
                        intent.putExtra("buildingNumber", buildingNumber);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(() -> startActivity(intent), 260);
                        break;
                    case R.id.lectureMain:
                        intent = new Intent(getApplicationContext(), LectureMain.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
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
                Intent intent = new Intent(getApplicationContext(), DonanBagGi.class);
                startActivity(intent);
            }
        });

        BuildingButton = (TextView) findViewById(R.id.go_Donan);
        BuildingButton.setText("불러오는중");
        Intent intent = getIntent();
        try {
            name = intent.getExtras().getString("GANG");
        } catch (Exception e) {
            name = "EKU";
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
                        BuildingButton.setText(showBuilding[buildingSelected]);
                        ViewMemo(minor[buildingSelected]);
                    }
                })
                .setNegativeButton("취소", null)
                .create();


        if (name.equals("EKU")){
            GridView gridView = (GridView) findViewById(R.id.board_Memo);
            gAdapter = new GridListAdapter();
            gAdapter.addItem(new ListItem("기본 메시지입니다", "EKU"));
            gAdapter.addItem(new ListItem("비콘을 연결하면 강의동 메시지를 확인하실 수 있습니다", "EKU"));
            gridView.setAdapter(gAdapter);
        }
        else {
            for (int i=0;i< showBuilding.length;i++){
                if (name.equals(showBuilding[i]))
                    ViewMemo(minor[i]);
            }
        }

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
                    finish();
                } else if (check.equals("needVerify")) {
                    Intent intent = new Intent(getApplicationContext(), VerfityActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), WriteBoard.class);
                    intent.putExtra("address", "MainBoard");
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
                Log.i("a", String.valueOf(msg.what));
                switch (msg.what) {
                    case 200:
                        String responseResult = (String) msg.obj;
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


    public void PreviewCom(LinearLayout sc) {
        ComminityItem[] listcom = new ComminityItem[3];
        RequestQueue queue;
        JSONObject jsonBodyObj;
        String requestbody;
        String url;
        JsonArrayRequest request;

        queue = Volley.newRequestQueue(this);
        // Body에 담을 JSON Object 생성 및 선언
        jsonBodyObj = new JSONObject();
        try {
            jsonBodyObj.put("page", "0");
            jsonBodyObj.put("lecture_building", now_building);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // body String 선언
        requestbody = String.valueOf(jsonBodyObj.toString());

        url = "https://www.eku.kro.kr/board/info/lists";
        request = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Request에 대한 reponse 받음
                        Log.d("---", "---");
                        Log.w("//===========//", "================================================");
                        Log.d("", "\n" + "[FREE_COMMUNITY_BOARD > getRequestVolleyPOST_BODY_JSON() 메소드 : Volley POST_BODY_JSON 요청 응답]");
                        Log.d("", "\n" + "[" + "응답 전체 - " + String.valueOf(response.toString()) + "]");
                        Log.w("//===========//", "================================================");
                        Log.d("---", "---");

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String title = jsonObject.getString("title");
                                listcom[i] = new ComminityItem(id, title);
                                if (i == 2)
                                    break;
                            }
                            mainitem = new MainItem(getApplicationContext(), "공지게시판", listcom[0], listcom[1], listcom[2], buildingNumber);
                            sc.addView(mainitem);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                // Response Error 출력시,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("---", "---");
                        Log.e("//===========//", "================================================");
                        Log.d("", "\n" + "[A_Main > getRequestVolleyPOST_BODY_JSON() 메소드 : Volley POST_BODY_JSON 요청 실패]");
                        Log.d("", "\n" + "[" + "에러 코드 - " + String.valueOf(error.toString()) + "]");
                        Log.e("//===========//", "================================================");
                        Log.d("---", "---");
                    }
                }
        ) {
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
                    if (requestbody != null && requestbody.length() > 0 && !requestbody.equals("")) {
                        return requestbody.getBytes("utf-8");
                    } else {
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

    public void PreviewFree(LinearLayout sc) {
        FreeCommunityItem[] listFree = new FreeCommunityItem[3];
        RequestQueue queue;
        JSONObject jsonBodyObj;
        final String requestBody;
        String url;
        JsonArrayRequest request;

        queue = Volley.newRequestQueue(this);
        // Body에 담을 JSON Object 생성 및 선언
        jsonBodyObj = new JSONObject();
        try {
            jsonBodyObj.put("", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // body String 선언
        requestBody = String.valueOf(jsonBodyObj.toString());

        // Server 주소
        url = "https://www.eku.kro.kr/board/free/lists";
        // VOLLEY 라이브러리를 이용하여 Server에 JSON Array 요청
        request = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Request에 대한 reponse 받음
                        Log.d("---", "---");
                        Log.w("//===========//", "================================================");
                        Log.d("", "\n" + "[FREE_COMMUNITY_BOARD > getRequestVolleyPOST_BODY_JSON() 메소드 : Volley POST_BODY_JSON 요청 응답]");
                        Log.d("", "\n" + "[" + "응답 전체 - " + String.valueOf(response.toString()) + "]");
                        Log.w("//===========//", "================================================");
                        Log.d("---", "---");

                        try {
                            // Json Array 의 각 데이터를 파싱
                            // Array List 에 삽입
                            // 리사이클러 뷰 어댑터 갱신
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String title = jsonObject.getString("title");
                                listFree[i] = new FreeCommunityItem(id, title);
                                if (i == 2)
                                    break;
                            }
                            mainitem = new MainItem(getApplicationContext(), "자유게시판", listFree[0], listFree[1], listFree[2], buildingNumber);
                            sc.addView(mainitem);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                // Response Error 출력시,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("---", "---");
                        Log.e("//===========//", "================================================");
                        Log.d("", "\n" + "[A_Main > getRequestVolleyPOST_BODY_JSON() 메소드 : Volley POST_BODY_JSON 요청 실패]");
                        Log.d("", "\n" + "[" + "에러 코드 - " + String.valueOf(error.toString()) + "]");
                        Log.e("//===========//", "================================================");
                        Log.d("---", "---");
                    }
                }
        ) {
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
                    if (requestBody != null && requestBody.length() > 0 && !requestBody.equals("")) {
                        return requestBody.getBytes("utf-8");
                    } else {
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


    public void PreviewLec(LinearLayout sc) {
        Lecture[] listLecture = new Lecture[3];

        Handler handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                String responseResult = (String) msg.obj;
                try {
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
                    mainitem = new MainItem(getApplicationContext(), "강의게시판", listLecture[0], listLecture[1], listLecture[2]);
                    sc.addView(mainitem);
                } catch (JSONException e) {
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