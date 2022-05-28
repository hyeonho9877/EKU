package com.kyonggi.eku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    private PermissionSupport permission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        permissionCheck();
        bluetoothOn();
        Button b = findViewById(R.id.startButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN)
                        == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(getApplicationContext(),findGang.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    showToast("블루투스 권한을 확인해 주십시오.");
                }
            }
        });
    }
    private void bluetoothOn() {
        BluetoothAdapter ap = BluetoothAdapter.getDefaultAdapter();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ap == null)
            showToast("bluetooth 를 지원하지 않습니다.");
        if (!ap.isEnabled())
        {
            Toast.makeText(getBaseContext(),"블루투스가 꺼져있습니다. 강의동 확인을 위해 블루투스를 켠 후 재실행 바랍니다.",Toast.LENGTH_LONG).show();
            Intent blueIntent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
            startActivity(blueIntent);
            finish();
        }
        else if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(getBaseContext(),"위치정보가 꺼져있습니다. 강의동 확인을 위해 위치 정보를 켠 후 재실행 바랍니다.",Toast.LENGTH_LONG).show();
            Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(gpsOptionsIntent);
            finish();
        }

    }
    private void showToast(String s) {
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show();
    }

    private void permissionCheck() {
        // PermissionSupport.java 클래스 객체 생성
        permission = new PermissionSupport(this, this);
        // 권한 체크 후 리턴이 false로 들어오면

        permission.requestPermission();

    }

    // Request Permission에 대한 결과 값 받아와
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //여기서도 리턴이 false로 들어온다면 (사용자가 권한 허용 거부)
        if (!permission.permissionResult(requestCode, permissions, grantResults)) {
            // 다시 permission 요청
            permission.requestPermission();
        }

    }


}