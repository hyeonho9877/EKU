package com.kyonggi.eku;

import android.content.Context;
import android.view.LayoutInflater;
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
        textView = findViewById(R.id.MainItem_1);
        textView.setText(L1.getCtext());
        textView = findViewById(R.id.MainItem_2);
        textView.setText(L2.getCtext());
        textView = findViewById(R.id.MainItem_3);
        textView.setText(L3.getCtext());

    }

}

