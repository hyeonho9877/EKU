package com.kyonggi.eku.view.signIn;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kyonggi.eku.databinding.ActivitySigninBinding;
import com.kyonggi.eku.presenter.signIn.SignInPresenter;

public class ActivitySignIn extends AppCompatActivity {

    private ActivitySigninBinding binding;
    private SignInPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SignInPresenter(this); // 프레젠터 할당
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
            presenter.signUp();
        });


        /*binding.password.setOnKeyListener((view, keyCode, keyEvent) -> {
            switch (keyCode) {
                case KeyEvent.KEYCODE_ENTER:
                    binding.signIn.callOnClick();
                    break;
            }
            return true;
        });*/
    }
}