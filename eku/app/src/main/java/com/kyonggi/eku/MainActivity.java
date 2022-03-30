package com.kyonggi.eku;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Collections;
import com.minew.beacon.BeaconValueIndex;
import com.minew.beacon.BluetoothState;
import com.minew.beacon.MinewBeacon;
import com.minew.beacon.MinewBeaconManager;
import com.minew.beacon.MinewBeaconManagerListener;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ACCESS_FINE_LOCATION = 1000;
    private MinewBeaconManager mMinewBeaconManager;
    private RecyclerView mRecycle;
    private BeaconListAdapter mAdapter;
    private static final int REQUEST_ENABLE_BT = 2;
    private boolean isScanning;

    UserRssi comp = new UserRssi();
    private TextView mStart_scan;
    private boolean mIsRefreshing;
    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        //툴바 생성하고 리사이클 바 연결
        initManager();
        //매니저 초기화
        checkBluetooth();
        //블루투스 되는지 확인
        checkLocationPermition();
        //권한체크
        initListener();
        //리슨

    }

    private void checkLocationPermition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

            if(permissionCheck == PackageManager.PERMISSION_DENIED){

                // 권한 없음
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_ACCESS_FINE_LOCATION);


            } else{

                // ACCESS_FINE_LOCATION 에 대한 권한이 이미 있음.

            }


        }

// OS가 Marshmallow 이전일 경우 권한체크를 하지 않는다.
        else{

        }
    }

    /**
     * 초기 블루투스 스캔을 체크함
     * 꺼졌는지 켜졋는지 관리
     * 지원안되면 메시지 줌
     */
    private void checkBluetooth() {
        BluetoothState bluetoothState = mMinewBeaconManager.checkBluetoothState();
        switch (bluetoothState) {
            case BluetoothStateNotSupported:
                Toast.makeText(this, "Not Support BLE", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BluetoothStatePowerOff:
                showBLEDialog();
                break;
            case BluetoothStatePowerOn:
                break;
        }
    }


    /**
     * 뷰를 초기화 하는 메소드
     * 툴바(맨위에 있는 바를
     */
    private void initView() {
        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //툴바 작동
        //setSupportActionBar(toolbar);
        //mstart_scan은 textView임..
        mStart_scan = (TextView) findViewById(R.id.start_scan);
        //리사이클 뷰 생성 리사이클 뷰는 수평으로 나오게 만들어서 데이터를 준내 표시하는 놈임
        mRecycle = (RecyclerView) findViewById(R.id.recyeler);
        // 리사이클 뷰에 레이아웃 삽입
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //그냥 레이아웃삽입임
        mRecycle.setLayoutManager(layoutManager);
        //비콘 리스트의 어댑터 생성
        mAdapter = new BeaconListAdapter();
        //리사이클바에 어댑터 연결
        mRecycle.setAdapter(mAdapter);
        mRecycle.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager
                .HORIZONTAL));
    }

    //매니저 초기화
    private void initManager() {
        mMinewBeaconManager = MinewBeaconManager.getInstance(this);
    }


    private void initListener() {
        mStart_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mMinewBeaconManager != null) {
                    //블루투스가 있는지 확인
                    BluetoothState bluetoothState = mMinewBeaconManager.checkBluetoothState();
                    switch (bluetoothState) {
                        case BluetoothStateNotSupported:
                            Toast.makeText(MainActivity.this, "Not Support BLE", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case BluetoothStatePowerOff:
                            showBLEDialog();
                            return;
                        case BluetoothStatePowerOn:
                            break;
                    }
                }//스캔 발견하면 스캔시작되었다고 말해줌
                if (isScanning) {
                    isScanning = false;
                    mStart_scan.setText("Start");
                    if (mMinewBeaconManager != null) {
                        mMinewBeaconManager.stopScan();
                    }
                } else {
                    //아니었으면 멈춰싿고 함
                    isScanning = true;
                    mStart_scan.setText("Stop");
                    try {
                        mMinewBeaconManager.startScan();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //리사이클 뷰 값을 바꿔놓는다.
        mRecycle.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                state = newState;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        mMinewBeaconManager.setDeviceManagerDelegateListener(new MinewBeaconManagerListener() {
            /**
             *   비콘 새로 등판시 하는일.
             *
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

                Toast.makeText(getApplicationContext(),  "비콘디졌수", Toast.LENGTH_SHORT).show();

            }

            /**
             *  the manager calls back this method every 1 seconds, you can get all scanned beacons.
             *  1초마다 콜백을 하는데 모든 비콘을 스캔할 수 있다.
             *  @param minewBeacons all scanned beacons
             */
            @Override
            public void onRangeBeacons(final List<MinewBeacon> minewBeacons) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Collections.sort(minewBeacons, comp);
                        Log.e("tag", state + "");
                        if (state == 1 || state == 2) {
                        } else {
                            mAdapter.setItems(minewBeacons);
                        }

                    }
                });
                for (MinewBeacon minewBeacon : minewBeacons) {
                    String deviceName = minewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_RSSI).getStringValue();
                    Toast.makeText(getApplicationContext(), deviceName + "만큼 떨어짐", Toast.LENGTH_SHORT).show();
                }
            }

            /**
             *  the manager calls back this method when BluetoothStateChanged.
             *  블루투스 상태가 달라졌을때 표시하는 비콘
             *  @param state BluetoothState
             */
            @Override
            public void onUpdateState(BluetoothState state) {
                switch (state) {
                    case BluetoothStatePowerOn:
                        Toast.makeText(getApplicationContext(), "BluetoothStatePowerOn", Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothStatePowerOff:
                        Toast.makeText(getApplicationContext(), "BluetoothStatePowerOff", Toast.LENGTH_SHORT).show();
                        break;
                }
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


    /**
     * ㅁㄹ?
     * **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                break;
        }
    }
}
