package com.kyonggi.eku.presenter.lecture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.kyonggi.eku.view.signIn.ActivitySignIn;

public class LecturePresenter {
    private final Context context;
    private final Activity activity;

    public LecturePresenter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void signIn() {
        Intent signInIntent = new Intent(context, ActivitySignIn.class);
        activity.startActivity(signInIntent);
    }
}
