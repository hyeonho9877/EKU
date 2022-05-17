package com.kyonggi.eku;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

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

    int[] input_time = {0,0,0,0,0,0,0,0};
    String day="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_table);

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
                        String temp="";
                        for(int i:input_time)
                        {
                            temp+=i;
                        }
                        temp +=dialog_title.getText().toString();
                        temp +=dialog_Building.getText().toString();
                        temp +=dialog_Professor.getText().toString();
                        temp +=day;
                        dialog.dismiss();
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