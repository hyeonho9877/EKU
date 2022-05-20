package com.kyonggi.eku.view.signIn;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.kyonggi.eku.databinding.ActivitySigninBinding;
import com.kyonggi.eku.presenter.signIn.SignInPresenter;

public class ActivitySignIn extends AppCompatActivity {

    private static final String TAG = "ActivitySignIn";
    private ActivitySigninBinding binding;
    private SignInPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SignInPresenter(this, this); // 프레젠터 할당
        binding = ActivitySigninBinding.inflate(getLayoutInflater()); // 뷰 바인딩
        View view = binding.getRoot();
        setContentView(view); // 할당
        initListener(); // 리스너 초기화
    }

    private void initListener() {

        // 로그인 버튼 리스너
        binding.signIn.setOnClickListener(v -> {
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            if (email.length() == 0 || password.length() == 0) {
                Toast.makeText(getBaseContext(), "이메일과 비밀번호 입력을 확인해 주세요", Toast.LENGTH_SHORT).show();
            } else {
                presenter.signIn(email, password);
            }
        });

        binding.signUp.setOnClickListener(view -> {
            if (allPermissionGranted()) {
                presenter.signUp();
            } else {
                String[] permissions = new String[]{Manifest.permission.CAMERA};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSIONS);
            }
        });
    }

    private boolean allPermissionGranted(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                presenter.startCamera();
            } else {
                // Permission request was denied.
                presenter.skipCamera();
            }
        }
    }

    private final int REQUEST_CODE_PERMISSIONS = 10;
}