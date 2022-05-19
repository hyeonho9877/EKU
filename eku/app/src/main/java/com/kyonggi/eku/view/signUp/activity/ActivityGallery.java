package com.kyonggi.eku.view.signUp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kyonggi.eku.databinding.ActivityGalleryBinding;
import com.kyonggi.eku.presenter.signUp.GalleryPresenter;

public class ActivityGallery extends AppCompatActivity {

    private static final String TAG = "ActivityGallery";
    private ActivityGalleryBinding binding;
    private GalleryPresenter presenter;
    private Uri photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGalleryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        presenter = new GalleryPresenter(this, this);

        replacePhoto();
        initListeners();
    }

    private void initListeners() {
        binding.buttonConfirm.setOnClickListener(v-> presenter.confirm(photo, getContentResolver()));
        binding.buttonCancel.setOnClickListener(v->{
            finish();
        });
    }

    private void replacePhoto() {
        photo = (Uri) getIntent().getParcelableExtra("photo");
        if (photo == null) {
            Toast.makeText(this, "에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
            finish();
        }
        binding.imageView.setImageURI(photo);
    }
}
