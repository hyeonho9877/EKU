package com.kyonggi.eku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kyonggi.eku.view.signIn.ActivitySignIn;

public class AccountActivity extends AppCompatActivity {

    String email="";
    private Context getApplicationContext;
    String password ="";
    boolean postLogin=false;
    boolean verify =false;
    String student_no = "";
    String department ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        UserInformation userInformation = new UserInformation(getApplicationContext());
        if(!userInformation.fromPhoneVerify(getApplicationContext()))
        {

            Intent intent = new Intent(getApplicationContext(), ActivitySignIn.class);
            startActivity(intent);
            finish();
        }
        else{
            Button button = findViewById(R.id.Account_logOut);
            TextView textView = findViewById(R.id.Account_name);
            String temp = userInformation.department+" "+userInformation.student_no;
            textView.setText(temp);
            Button button2= findViewById(R.id.Account_back);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = null;
                    intent = new Intent(getApplicationContext(), MainBoard.class);
                    startActivity(intent);
                    finish();
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userInformation.wasteAll(getApplicationContext());
                    Intent intent = new Intent(getApplicationContext(), MainBoard.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }
}