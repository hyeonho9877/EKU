package com.kyonggi.eku.presenter.signUp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;
import com.kyonggi.eku.R;
import com.kyonggi.eku.databinding.ActivitySignupPhotoBinding;
import com.kyonggi.eku.view.signUp.activity.ActivityGallery;
import com.kyonggi.eku.view.signUp.activity.ActivityInputSignUpInfo;
import com.kyonggi.eku.view.signUp.activity.ActivitySignUpCamera;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class SignUpCameraPresenter {
    private static final String TAG = "SignUpPresenter";
    private final Context context;
    private ImageCapture imageCapture;
    private final ActivitySignUpCamera activity;
    private Handler handler;
    private MediaPlayer mediaPlayer;


    public SignUpCameraPresenter(Context context, ActivitySignUpCamera activity) {
        this.context = context;
        this.activity = activity;
    }

    public void startCamera(ActivitySignupPhotoBinding binding) {
        PreviewView previewView = binding.viewFinder;
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(context);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder().build();

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                preview.setSurfaceProvider(previewView.getSurfaceProvider());
                imageCapture = new ImageCapture.Builder().build();
                cameraProvider.unbindAll();
                Camera camera = cameraProvider.bindToLifecycle(activity,
                        cameraSelector,
                        preview,
                        imageCapture);
            } catch (InterruptedException | ExecutionException exception) {
                Log.e(TAG, "startCamera: Use case binding failed", exception);
            }
        }, ContextCompat.getMainExecutor(context));

    }

    public void takePhoto() {
        String name = new SimpleDateFormat(FILENAME_FORMAT, Locale.KOREA).format(System.currentTimeMillis());
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image");
        }
        ContentResolver contentResolver = context.getContentResolver();
        ImageCapture.OutputFileOptions fileOptions = new ImageCapture.OutputFileOptions.Builder(contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues).build();

        Resources res = context.getResources();
        int resId = R.raw.camera_shutter;
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId));

        mediaPlayer = MediaPlayer.create(context, uri);
        mediaPlayer.setOnCompletionListener(mp -> stopSound());
        mediaPlayer.start();

        imageCapture.takePicture(
                fileOptions,
                ContextCompat.getMainExecutor(context),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        String msg = outputFileResults.getSavedUri() + "에 사진이 저장되었습니다.";
                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, ActivityGallery.class);
                        intent.putExtra("photo", outputFileResults.getSavedUri());
                        activity.startActivity(intent);
                        Log.d(TAG, "onImageSaved: " + msg);
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Log.e(TAG, "onError: Photo capture failed" + exception.getMessage(), exception);
                    }
                }
        );
    }

    public void skipPhoto(){
        Intent intent = new Intent(context, ActivityInputSignUpInfo.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private final int REQUEST_CODE = 0;
    private final String FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";
}
