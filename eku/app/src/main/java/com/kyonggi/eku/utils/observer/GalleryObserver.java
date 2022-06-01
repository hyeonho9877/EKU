package com.kyonggi.eku.utils.observer;

import static androidx.activity.result.contract.ActivityResultContracts.GetContent;
import static com.kyonggi.eku.utils.SendTool.CONNECTION_FAILED;
import static com.kyonggi.eku.utils.SendTool.HTTP_BAD_REQUEST;
import static com.kyonggi.eku.utils.SendTool.HTTP_INTERNAL_SERVER_ERROR;
import static com.kyonggi.eku.utils.SendTool.HTTP_OK;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.kyonggi.eku.model.OCRForm;
import com.kyonggi.eku.utils.SendTool;
import com.kyonggi.eku.view.signUp.activity.ActivityInputSignUpInfo;

public class GalleryObserver implements DefaultLifecycleObserver {
    private static final String TAG = "GalleryObserver";
    private final ContentResolver contentResolver;
    private final ActivityResultRegistry registry;
    private final Handler handler;
    private final ProgressDialog dialog;
    private ActivityResultLauncher<String> getContent;

    public GalleryObserver(ActivityResultRegistry registry, ContentResolver contentResolver, Context context, Activity activity) {
        this.registry = registry;
        this.contentResolver = contentResolver;
        this.dialog = new ProgressDialog(context);
        this.handler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(@NonNull Message msg) {
                int code = msg.what;
                String response = (String) msg.obj;
                switch (code) {
                    case CONNECTION_FAILED | HTTP_BAD_REQUEST | HTTP_INTERNAL_SERVER_ERROR:
                        Toast.makeText(context, "네트워크 연결에 실패하였습니다.", Toast.LENGTH_LONG).show();
                        break;
                    case HTTP_OK:
                        dialog.dismiss();
                        OCRForm OCRForm = SendTool.parseToSingleEntity(response, OCRForm.class);
                        Intent intent = new Intent(context, ActivityInputSignUpInfo.class);
                        intent.putExtra("studentInfo", OCRForm);
                        context.startActivity(intent);
                        activity.finish();
                        break;
                }
            }
        };
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        getContent = registry.register("key", owner, new GetContent(),
                result -> {
                    if (result != null) {
                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        dialog.setMessage("사진을 분석중입니다..");
                        dialog.show();
                        SendTool.requestForMultiPart("/signUp/ocr", result, contentResolver, handler);
                    }
                });
    }

    public void selectImage() {
        getContent.launch("image/*");
    }
}
