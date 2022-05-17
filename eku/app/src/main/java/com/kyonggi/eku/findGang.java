package com.kyonggi.eku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.minew.beacon.BeaconValueIndex;
import com.minew.beacon.BluetoothState;
import com.minew.beacon.MinewBeacon;
import com.minew.beacon.MinewBeaconManager;
import com.minew.beacon.MinewBeaconManagerListener;

import java.util.List;

public class findGang extends AppCompatActivity {

    /**
     * 비콘
     */
    private PermissionSupport permission;
    private MinewBeaconManager mMinewBeaconManager;
    private BeaconListAdapter mAdapter;
    private static final int REQUEST_ENABLE_BT = 2;
    private boolean isScanning;
    UserRssi comp = new UserRssi();
    private int state;
    boolean stealing=false;

    /*
     **
     *  비콘
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_gang);
        bluetoothOn();
        permissionCheck();
        initManager();
        initListener();
        mMinewBeaconManager.startScan();
        Button b = findViewById(R.id.skipButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainBoard.class);
                startActivity(intent);
                finish();
            }
        });


    }
    private void bluetoothOn(){
        BluetoothAdapter ap = BluetoothAdapter.getDefaultAdapter();
        if (ap == null)
            showToast("bluetooth 를 지원하지 않습니다.");
        else
            showToast("bluetooth 를 지원합니다.");
        ap.enable();
    }

    private void showToast(String s) {
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show();
    }

    private void permissionCheck() {
        // PermissionSupport.java 클래스 객체 생성
        permission = new PermissionSupport(this, this);
        // 권한 체크 후 리턴이 false로 들어오면
        if (!permission.checkPermission()){
            //권한 요청
            //후에 권한 요청 설명 필요할 꺼 같음
            permission.requestPermission();
        }
    }

    // Request Permission에 대한 결과 값 받아와
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //여기서도 리턴이 false로 들어온다면 (사용자가 권한 허용 거부)
        if (!permission.permissionResult(requestCode, permissions, grantResults)) {
            // 다시 permission 요청
            permission.requestPermission();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //매니저 초기화
    private void initManager() {
        mMinewBeaconManager = MinewBeaconManager.getInstance(this);

    }


    private void initListener() {
        mMinewBeaconManager.setDeviceManagerDelegateListener(new MinewBeaconManagerListener() {
            /**
             *   비콘 새로 등판시 하는일.
             *  @param minewBeacons  new beacons the manager scanned
             */
            @Override
            public void onAppearBeacons(List<MinewBeacon> minewBeacons) {
                for(MinewBeacon m :minewBeacons) {
                    String temp = m.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Minor).getStringValue();
                    Intent intent = new Intent(getApplicationContext(), MainBoard.class);
                    if(temp.equals("61686"))
                    {
                        intent.putExtra("GANG","8강의동");
                    }
                    if(temp.equals("61633"))
                    {
                        intent.putExtra("GANG","4강의동");
                    }
                    if(temp.equals("61524"))
                    {
                        intent.putExtra("GANG","5강의동");
                    }
                    if(temp.equals("61632"))
                    {
                        intent.putExtra("GANG","7강의동");
                    }
                    if(temp.equals("61618")) {
                        intent.putExtra("GANG","6강의동");
                    }
                    mMinewBeaconManager.stopScan();
                    startActivity(intent);
                    finish();
                }


            }
            /**
             *  if a beacon didn't update data in 10 seconds, we think this beacon is out of rang, the manager will call back this method.
             *  비콘이 사라졌을 경우
             *  @param minewBeacons beacons out of range
             */
            @Override
            public void onDisappearBeacons(List<MinewBeacon> minewBeacons) {


            }

            @Override
            public void onRangeBeacons(List<MinewBeacon> minewBeacons) {

            }

            @Override
            public void onUpdateState(BluetoothState bluetoothState) {

            }


        });
    }
    /*
     * 블루투스 스캔을 때려쳤을 때쓰는 코드
     * */
    @Override
    protected void onDestroy() {

        super.onDestroy();

        //stop scan
        if (isScanning) {
            mMinewBeaconManager.stopScan();
        }
    }
    /*
    BLE다이어로그를 보여준다.
    * */
    private void showBLEDialog() {
        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
    }

}