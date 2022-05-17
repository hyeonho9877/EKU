package com.kyonggi.eku;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainItem extends LinearLayout {

    public MainItem(Context context, String title, String content1, String content2, String content3) {
        super(context);
        init(context, title, content1, content2, content3);
    }

    private void init(Context context, String title, String content1, String content2, String content3){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_board,this,true);
        TextView textView = findViewById(R.id.MainItem_title);
        textView.setText(title);
        textView = findViewById(R.id.MainItem_1);
        textView.setText(content1);
        textView = findViewById(R.id.MainItem_2);
        textView.setText(content2);
        textView = findViewById(R.id.MainItem_3);
        textView.setText(content3);

    }

}

