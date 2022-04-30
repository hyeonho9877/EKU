package com.kyonggi.eku;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.gridlayout.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleTable extends AppCompatActivity {

    /*
     *
     * 제목
     * 시간표
     * 기능
     * 모양만
     * */
    String[] showBuilding = {"1강의동","2강의동","3강의동","4강의동","5강의동","6강의동","7강의동","8강의동","9강의동","제2공학관"};
    int buildingSelected = 0;
    int[] building = {1,2,3,4,5,6,7,8,9,0};
    AlertDialog buildingSelectDialog;
    long backKeyPressedTime;
    EditText password;

    int[] input_time = {0,0,0,0,0,0,0,0};
    String day="";
    JSONArray lecture = new JSONArray();
    boolean[][] check={{false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_table);
        password =findViewById(R.id.Schedule_password);
        UserInformation userInformation = new UserInformation(getApplicationContext());
 /*       if (!(userInformation.fromPhoneVerify(getApplicationContext())))
        {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }*/

        GridLayout lp = (GridLayout) findViewById(R.id.gridLayout);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        int w = lp.getWidth();
        int h = lp.getHeight();
        Button button2 = new Button(ScheduleTable.this);
        button2.setWidth((int)dp2px(30.0f));
        button2.setHeight((int)dp2px(50.0f));

        Toast.makeText(getApplicationContext(),lp.getRowCount()+"야생"+lp.getColumnCount(),Toast.LENGTH_LONG).show();
        GridLayout.LayoutParams gridLayoutWitch = new GridLayout.LayoutParams();
        gridLayoutWitch.rowSpec = GridLayout.spec(3);
        gridLayoutWitch.columnSpec = GridLayout.spec(3);
        lp.addView(button2,gridLayoutWitch);


        TextView BuildingButton = (TextView) findViewById(R.id.schedule_Spinner);
        BuildingButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildingSelectDialog.show();
            }
        });
        buildingSelectDialog = new AlertDialog.Builder(ScheduleTable.this)
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

        final DrawerLayout drawerLayout = findViewById(R.id.schedule_drawerLayout);

        findViewById(R.id.schedule_Menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.schedule_navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                switch(id) {
                    case R.id.Home:
                        intent = new Intent(getApplicationContext(), MainBoard.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.Announce:
                        intent = new Intent(getApplicationContext(), MainCommunity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.Free:
                        intent = new Intent(getApplicationContext(), MainFreeCommunity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.lectureMain:
                        intent = new Intent(getApplicationContext(), LectureMain.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.ToDo:
                        intent = new Intent(getApplicationContext(), TodoActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.TimeTable:
                        intent = new Intent(getApplicationContext(), ScheduleTable.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return false;
            }
        });

        Button UpButton = (Button) findViewById(R.id.schedule_up_button);
        UpButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button DownButton = (Button) findViewById(R.id.schedule_down_button);
        DownButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button AddButton = (Button) findViewById(R.id.schedule_add_button);
        AddButton.setOnClickListener(new Button.OnClickListener() {


            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(ScheduleTable.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.dialog_schedule);
                EditText dialog_title = dialog.findViewById(R.id.timeTableTitle);
                EditText dialog_Professor = dialog.findViewById(R.id.timeTableProfessor);
                EditText dialog_Building = dialog.findViewById(R.id.timeTableBuilding);

                RadioButton mon = (RadioButton) dialog.findViewById(R.id.TimeTableMon);
                RadioButton tue = (RadioButton) dialog.findViewById(R.id.TimeTableTue);
                RadioButton wed = (RadioButton) dialog.findViewById(R.id.TimeTableWed);
                RadioButton thu = (RadioButton) dialog.findViewById(R.id.TimeTableThu);
                RadioButton fri = (RadioButton) dialog.findViewById(R.id.TimeTableFri);
                RadioGroup gro = (RadioGroup) dialog.findViewById(R.id.timeTableRadioGroup);
                gro.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if(i == R.id.TimeTableMon)
                        {
                            day="월";
                        }
                        else if(i == R.id.TimeTableTue)
                        {
                            day="화";
                        }
                        else if(i == R.id.TimeTableWed)
                        {
                            day="수";
                        }
                        else if(i == R.id.TimeTableThu)
                        {
                            day="목";
                        }
                        if(i == R.id.TimeTableFri)
                        {
                            day="금";
                        }
                    }
                });



                        /*
                         * 시간표 확인할때
                         * 1교시 => 0번입니다..
                         * */
                CheckBox checkBox1 = (CheckBox) dialog.findViewById(R.id.TimeTable_1);
                checkBox1.setOnClickListener(new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox)v).isChecked()) {
                            input_time[0]=1;
                        } else {
                            input_time[0]=0;
                        }
                    }
                }) ;
                CheckBox checkBox2 = (CheckBox) dialog.findViewById(R.id.TimeTable_2) ;
                checkBox2.setOnClickListener(new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox)v).isChecked()) {
                            input_time[1]=1;
                        } else {
                            input_time[1]=0;
                        }
                    }
                }) ;
                CheckBox checkBox3 = (CheckBox) dialog.findViewById(R.id.TimeTable_3) ;
                checkBox3.setOnClickListener(new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox)v).isChecked()) {
                            input_time[2]=1;
                        } else {
                            input_time[2]=0;
                        }
                    }
                }) ;
                CheckBox checkBox4 = (CheckBox) dialog.findViewById(R.id.TimeTable_4) ;
                checkBox4.setOnClickListener(new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox)v).isChecked()) {
                            input_time[3]=1;
                        } else {
                            input_time[3]=0;
                        }
                    }
                }) ;
                CheckBox checkBox5 = (CheckBox) dialog.findViewById(R.id.TimeTable_5) ;
                checkBox5.setOnClickListener(new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox)v).isChecked()) {
                            input_time[4]=1;
                        } else {
                            input_time[4]=0;
                        }
                    }
                }) ;
                CheckBox checkBox6 = (CheckBox) dialog.findViewById(R.id.TimeTable_6) ;
                checkBox6.setOnClickListener(new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox)v).isChecked()) {
                            input_time[5]=1;
                        } else {
                            input_time[5]=0;
                        }
                    }
                }) ;
                CheckBox checkBox7 = (CheckBox) dialog.findViewById(R.id.TimeTable_7) ;
                checkBox7.setOnClickListener(new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox)v).isChecked()) {
                            input_time[6]=1;
                        } else {
                            input_time[6]=0;
                        }
                    }
                }) ;
                CheckBox checkBox8 = (CheckBox) dialog.findViewById(R.id.TimeTable_8) ;
                checkBox8.setOnClickListener(new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox)v).isChecked()) {
                            input_time[7]=1;
                        } else {
                            input_time[7]=0;
                        }
                    }
                });
                Button btn_ok = dialog.findViewById(R.id.TimeTable_ok);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String temp = "";
                        for (int i : input_time) {
                            temp += i;
                        }
                        temp += dialog_title.getText().toString();
                        temp += dialog_Building.getText().toString();
                        temp += dialog_Professor.getText().toString();
                        temp += day;
                        dialog.dismiss();

                        boolean checked=false;
                        if (day.equals("월")) {
                            for(int i=0;i<input_time.length;i++)
                            {
                                if(input_time[i]==1 && check[0][i]==true)
                                {
                                    checked=true;
                                }
                            }
                        } else if (day.equals("화")) {
                            for(int i=0;i<input_time.length;i++)
                            {
                                if(input_time[i]==1 && check[1][i]==true)
                                {
                                    checked=true;
                                }
                            }

                        } else if (day.equals("수")) {
                            for(int i=0;i<input_time.length;i++)
                            {
                                if(input_time[i]==1 && check[2][i]==true)
                                {
                                    checked=true;
                                }
                            }

                        }
                        else if (day.equals("목"))
                        {
                            for(int i=0;i<input_time.length;i++)
                            {
                                if(input_time[i]==1 && check[3][i]==true)
                                {
                                    checked=true;
                                }
                            }
                        }
                        else if(day.equals("금"))
                        {
                            for(int i=0;i<input_time.length;i++)
                            {
                                if(input_time[i]==1 && check[4][i]==true)
                                {
                                    checked=true;
                                }
                            }
                        }

                        if(checked)
                        {
                            Toast.makeText(getApplicationContext(),"중복되는 값이 있습니다.",Toast.LENGTH_LONG).show();
                        }
                        else{
                            JSONObject jsonObject = new JSONObject();
                            try {
                                String lecture_time="";
                                lecture_time+=day;
                                for(int i=0;i<input_time.length;i++)
                                {
                                    if(input_time[i]==1)
                                    {
                                        int j=i;
                                        lecture_time+=(j+1);
                                    }
                                }
                                jsonObject.put("day",lecture_time);
                                jsonObject.put("lecture_room",dialog_Building);
                                jsonObject.put("professor",dialog_Professor);
                                jsonObject.put("lecture_name",dialog_title);
                                jsonObject.put("studNo",userInformation.fromPhoneStudentNo(getApplicationContext()));
                                jsonObject.put("password",password.getText().toString());
                                lecture.put(jsonObject);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        JSONObject element;
                        for(int i=0;i<lecture.length();i++){
                            element = (JSONObject) lecture.opt(i);




                        }



                        /*
                        * inputTime 초기화
                        * */
                        for(int i=0;i<input_time.length;i++)
                        {
                            input_time[i]=0;
                        }
                        Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();

                    }
                });
                Button btn_cancel = dialog.findViewById(R.id.TimeTable_cancel);


                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        Button DelButton = (Button) findViewById(R.id.schedule_delete_button);
        DelButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    public float dp2px(float dp){
        Resources resources = this.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    //px을 dp로 변환 (px을 입력받아 dp를 리턴)
    public float px2dp(float px){
        Resources resources = this.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "뒤로 가기 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            finish();
        }
    }
}