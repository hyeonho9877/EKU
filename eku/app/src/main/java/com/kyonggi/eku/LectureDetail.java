package com.kyonggi.eku;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

public class LectureDetail extends AppCompatActivity {

    String[] items = {"1강의동","2강의동","3강의동","4강의동","5강의동","6강의동","7강의동","8강의동","9강의동","제2공학관"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_detail);

        Spinner spinner = (Spinner)findViewById(R.id.lecture_detail_spinner);
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

        Intent intent = getIntent();
        int Lid = intent.getIntExtra("key",-1);

        TextView textView = (TextView)findViewById(R.id.lecture_detail_name);
        String name = PreferenceManagers.getString(getApplicationContext(), "name"+Lid);
        textView.setText(name);

        textView = (TextView)findViewById(R.id.lecture_detail_professor);
        name = PreferenceManagers.getString(getApplicationContext(), "professor"+Lid);
        textView.setText(name);

        RatingBar ratingStar = findViewById(R.id.lecture_detail_rating);
        Float rating = PreferenceManagers.getFloat(getApplicationContext(),"rating" + Lid);
        ratingStar.setRating(rating);

        Button closeButton = (Button) findViewById(R.id.lecture_detail_CloseButton);
        closeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        detail_Lecture(Lid);
    }

    public void detail_Lecture(int Lid){
        LinearLayout sc = (LinearLayout)findViewById(R.id.lecture_detail_scroll);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams linearLayoutParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        /*
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                int Lid = view.getId();
                intent.putExtra("key",Lid);
                // Toast.makeText(getApplicationContext(),String.valueOf(Lid), Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();;

            }
        });
         */

        TextView textView = new TextView(getApplicationContext());
        textView.setText("작성자 : " + PreferenceManagers.getString(getApplicationContext(), "writer"+Lid));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17.0f);
        textView.setLayoutParams(linearLayoutParams);
        textView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        linearLayout.addView(textView);


        TextView scoreView = new TextView(getApplicationContext());
        scoreView.setText("학점 : " + PreferenceManagers.getString(getApplicationContext(), "score"+Lid));
        scoreView.setGravity(Gravity.RIGHT);
        scoreView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17.0f);
        scoreView.setLayoutParams(linearLayoutParams);
        scoreView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        linearLayout.addView(scoreView);

        TextView contentView = new TextView(getApplicationContext());
        contentView.setText(PreferenceManagers.getString(getApplicationContext(), "content"+Lid));
        contentView.setGravity(Gravity.LEFT);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17.0f);
        contentView.setLayoutParams(linearLayoutParams);
        contentView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        linearLayout.addView(contentView);

        linearLayout.setBackgroundColor(Color.GRAY);
        sc.addView(linearLayout);


    }


}