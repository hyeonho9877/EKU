package com.kyonggi.eku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;

public class MainBoard extends AppCompatActivity {

    String[] items = {"1강의동","2강의동","3강의동","4강의동","5강의동","6강의동","7강의동","8강의동","9강의동","제2공학관"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);

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
}