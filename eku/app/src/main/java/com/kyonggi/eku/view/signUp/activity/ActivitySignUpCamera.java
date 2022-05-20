package com.kyonggi.eku.view.signUp.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.kyonggi.eku.databinding.ActivitySignupPhotoBinding;
import com.kyonggi.eku.presenter.signUp.SignUpCameraPresenter;
import com.kyonggi.eku.utils.observer.GalleryObserver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivitySignUpCamera extends AppCompatActivity {

    private final String TAG = "SignUpActivity";
    private ActivitySignupPhotoBinding binding;
    private SignUpCameraPresenter presenter;
    private ExecutorService cameraExecutor;
    private GalleryObserver observer;


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupPhotoBinding.inflate(getLayoutInflater()); // 뷰 바인딩
        View view = binding.getRoot();
        setContentView(view); // 할당
        presenter = new SignUpCameraPresenter(this, this);

        observer = new GalleryObserver(getActivityResultRegistry(), getContentResolver(), presenter.getHandler());
        getLifecycle().addObserver(observer);
        presenter.startCamera(binding);

        initListeners();
        cameraExecutor = Executors.newSingleThreadExecutor();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }


    private void initListeners() {
        binding.buttonImageCapture.setOnClickListener(v -> presenter.takePhoto());
        binding.buttonGallery.setOnClickListener(v -> observer.selectImage());
        binding.buttonSkip.setOnClickListener(v -> presenter.skipPhoto());
    }
}
