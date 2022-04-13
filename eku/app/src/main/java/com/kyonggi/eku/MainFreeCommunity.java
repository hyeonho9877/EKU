package com.kyonggi.eku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainFreeCommunity extends AppCompatActivity {
    String[] items = {"1강의동","2강의동","3강의동","4강의동","5강의동","6강의동","7강의동","8강의동","9강의동","제2공학관"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_free_community);

        final DrawerLayout drawerLayout = findViewById(R.id.FreeCommunity_drawerLayout);

        findViewById(R.id.FreeCommunity_Menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.FreeCommunity_navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.lectureMain) {
                    Intent intent = new Intent(getApplicationContext(), LectureMain.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });

        Spinner spinner = (Spinner)findViewById(R.id.FreeCommunity_Spinner);
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
        ImageButton imageButton = (ImageButton)findViewById(R.id.FreeCommunity_Write);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WriteFreeCommunity.class);
                startActivity(intent);
                finish();
            }
        });

        EditText searchText = (EditText)findViewById(R.id.FreeCommunity_Searchtext);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int s, int s1, int s2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int s, int s1, int s2) {
                LinearLayout sc = (LinearLayout)findViewById(R.id.FreeCommunity_scroll);
                sc.removeAllViews();
                ///바꾸셈
                int count = PreferenceManagers.getInt(getApplicationContext(), "FreeCommunity_count");
                for (int i = count; i >= 1; i--) {
                    if (PreferenceManagers.getString(getApplicationContext(), "FreeCommunity_title" + String.valueOf(i)).contains(charSequence)) {

                        String str = "FreeCommunity_title" + i;
                        String title = PreferenceManagers.getString(getApplicationContext(), str);

                        str = "FreeCommunity_writer" + i;
                        String professor = PreferenceManagers.getString(getApplicationContext(), str);

                        ///강의동 체크

                        write_Lecture(title, professor, i);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ImageButton imageButton1 = (ImageButton)findViewById(R.id.FreeCommunity_Search);
        imageButton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //?????????????????
            }
        });
        Button moveButton = (Button)findViewById(R.id.FreeCommunity_announce_button);
        moveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainCommunity.class);
                startActivity(intent);
                finish();
            }
        });

        //initialize();
        int count = PreferenceManagers.getInt(getApplicationContext(), "FreeCommunity_count");
        if (count==-1){
            PreferenceManagers.setInt(getApplicationContext(), "FreeCommunity_count", 0);
        }
        Toast.makeText(getApplicationContext(),"자유작성"+count, Toast.LENGTH_SHORT).show();
        if (count >0){
            for (int i = count;i>=1;i--){
                if(!PreferenceManagers.getString(getApplicationContext(),"FreeCommunity_title"+String.valueOf(i)).equals("")) {
                    String str = "FreeCommunity_title" + i;
                    String title = PreferenceManagers.getString(getApplicationContext(), str);

                    str = "FreeCommunity_writer" + i;
                    String professor = PreferenceManagers.getString(getApplicationContext(), str);

                    write_Lecture(title, professor, i);
                }
            }
        }
    }

    public void write_Lecture(String Title, String writer, int count){
        LinearLayout sc = (LinearLayout)findViewById(R.id.FreeCommunity_scroll);
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
                Intent intent = new Intent(getApplicationContext(),DetailFreeCommunity.class);
                int Lid = view.getId();
                intent.putExtra("FreeCommunity_key",Lid);
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
        professorView.setText(writer);
        professorView.setGravity(Gravity.RIGHT);
        professorView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17.0f);
        professorView.setLayoutParams(linearLayoutParams);
        professorView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        linearLayout.addView(professorView);

        sc.addView(linearLayout);


    }
    public void initialize() { //초기화
        int i = PreferenceManagers.getInt(getApplicationContext(), "FreeCommunity_count");
        for (int j=0;j<=i;j++){
            PreferenceManagers.removeKey(getApplicationContext(),"FreeCommunity_name"+j);
            PreferenceManagers.removeKey(getApplicationContext(),"FreeCommunity_writer"+j);
        }
        PreferenceManagers.setInt(getApplicationContext(), "FreeCommunity_count", 0);
        Toast.makeText(getApplicationContext(),"초기화 켜져있어요", Toast.LENGTH_SHORT).show();
    }
}