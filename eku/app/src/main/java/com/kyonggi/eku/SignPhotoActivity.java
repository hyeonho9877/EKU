package com.kyonggi.eku;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import java.io.File; import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SignPhotoActivity extends AppCompatActivity {

        final String TAG = getClass().getSimpleName();
        ImageView imageView;
        Button cameraBtn;
        Button cancleBtn;
        final static int TAKE_PICTURE = 1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_photo);

            // 레이아웃과 변수 연결
            imageView = findViewById(R.id.image_photo);
            cameraBtn = findViewById(R.id.btn_camera);
            cancleBtn = findViewById(R.id.btn_cancle);

            // 카메라 버튼에 리스터 추가
            cameraBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, TAKE_PICTURE);
                }
            });

            // 6.0 마쉬멜로우 이상일 경우에는 권한 체크 후 권한 요청
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ) {
                    Log.d(TAG, "권한 설정 완료");
                } else {
                    Log.d(TAG, "권한 설정 요청");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }

            cancleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent cancleIntent = new Intent(getApplicationContext(), SignPhotoActivity.class);
                    finish();
                }
            });
        }

        // 권한 요청
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            Log.d(TAG, "onRequestPermissionsResult");
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
                Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            }
        }

        // 카메라로 촬영한 영상을 가져오는 부분
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
            super.onActivityResult(requestCode, resultCode, intent);

            switch (requestCode) {
                case TAKE_PICTURE:
                    if (resultCode == RESULT_OK && intent.hasExtra("data")) {
                        Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                        }

                    }
                    break;
            }
        }
    }

