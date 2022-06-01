package com.kyonggi.eku;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

    private MinewBeaconManager mMinewBeaconManager;
    private BeaconListAdapter mAdapter;
    private static final int REQUEST_ENABLE_BT = 2;
    private boolean isScanning;
    UserRssi comp = new UserRssi();
    private int state;
    boolean stealing=false;
    int gone=0;
    /*
     **
     *  비콘
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_gang);

        initManager();
        Button b = findViewById(R.id.skipButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gone==0) {
                    Intent intent = new Intent(getApplicationContext(), MainBoard.class);
                    intent.putExtra("NoMap", "O");
                    gone++;
                    startActivity(intent);
                    finish();

                }
            }
        });
        mMinewBeaconManager.startScan();
        initListener();



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
                Toast.makeText(getApplicationContext(),"탐색 중..",Toast.LENGTH_LONG).show();
                for(MinewBeacon m :minewBeacons) {

                    String temp = m.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Minor).getStringValue();
                    Intent intent = new Intent(getApplicationContext(), MainBoard.class);
                    if(temp.equals("61686"))
                    {
                        intent.putExtra("GANG","8강의동");
                        intent.putExtra("NoMap","1");
                        if(gone==0)
                        {
                            Toast.makeText(getApplicationContext(),temp,Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                            gone++;
                            mMinewBeaconManager.stopScan();
                            finish();
                        }
                        else{
                        }

                    }
                    else if(temp.equals("61633"))
                    {
                        intent.putExtra("GANG","종합강의동");
                        intent.putExtra("NoMap","1");
                        if(gone==0)
                        {
                            Toast.makeText(getApplicationContext(),temp,Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                            gone++;
                            mMinewBeaconManager.stopScan();
                            finish();
                        }
                        else{
                        }

                    }
                    else if(temp.equals("61524"))
                    {
                        intent.putExtra("GANG","제2공학관");
                        intent.putExtra("NoMap","1");
                        if(gone==0)
                        {
                            Toast.makeText(getApplicationContext(),temp,Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                            gone++;
                            mMinewBeaconManager.stopScan();
                            finish();
                        }
                        else{
                        }

                    }
                    else if(temp.equals("61632"))
                    {
                        intent.putExtra("GANG","7강의동");
                        intent.putExtra("NoMap","1");
                        if(gone==0)
                        {
                            Toast.makeText(getApplicationContext(),temp,Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                            gone++;
                            mMinewBeaconManager.stopScan();
                            finish();
                        }
                        else{
                        }

                    }
                    else if(temp.equals("61618")) {
                        intent.putExtra("GANG","6강의동");
                        intent.putExtra("NoMap","1");
                        if(gone==0)
                        {
                            Toast.makeText(getApplicationContext(),temp,Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                            gone++;
                            mMinewBeaconManager.stopScan();
                            finish();
                        }
                        else{
                        }

                    }



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
    /*private void showBLEDialog() {
        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
    }*/

}