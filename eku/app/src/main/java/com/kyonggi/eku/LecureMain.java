package com.kyonggi.eku;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class LecureMain extends AppCompatActivity {


    ImageButton imageButton;
    ImageButton imageButton1;
    String[] items = {"1강의동","2강의동","3강의동","4강의동","5강의동","6강의동","7강의동","8강의동","9강의동","제2공학관"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner)findViewById(R.id.LectureMain_spinner);
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
        imageButton = (ImageButton)findViewById(R.id.LectureMain_WriteButton);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        EditText searchText = (EditText)findViewById(R.id.LectureMain_searchtext);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int s, int s1, int s2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int s, int s1, int s2) {
                LinearLayout sc = (LinearLayout)findViewById(R.id.LectureMain_scroll);
                sc.removeAllViews();
                int count = PreferenceManagers.getInt(getApplicationContext(), "count");
                for (int i = count; i >= 1; i--) {
                    if (PreferenceManagers.getString(getApplicationContext(), "name" + String.valueOf(i)).contains(charSequence) ||
                            PreferenceManagers.getString(getApplicationContext(), "professor" + String.valueOf(i)).contains(charSequence)) {

                        String str = "name" + i;
                        String title = PreferenceManagers.getString(getApplicationContext(), str);

                        str = "professor" + i;
                        String professor = PreferenceManagers.getString(getApplicationContext(), str);

                        str = "rating" + i;
                        Float rating = PreferenceManagers.getFloat(getApplicationContext(), str);

                        write_Lecture(title, professor, rating, i);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        imageButton1 = (ImageButton)findViewById(R.id.LectureMain_searchButton);
        imageButton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //?????????????????
            }
        });
        //initialize();
        int count = PreferenceManagers.getInt(getApplicationContext(), "count");
        if (count==-1){
            PreferenceManagers.setInt(getApplicationContext(), "count", 0);
        }
        if (count >0){
            for (int i = count;i>=1;i--){
                if(!PreferenceManagers.getString(getApplicationContext(),"name"+String.valueOf(i)).equals("")) {
                    String str = "name" + i;
                    String title = PreferenceManagers.getString(getApplicationContext(), str);

                    str = "professor" + i;
                    String professor = PreferenceManagers.getString(getApplicationContext(), str);

                    str = "rating" + i;
                    Float rating = PreferenceManagers.getFloat(getApplicationContext(), str);

                    write_Lecture(title, professor, rating, i);
                }
            }
        }
    }

    public void write_Lecture(String Title, String professor, float rating, int count){
        LinearLayout sc = (LinearLayout)findViewById(R.id.LectureMain_scroll);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout .setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams linearLayoutParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        linearLayout.setId(count);
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
        Rating.setRating(rating);
        LinearLayout.LayoutParams centerParams = new LinearLayout.LayoutParams(linearLayoutParams.WRAP_CONTENT, linearLayoutParams.WRAP_CONTENT);
        centerParams.gravity = Gravity.CENTER;
        Rating.setLayoutParams(centerParams);

        linearLayout.addView(Rating);

        sc.addView(linearLayout);


    }
    public void initialize() { //일기 초기화
        int i = PreferenceManagers.getInt(getApplicationContext(), "count");
        for (int j=0;j<=i;j++){
            PreferenceManagers.removeKey(getApplicationContext(),"name"+j);
            PreferenceManagers.removeKey(getApplicationContext(),"writer"+j);
            PreferenceManagers.removeKey(getApplicationContext(),"professor"+j);
        }
        PreferenceManagers.setInt(getApplicationContext(), "count", 0);
        Toast.makeText(getApplicationContext(),"초기화 켜져있어요", Toast.LENGTH_SHORT).show();
    }
}