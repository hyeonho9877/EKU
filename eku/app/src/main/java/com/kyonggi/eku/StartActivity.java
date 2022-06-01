package com.kyonggi.eku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {
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
                    if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(getApplicationContext(),findGang.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        showToast("근처기기 권한을 확인해 주십시오.");
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.BLUETOOTH_CONNECT,
                            Manifest.permission.BLUETOOTH_SCAN,
                            Manifest.permission.BLUETOOTH_ADVERTISE,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    1);
            //Toast.makeText(getApplicationContext(),(ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT))+"",Toast.LENGTH_LONG).show();
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            requestPermissions(
                    new String[]{
                            Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_ADMIN,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    1);
            //Toast.makeText(getApplicationContext(),(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION))+"",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"너무 하위 기종입니다.",Toast.LENGTH_LONG).show();

        }

    }

    // Request Permission에 대한 결과 값 받아와
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);

    }
}
