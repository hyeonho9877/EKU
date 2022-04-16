package com.kyonggi.eku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
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

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DetailFreeCommunity extends AppCompatActivity {

    /**
     * 자유게시판 세부화면
     */
    String[] showBuilding = {"1강의동","2강의동","3강의동","4강의동","5강의동","6강의동","7강의동","8강의동","9강의동","제2공학관"};
    int buildingSelected = 0;
    int[] building = {1,2,3,4,5,6,7,8,9,0};
    AlertDialog buildingSelectDialog;
    int Lid;
    int count;
    LinearLayout sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_free_community);

        TextView BuildingButton = (TextView) findViewById(R.id.detail_Free_Spinner);
        BuildingButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildingSelectDialog.show();
            }
        });
        buildingSelectDialog = new AlertDialog.Builder(DetailFreeCommunity.this)
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
        Lid = intent.getIntExtra("FreeCommunity_key",-1);

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
            if (building.charAt(j)=='1') {
                buildingtemp += String.valueOf(j);
            }
        }
        textView.setText(buildingtemp + "강의동");

        count = PreferenceManagers.getInt(getApplicationContext(), "FreeCommunity_comment"+Lid);
        if (count==-1){
            PreferenceManagers.setInt(getApplicationContext(), "FreeCommunity_comment"+Lid, 0);
            count = PreferenceManagers.getInt(getApplicationContext(), "FreeCommunity_comment"+Lid);
        }
        TextView commentCount = (TextView)findViewById(R.id.textView3);
        commentCount.setText("댓글  "+count);
        if (count>0){
            update_comment(count);
        }

        ImageButton writeButton = (ImageButton) findViewById(R.id.detail_Free_Write_Comment_button);
        writeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText commentLine = (EditText) findViewById(R.id.detail_Free_Write_Comment);
                String comment = commentLine.getText().toString();
                count++;

                /*
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd hh-mm");
                String time = timeFormat.format(date);
                 */




                if (count>1)
                    sc.removeAllViews();
                update_comment(count);
            }

        });

        LinearLayout buttonLayout =  (LinearLayout) findViewById(R.id.detail_Free_button);
        buttonLayout.setGravity(Gravity.CENTER);
        if (PreferenceManagers.getString(getApplicationContext(), "FreeCommunity_writer"+Lid).equals("고지웅")){
            Button modifyButton = new Button(getApplicationContext());
            modifyButton.setText("수정");
            modifyButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Handler handler = new Handler(){
                        public void handleMessage(@NonNull Message msg){
                            switch (msg.what) {
                                case 0:
                                    String responseResult=(String)msg.obj;
                                    Toast.makeText(getApplicationContext(), responseResult, Toast.LENGTH_SHORT).show();
                            }
                        }
                    };

                    SendTool sendTool = new SendTool(handler);

                    HashMap<String,String> temp2 = new HashMap<>();
                    temp2.put("studNo","201713924");
                    temp2.put("department","컴퓨터공학과");
                    temp2.put("title","test제목");
                    temp2.put("content","test내용");

                    try {
                        sendTool.request("http://115.85.182.126:8080/comment/free/update","POST",temp2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            Button deleteButton = new Button(getApplicationContext());
            deleteButton.setText("삭제");
            deleteButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Handler handler = new Handler(){
                        public void handleMessage(@NonNull Message msg){
                            switch (msg.what) {
                                case 0:
                                    String responseResult=(String)msg.obj;
                                    Toast.makeText(getApplicationContext(), responseResult, Toast.LENGTH_SHORT).show();
                            }
                        }
                    };

                    SendTool sendTool = new SendTool(handler);

                    HashMap<String,String> temp2 = new HashMap<>();

                    temp2.put("commentId","1");

                    try {
                        sendTool.request("http://115.85.182.126:8080/comment/free/delete","POST",temp2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    public void write_comment(String comment,String writer){
        sc = (LinearLayout)findViewById(R.id.detail_Free_scroll);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout .setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams linearLayoutParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
        TextView textView = new TextView(getApplicationContext());
        textView.setText(comment);
        textView.setGravity(Gravity.LEFT);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13.0f);
        textView.setLayoutParams(linearLayoutParams);
        textView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        linearLayout.addView(textView);

        TextView textview2 = new TextView(getApplicationContext());
        textview2.setText(writer);
        textview2.setGravity(Gravity.RIGHT);
        textview2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10.0f);
        textview2.setLayoutParams(linearLayoutParams);
        textview2.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        linearLayout.addView(textview2);

        sc.addView(linearLayout);
    }

    public void update_comment(int count){
        for (int i = 1;i<=count;i++){
            String str = "FreeCommunity_comment_content"+ Lid + i;
            String comment = PreferenceManagers.getString(getApplicationContext(), str);

            str = "FreeCommunity_comment_writer"+ Lid + i;;
            String writer = PreferenceManagers.getString(getApplicationContext(), str);

            write_comment(comment, writer);
        }

    }

}