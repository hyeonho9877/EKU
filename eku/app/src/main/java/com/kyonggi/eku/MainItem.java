package com.kyonggi.eku;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainItem extends LinearLayout {

    public MainItem(Context context, String title, Lecture L1, Lecture L2, Lecture L3) {
        super(context);
        init(context, title, L1, L2, L3);
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



}

