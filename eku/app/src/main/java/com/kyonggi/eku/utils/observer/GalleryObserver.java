package com.kyonggi.eku.utils.observer;

import static androidx.activity.result.contract.ActivityResultContracts.GetContent;

import android.content.ContentResolver;
import android.os.Handler;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.kyonggi.eku.SendTool;

public class GalleryObserver implements DefaultLifecycleObserver {
    private static final String TAG = "GalleryObserver";
    private final ContentResolver contentResolver;
    private final ActivityResultRegistry registry;
    private final Handler handler;
    private ActivityResultLauncher<String> getContent;

    public GalleryObserver(ActivityResultRegistry registry, ContentResolver contentResolver, Handler handler) {
        this.registry = registry;
        this.contentResolver = contentResolver;
        this.handler = handler;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        getContent = registry.register("key", owner, new GetContent(),
                result -> {
                    SendTool.requestForMultiPart("/signUp/ocr", result, contentResolver, handler);
                });
    }

    public void selectImage() {
        getContent.launch("image/*");
    }
}
