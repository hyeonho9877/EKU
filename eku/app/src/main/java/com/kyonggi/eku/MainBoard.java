package com.kyonggi.eku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class MainBoard extends AppCompatActivity {

    String[] items = {"1강의동","2강의동","3강의동","4강의동","5강의동","6강의동","7강의동","8강의동","9강의동","제2공학관"};
    GestureDetector gestureDetector = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);

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

        Spinner spinner = (Spinner)findViewById(R.id.board_Spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //선택
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //없음
            }
        });
        //initialize();
        GridView gridView = (GridView)findViewById(R.id.board_Memo);
        GridListAdapter gAdapter = new GridListAdapter();

        int count = PreferenceManagers.getInt(getApplicationContext(), "board_Count");

        for (int i=1; i<=count; i++){
            String content = PreferenceManagers.getString(getApplicationContext(), "board"+i);
            String time = PreferenceManagers.getString(getApplicationContext(), "time"+i);
            gAdapter.addItem(new ListItem(content,time));
        }

        gridView.setAdapter(gAdapter);

        ImageButton imageButton = (ImageButton)findViewById(R.id.board_Write);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WriteBoard.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void initialize() { //데이터 초기화
        int i = PreferenceManagers.getInt(getApplicationContext(), "board_Count");
        for (int j=1;j<=i;j++){
            PreferenceManagers.removeKey(getApplicationContext(),"board"+j);
            PreferenceManagers.removeKey(getApplicationContext(),"time"+j);
        }
        PreferenceManagers.setInt(getApplicationContext(), "board_Count", 0);
    }
    public void onSwipeLeft() {
        Toast.makeText(this,"좌측 스와이프", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MainCommunity.class);
        startActivity(intent);
        finish();
    }

    public void onSwipeRight() {
        Toast.makeText(this,"우측 스와이프", Toast.LENGTH_SHORT).show();
        /*   ---------------------여기 지도 activity 넣어주셈-------------------------
        Intent intent = new Intent(getApplicationContext(), 지도.class);
        startActivity(intent);
        finish();

         */

    }

    public void onSwipeTop() {
        Toast.makeText(this,"상단 스와이프", Toast.LENGTH_SHORT).show();
    }

    public void onSwipeBottom() {
        Toast.makeText(this,"하단 스와이프", Toast.LENGTH_SHORT).show();
    }
}