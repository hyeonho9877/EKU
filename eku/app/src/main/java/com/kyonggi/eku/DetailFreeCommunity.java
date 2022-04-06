package com.kyonggi.eku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class DetailFreeCommunity extends AppCompatActivity {

    String[] items = {"1강의동","2강의동","3강의동","4강의동","5강의동","6강의동","7강의동","8강의동","9강의동","제2공학관"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_free_community);

        Spinner spinner = (Spinner)findViewById(R.id.detail_Free_Spinner);
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
        int Lid = intent.getIntExtra("FreeCommunity_key",-1);

        TextView textView = (TextView)findViewById(R.id.detail_Free_title);
        String text = PreferenceManagers.getString(getApplicationContext(), "FreeCommunity_title"+Lid);
        textView.setText(text);

        textView = (TextView)findViewById(R.id.detail_Free_content);
        text = PreferenceManagers.getString(getApplicationContext(), "FreeCommunity_content"+Lid);
        textView.setText(text);

        textView = (TextView)findViewById(R.id.detail_Free_writer);
        text = PreferenceManagers.getString(getApplicationContext(), "FreeCommunity_writer"+Lid);
        textView.setText(text);

        textView = (TextView)findViewById(R.id.detail_Free_time);
        text = PreferenceManagers.getString(getApplicationContext(), "FreeCommunity_time"+Lid);
        textView.setText(text);

        textView = (TextView)findViewById(R.id.detail_Free_building);
        String building = PreferenceManagers.getString(getApplicationContext(), "FreeCommunity_building"+Lid);
        String buildingtemp = "";
        for(int j = 0; j < building.length(); j++) {

            if (building.charAt(j)=='1')
                buildingtemp += String.valueOf(j);
        }
        textView.setText(buildingtemp);

        Button closeButton = (Button) findViewById(R.id.detail_Free_close);
        closeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}