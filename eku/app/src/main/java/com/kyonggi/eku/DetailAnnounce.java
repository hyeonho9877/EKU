package com.kyonggi.eku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

public class DetailAnnounce extends AppCompatActivity {

    /**
     * 공지 세부화면
     */
    String[] showBuilding = {"1강의동","2강의동","3강의동","4강의동","5강의동","6강의동","7강의동","8강의동","9강의동","제2공학관"};
    int buildingSelected = 0;
    int[] building = {1,2,3,4,5,6,7,8,9,0};
    AlertDialog buildingSelectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_announce);

        TextView BuildingButton = (TextView) findViewById(R.id.detail_announce_Spinner);
        BuildingButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildingSelectDialog.show();
            }
        });
        buildingSelectDialog = new AlertDialog.Builder(DetailAnnounce.this)
                .setSingleChoiceItems(showBuilding, buildingSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        buildingSelected = i;
                    }
                })
                .setTitle("강의동")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BuildingButton.setText(showBuilding[buildingSelected]);
                    }
                })
                .setNegativeButton("취소", null)
                .create();

        Intent intent = getIntent();
        int Lid = intent.getIntExtra("announce_key",-1);

        TextView textView = (TextView)findViewById(R.id.detail_announce_title);
        String text = PreferenceManagers.getString(getApplicationContext(), "announce_title"+Lid);
        textView.setText(text);

        textView = (TextView)findViewById(R.id.detail_announce_content);
        text = PreferenceManagers.getString(getApplicationContext(), "announce_content"+Lid);
        textView.setText(text);

        textView = (TextView)findViewById(R.id.detail_announce_writer);
        text = PreferenceManagers.getString(getApplicationContext(), "announce_writer"+Lid);
        textView.setText(text);

        textView = (TextView)findViewById(R.id.detail_announce_time);
        text = PreferenceManagers.getString(getApplicationContext(), "announce_time"+Lid);
        textView.setText(text);

        textView = (TextView)findViewById(R.id.detail_announce_building);
        String building = PreferenceManagers.getString(getApplicationContext(), "announce_building"+Lid);
        String buildingtemp = "";
        for(int j = 0; j < building.length(); j++) {

            if (building.charAt(j)=='1')
                buildingtemp += String.valueOf(j);
        }
        textView.setText(buildingtemp);

        LinearLayout buttonLayout =  (LinearLayout) findViewById(R.id.detail_announce_button);
        buttonLayout.setGravity(Gravity.CENTER);
        if (PreferenceManagers.getString(getApplicationContext(), "announce_writer"+Lid).equals("고일석")){
            Button modifyButton = new Button(getApplicationContext());
            modifyButton.setText("수정");
            modifyButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Dialog dialog = new Dialog(DetailAnnounce.this, android.R.style.Theme_Material_Light_Dialog);
                    dialog.setContentView(R.layout.dialog_update_anounce);
                    EditText update_free_title = dialog.findViewById(R.id.update_anounce_title);
                    EditText update_free_content = dialog.findViewById(R.id.update_anounce_content);
                    Button btn_ok = dialog.findViewById(R.id.btn_anounce_update_ok);
                    Button btn_cancle = dialog.findViewById(R.id.btn_anounce_update_cancle);

                    btn_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Handler handler = new Handler(){
                                public void handleMessage(@NonNull Message msg){
                                    switch (msg.what){
                                        case 0:
                                            String responseResult=(String)msg.obj;
                                            Toast.makeText(getApplicationContext(),responseResult,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            };

                            HashMap<String,Object> sended = new HashMap<>();
                            sended.put("id",1);
                            sended.put("title",update_free_title);
                            sended.put("content",update_free_content);
                            sended.put("buliding","1000000000");


                            try {
                                SendTool.request(SendTool.APPLICATION_JSON,"/board/free/update",sended,handler);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    dialog.show();
                }
            });
            Button deleteButton = new Button(getApplicationContext());
            deleteButton.setText("삭제");
            deleteButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"삭제", Toast.LENGTH_SHORT).show();
                }
            });
            buttonLayout.addView(modifyButton);
            buttonLayout.addView(deleteButton);
        }
        Button closeButton = new Button(getApplicationContext());
        closeButton.setText("닫기");
        closeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainCommunity.class);
                startActivity(intent);
                finish();
            }
        });
        buttonLayout.addView(closeButton);

    }

}