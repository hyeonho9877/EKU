package com.kyonggi.eku.presenter.signUp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.kyonggi.eku.SendTool;
import com.kyonggi.eku.model.SignUpForm;
import com.kyonggi.eku.view.signUp.ActivityInputSignUpInfo;

public class GalleryPresenter {
    private static final String TAG = "GalleryPresenter";
    private final Context context;
    private final Activity activity;
    private Handler handler;

    public GalleryPresenter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void confirm(Uri uri, ContentResolver contentResolver) {
        SendTool.requestForMultiPart("/signUp/ocr", uri, contentResolver, getHandler());
    }

    private Handler getHandler() {
        if (handler == null) {
            this.handler = new Handler(Looper.getMainLooper()) {
                public void handleMessage(@NonNull Message msg) {
                    int code = msg.what;
                    String response = (String) msg.obj;
                    Log.d(TAG, "handleMessage: " + code);
                    switch (code) {
                        case SendTool.CONNECTION_FAILED:
                            Toast.makeText(context, "connection failed", Toast.LENGTH_LONG).show();
                            break;
                        case SendTool.HTTP_OK:
                            SignUpForm signUpForm = SendTool.parseToSingleEntity(response, SignUpForm.class);
                            Intent intent = new Intent(context, ActivityInputSignUpInfo.class);
                            intent.putExtra("studentInfo", signUpForm);
                            activity.startActivity(intent);
                            break;
                        case SendTool.HTTP_BAD_REQUEST:
                            Toast.makeText(context, "bad request", Toast.LENGTH_LONG).show();
                            break;
                        case SendTool.HTTP_INTERNAL_SERVER_ERROR:
                            Toast.makeText(context, "server error", Toast.LENGTH_LONG).show();
                        default:
                            Log.e(TAG, "handleMessage: Unknown Error");
                            break;
                    }
                }
            };
        }
        return handler;
    }
}
