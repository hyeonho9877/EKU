package com.kyonggi.eku;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.BuildCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TodoActivity extends AppCompatActivity {

    /*
     제목 : todolist


     기능 : todolist 추가 기능 수행


     */
    String[] showBuilding = {"1강의동","2강의동","3강의동","4강의동","5강의동","6강의동","7강의동","8강의동","9강의동","제2공학관"};
    int buildingSelected = 0;
    int[] building = {1,2,3,4,5,6,7,8,9,0};
    AlertDialog buildingSelectDialog;
    long backKeyPressedTime;

    private RecyclerView mRv_todo;
    private FloatingActionButton mBtn_write;
    private ArrayList<TodoItem> mTodoItems;
    private DBHelper mDBHelper;
    private CustomAdapter mAdapter;
    AlarmManager alarmManager=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this,BootReceiver.class);
        Button button = findViewById(R.id.TodoAlarmButton);
        if(PreferenceManagers.getString(getApplicationContext(),"TODO").equals("1")){
            button.setText("오전 알람취소");
        }
        else{
            button.setText("오전 알람등록");
        }
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String value;
                if(PreferenceManagers.getString(getApplicationContext(),"TODO").equals("1")&&alarmManager!=null){
                    alarmManager=(AlarmManager)TodoActivity.this.getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent,PendingIntent.FLAG_IMMUTABLE);
                    alarmManager.cancel(alarmIntent);
                    if(alarmIntent !=null)
                    {
                        alarmIntent.cancel();
                    }
                    button.setText("오전 알람등록");
                    //Toast.makeText(getApplicationContext(),"알람 취소가 완료되었습니다.",Toast.LENGTH_SHORT).show();
                    PreferenceManagers.setString(getApplicationContext(),"TODO","가나다라마바사");
                    pm.setComponentEnabledSetting(receiver,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
                }
                else{
                    button.setText("오전 알람취소");
                    PreferenceManagers.setString(getApplicationContext(),"TODO","1");
                    //Toast.makeText(getApplicationContext(),"알람 등록이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    int hour=8;
                    int minute=30;
                    int sec =0;
                    calendar.set(Calendar.HOUR_OF_DAY,hour);
                    calendar.set(Calendar.MINUTE,minute);
                    calendar.set(Calendar.SECOND,sec);
                    PendingIntent alarmIntent;
                    alarmManager=(AlarmManager)TodoActivity.this.getSystemService(Context.ALARM_SERVICE);

                    if (alarmManager != null) {
                        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent,PendingIntent.FLAG_IMMUTABLE);
                        Toast.makeText(getApplicationContext(),calendar.getTime().toString(),Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis()+AlarmManager.INTERVAL_DAY, alarmIntent);
                        } else {
                            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY, alarmIntent);
                        }

                        //Toast.makeText(TodoActivity.this, "알람이 저장되었습니다.", Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getApplicationContext(),PreferenceManagers.getString(getApplicationContext(),"TODO"),Toast.LENGTH_SHORT).show();
                    pm.setComponentEnabledSetting(receiver,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
                }


            }
        });
        setInit();
    }

    private void setInit() {
        mDBHelper = new DBHelper(this);

        mRv_todo = findViewById(R.id.rv_todo);
        mBtn_write = findViewById(R.id.btn_write);
        mTodoItems = new ArrayList<>();

        loadRecentDB();

        mBtn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(TodoActivity.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.dialog_edit);
                EditText et_title = dialog.findViewById(R.id.timeTableTitle);
                EditText et_content = dialog.findViewById(R.id.timeTableProfessor);
                Button btn_ok = dialog.findViewById(R.id.TimeTable_ok);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(et_title.getText().toString().length() <=0 ){
                            dialog_not_access_title();
                        }
                        else if(et_content.getText().toString().length() <=0){
                            dialog_not_access_content();
                        }
                        else {
                            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                            mDBHelper.InsertTodo(et_title.getText().toString(), et_content.getText().toString(), currentTime);

                            TodoItem item = new TodoItem();
                            item.setTitle(et_title.getText().toString());
                            item.setContent(et_content.getText().toString());
                            item.setWriteDate(currentTime);

                            mAdapter.addItem(item);
                            mRv_todo.smoothScrollToPosition(0);
                            dialog.dismiss();
                            Toast.makeText(TodoActivity.this, "할일 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                dialog.show();
            }
        });

    }

    private void loadRecentDB() {

        mTodoItems = mDBHelper.getTodoList();
        if(mAdapter == null) {
            mAdapter = new CustomAdapter(mTodoItems,this);
            mRv_todo.setHasFixedSize(true);
            mRv_todo.setAdapter(mAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            Intent intent = new Intent(getApplicationContext(),MainBoard.class);
            startActivity(intent);
            finish();
            //Toast.makeText(this, "뒤로 가기 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            finish();
        }
    }
    private void dialog_not_access_title() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("해야할 일이 너무 짧습니다.");
        builder.setMessage("할일 목록에 추가할 수 없습니다.");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
    private void dialog_not_access_content() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("상세내용이 너무 짧습니다.");
        builder.setMessage("할일 목록에 추가할 수 없습니다.");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
}