package com.kyonggi.eku;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.minew.beacon.BeaconValueIndex;
import com.minew.beacon.BluetoothState;
import com.minew.beacon.MinewBeacon;
import com.minew.beacon.MinewBeaconManager;
import com.minew.beacon.MinewBeaconManagerListener;

import java.util.List;

public class DonanBagGi extends AppCompatActivity {
    private PermissionSupport permission;
    private MinewBeaconManager mMinewBeaconManager;
    private boolean isScanning;
    boolean stealing=false;
    TextView textView;
    Button button;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donan_bag_gi);
        bluetoothOn();
        permissionCheck();
        initManager();
        initListener();
    }

    private void bluetoothOn(){
        BluetoothAdapter ap = BluetoothAdapter.getDefaultAdapter();
        ap.enable();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(getBaseContext(),"위치정보가 꺼져있습니다. 알람 확인을 위해 위치 정보를 켜주세요",Toast.LENGTH_LONG).show();
            Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(gpsOptionsIntent);
        }
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            Toast.makeText(getBaseContext(),"불루투스와 위치를 켜지 않아 작동을 중지합니다.",Toast.LENGTH_SHORT).show();
            return;
        }
    }
    // 권한 체크
    private void permissionCheck() {
        // PermissionSupport.java 클래스 객체 생성
        permission = new PermissionSupport(this, this);
        // 권한 체크 후 리턴이 false로 들어오면
        if (!permission.checkPermission()){
            //권한 요청
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
        button= findViewById(R.id.Donan_Button);
        textView = findViewById(R.id.Donan_TextView);
        b = findViewById(R.id.BackButton);
        mMinewBeaconManager = MinewBeaconManager.getInstance(this);
        Toast.makeText(this,"켜짐",Toast.LENGTH_SHORT).show();
    }


    private void initListener() {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainBoard.class);
                startActivity(intent);
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //스캔 발견하면 스캔시작되었다고 말해줌
                if (isScanning) {
                    button.setBackgroundColor(Color.BLUE);
                    button.setText("작동하기");
                    isScanning = false;
                    textView.setText("도난방지 해제됨");
                    if (mMinewBeaconManager != null) {
                        mMinewBeaconManager.stopScan();

                    }
                    stealing=false;
                } else {
                    button.setText("해제하기");
                    button.setBackgroundColor(Color.RED);
                    //아니었으면 멈춰싿고 함
                    isScanning = true;
                    textView.setText("도난방지 작동됨");
                    try {
                        mMinewBeaconManager.startScan();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mMinewBeaconManager.setDeviceManagerDelegateListener(new MinewBeaconManagerListener() {
            /**
             *   비콘 새로 등판시 하는일.
             *  @param minewBeacons  new beacons the manager scanned
             */
            @Override
            public void onAppearBeacons(List<MinewBeacon> minewBeacons) {


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
                for(MinewBeacon m :minewBeacons) {
                    Toast.makeText(getApplicationContext(),   m.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_RSSI).getStringValue()+"", Toast.LENGTH_SHORT).show();
                    String temp = m.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_UUID).getStringValue();
                    String rssi = m.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_RSSI).getStringValue();
                    double iRssi = 0;
                    try {
                        iRssi = Double.valueOf(rssi);
                    } catch (Exception e) {
                        iRssi = 0;
                    }
                    if (temp.equals("E2C56DB5-DFFB-48D2-B060-D0F5A71096E0") && iRssi < -80) {
                        if (stealing == true)
                        {
                        }
                        else{
                            stealing=true;
                            textView.setText("훔쳐진 디바이스 입니다!!!!!");
                            AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            audio.setStreamVolume(AudioManager.STREAM_MUSIC,15,AudioManager.FLAG_PLAY_SOUND);
                            Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                            vibrator.vibrate(new long[]{50,300},0); // 0.5초간 진동
                            MediaPlayer player = MediaPlayer.create(getBaseContext(),R.raw.sirent);
                            player.setLooping(true);
                            player.start();
                            Button offButton = findViewById(R.id.Donan_Off);
                            offButton.setVisibility(View.VISIBLE);
                            offButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    offButton.setVisibility(View.INVISIBLE);
                                    player.stop();
                                    vibrator.cancel();
                                    player.release();
                                    stealing=false;
                                    textView.setText("도난방지 해제됨");
                                    button.setBackgroundColor(Color.BLUE);
                                    button.setText("작동하기");
                                    isScanning = false;
                                    if (mMinewBeaconManager != null) {
                                        mMinewBeaconManager.stopScan();
                                    }
                                    return;
                                }
                            });
                        }


                    }
                }
                //Toast.makeText(getApplicationContext(),  "비콘개수==>"+list.size(), Toast.LENGTH_SHORT).show();
             /*   if(minewBeacons.size()>=1)
                {
                    for (MinewBeacon minewBeacon : minewBeacons) {
                        String deviceName = minewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_RSSI).getStringValue();
                        Toast.makeText(getApplicationContext(), deviceName + "만큼 떨어짐", Toast.LENGTH_SHORT).show();
                    }
                }*/
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

}
