package com.kyonggi.eku.view.signUp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kyonggi.eku.R;
import com.kyonggi.eku.databinding.ActivityInputSignupInfoBinding;
import com.kyonggi.eku.model.SignUpForm;

public class ActivityInputSignUpInfo extends AppCompatActivity {

    private static final String TAG = "ActivityInputSignUpInfo";
    private ActivityInputSignupInfoBinding binding;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInputSignupInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FragmentSignupInfo fragmentSignupInfo = new FragmentSignupInfo();
        fragmentTransaction.add(R.id.fragment_user_interaction, fragmentSignupInfo);
        fragmentTransaction.commit();


        initListeners();
        SignUpForm studentInfo = (SignUpForm) getIntent().getSerializableExtra("studentInfo");
        Log.d(TAG, "onCreate: "+studentInfo);
    }

    private void initListeners() {

    }
}
