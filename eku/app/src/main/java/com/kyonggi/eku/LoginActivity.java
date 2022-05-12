package com.kyonggi.eku;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.kyonggi.eku.databinding.ActivityLoginBinding;
import com.kyonggi.eku.presenter.LoginPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    /*
       로그인 한값을
    * 넘겨주는 코드
    *
     */

    private ActivityLoginBinding binding;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenter(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initListener();
    }

    private void initListener() {
        binding.signIn.setOnClickListener(v -> {
            String studNo = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            if (studNo.length() == 0 || password.length() == 0) {
                Toast.makeText(getBaseContext(), "ID와 PASSWORD 입력을 확인해 주세요", Toast.LENGTH_SHORT).show();
            } else {
                presenter.signIn(studNo, password);
            }
        });

        // 비밀번호 8자 이상시 활성화되는 버튼 이벤트 지금 6자
        binding.password.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //나중에 수정필요
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.password.getText().length() >= 6) {
                    binding.signIn.setTextColor(Color.WHITE);
                    binding.signIn.setBackgroundResource(R.drawable.rounded);
                } else {
                    binding.signIn.setTextColor(Color.BLACK);
                    binding.signIn.setBackgroundColor(Color.TRANSPARENT);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                /*
                if (editable.length() >= 8) {
                    btn_login.setClickable(true);
                    btn_login.setBackgroundColor(Color.BLUE);
                    //btn_login.setTextColor(Color.WHITE);
                } else {
                    btn_login.setClickable(false);
                    btn_login.setBackgroundColor(Color.GRAY);
                    //btn_login.setTextColor(Color.BLACK);
                }
                 */
            }
        });
        //password1!

        binding.signUp.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), PhotoServeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}