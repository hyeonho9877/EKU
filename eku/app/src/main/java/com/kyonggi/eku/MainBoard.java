package com.kyonggi.eku;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.kyonggi.eku.view.signIn.ActivitySignIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;


/*
* 강의동 낙서게시판
* 낙서게시판임
 */


public class MainBoard extends AppCompatActivity {

    public static final int Minorcheck = 61686;
    String[] showBuilding = {"1강의동","2강의동","3강의동","4강의동","5강의동","6강의동","7강의동","8강의동","9강의동","제2공학관"};
    int buildingSelected = 0;
    int[] building = {1,2,3,4,5,6,7,8,9,0};
    AlertDialog buildingSelectDialog;
    GestureDetector gestureDetector = null;
    long backKeyPressedTime;
    TextView BuildingButton;
    GridListAdapter gAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);

        Button button = findViewById(R.id.donanRun);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DonanBagGi.class);
                startActivity(intent);
            }
        });
        View gestureView = findViewById(R.id.gestureView);
        gestureView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
        gestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            ViewConfiguration configuration = ViewConfiguration.get(getApplicationContext());
            final int minSwipeDelta = configuration.getScaledPagingTouchSlop();
            final int minSwipeVelocity = configuration.getScaledMinimumFlingVelocity();
            final int maxSwipeVelocity = configuration.getScaledMaximumFlingVelocity();

            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float deltaX = motionEvent1.getX() - motionEvent.getX();
                    float deltaY = motionEvent1.getY() - motionEvent.getY();
                    float absVelocityX = Math.abs(velocityX);
                    float absVelocityY = Math.abs(velocityY);
                    float absDeltaX = Math.abs(deltaX);
                    float absDeltaY = Math.abs(deltaY);
                    if (absDeltaX > absDeltaY) {
                        if (absDeltaX > minSwipeDelta && absVelocityX > minSwipeVelocity
                                && absVelocityX < maxSwipeVelocity) {
                            if (deltaX < 0) {
                                onSwipeLeft();
                            } else {
                                onSwipeRight();
                            }
                        }
                        result = true;
                    } else if (absDeltaY > minSwipeDelta && absVelocityY > minSwipeVelocity
                            && absVelocityY < maxSwipeVelocity) {
                        if (deltaY < 0) {
                            onSwipeTop();
                        } else {
                            onSwipeBottom();
                        }
                    }
                    result = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;

            }
        });

        BuildingButton = (TextView) findViewById(R.id.go_Donan);
        BuildingButton.setText("불러오는중");
        Intent intent = getIntent();
        String name;
        try {
            name = intent.getExtras().getString("GANG");
        } catch (Exception e)
        {
            name="8강의동";
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
                        BuildingButton.setText(showBuilding[buildingSelected]);
                    }
                })
                .setNegativeButton("취소", null)
                .create();

        GridView gridView = (GridView)findViewById(R.id.board_Memo);
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
                        for (int i = 0; i < BoardArray.length(); i++) {
                            try {
                                JSONObject BoardObject = BoardArray.getJSONObject(i);
                                int minor = BoardObject.getInt("minor");
                                if (minor == Minorcheck) {
                                    String content = BoardObject.getString("content");
                                    String time = BoardObject.getString("writtenTime");
                                    gAdapter.addItem(new ListItem(content, time));
                                    gridView.setAdapter(gAdapter);
                                }
                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                            }
                        }
                }
            }
        };

        HashMap<String, Object> params = new HashMap<>();
        params.put("minor", "61686");


        try {
            SendTool.request(SendTool.POST_PARAM,"/doodle/read", params, handler);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }




        ImageButton imageButton = (ImageButton)findViewById(R.id.board_Write);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                UserInformation userInfo = new UserInformation();
                String check = userInfo.sessionCheck(getBaseContext());
                if(check.equals("needLogin"))
                {
                    Intent intent = new Intent(getApplicationContext(), ActivitySignIn.class);
                    startActivity(intent);
                    finish();
                }
                else if(check.equals("needVerify"))
                {
                    Intent intent = new Intent(getApplicationContext(), VerfityActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), WriteBoard.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void onSwipeLeft() {
        Toast.makeText(this,"좌측 스와이프", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MainCommunity.class);
        startActivity(intent);
        finish();
    }

    public void onSwipeRight() {
        Toast.makeText(this,"우측 스와이프", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        intent.putExtra("title", BuildingButton.getText().toString());
        startActivity(intent);
        finish();

    }

    public void onSwipeTop() {
        Toast.makeText(this,"상단 스와이프", Toast.LENGTH_SHORT).show();
    }

    public void onSwipeBottom() {
        Toast.makeText(this,"하단 스와이프", Toast.LENGTH_SHORT).show();
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