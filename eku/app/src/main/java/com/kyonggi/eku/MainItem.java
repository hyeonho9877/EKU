package com.kyonggi.eku;

import static com.kyonggi.eku.view.board.activity.ActivityBoard.BOARD_FREE;
import static com.kyonggi.eku.view.board.activity.ActivityBoard.BOARD_INFO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.GravityCompat;

import com.kyonggi.eku.view.board.activity.ActivityBoard;

public class MainItem extends LinearLayout {

    public MainItem(Context context, String title, Lecture L1, Lecture L2, Lecture L3) {
        super(context);
        init(context, title, L1, L2, L3);
    }

    public MainItem(Context context, String title, FreeCommunityItem F1, FreeCommunityItem F2, FreeCommunityItem F3, String buildingNumber) {
        super(context);
        init(context, title, F1, F2, F3, buildingNumber);
    }

    public MainItem(Context context, String title, ComminityItem C1, ComminityItem C2, ComminityItem C3, String buildingNumber) {
        super(context);
        init(context, title, C1, C2, C3, buildingNumber);
    }

    private void init(Context context, String title, Lecture L1, Lecture L2, Lecture L3){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_board,this,true);
        TextView textView = findViewById(R.id.MainItem_title);
        textView.setText(title);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LectureMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        Button m = findViewById(R.id.MainItem_button);
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LectureMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        textView = findViewById(R.id.MainItem_1);
        textView.setText(L1.getCtext());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LectureDetail.class);
                intent.putExtra("Name", L1.getLectureName());
                intent.putExtra("Prof", L1.getProfessor());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        textView = findViewById(R.id.MainItem_2);
        textView.setText(L2.getCtext());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LectureDetail.class);
                intent.putExtra("Name", L2.getLectureName());
                intent.putExtra("Prof", L2.getProfessor());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        textView = findViewById(R.id.MainItem_3);
        textView.setText(L3.getCtext());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LectureDetail.class);
                intent.putExtra("Name", L3.getLectureName());
                intent.putExtra("Prof", L3.getProfessor());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private void init(Context context, String title, FreeCommunityItem F1, FreeCommunityItem F2, FreeCommunityItem F3, String buildingNumber){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_board,this,true);
        TextView textView = findViewById(R.id.MainItem_title);
        textView.setText(title);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityBoard.class);
                intent.putExtra("mode", BOARD_FREE);
                intent.putExtra("buildingNumber", buildingNumber);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        Button m = findViewById(R.id.MainItem_button);
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityBoard.class);
                intent.putExtra("mode", BOARD_FREE);
                intent.putExtra("buildingNumber", buildingNumber);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        textView = findViewById(R.id.MainItem_1);
        textView.setText(F1.getFc_title());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailFreeCommunity.class);
                intent.putExtra("id",F1.getFc_id());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        textView = findViewById(R.id.MainItem_2);
        textView.setText(F2.getFc_title());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailFreeCommunity.class);
                intent.putExtra("id",F2.getFc_id());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        textView = findViewById(R.id.MainItem_3);
        textView.setText(F3.getFc_title());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailFreeCommunity.class);
                intent.putExtra("id",F3.getFc_id());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private void init(Context context, String title, ComminityItem C1, ComminityItem C2, ComminityItem C3, String buildingNumber){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_board,this,true);
        TextView textView = findViewById(R.id.MainItem_title);
        textView.setText(title);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityBoard.class);
                intent.putExtra("mode", BOARD_INFO);
                intent.putExtra("buildingNumber", buildingNumber);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        Button m = findViewById(R.id.MainItem_button);
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityBoard.class);
                intent.putExtra("mode", BOARD_INFO);
                intent.putExtra("buildingNumber", buildingNumber);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        textView = findViewById(R.id.MainItem_1);
        textView.setText(C1.getc_title());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailFreeCommunity.class);
                intent.putExtra("id",C1.getc_id());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        textView = findViewById(R.id.MainItem_2);
        textView.setText(C2.getc_title());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailFreeCommunity.class);
                intent.putExtra("id",C2.getc_id());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        textView = findViewById(R.id.MainItem_3);
        textView.setText(C3.getc_title());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailFreeCommunity.class);
                intent.putExtra("id",C3.getc_id());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
}

