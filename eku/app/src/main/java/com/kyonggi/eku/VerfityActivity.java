package com.kyonggi.eku;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kyonggi.eku.view.signIn.ActivitySignIn;

public class VerfityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfity);
        Button Verifybutton = findViewById(R.id.GoogleButton);
        Button OKbutton = findViewById(R.id.OKButton);
        TextView textView = findViewById(R.id.textView18);

        Verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com/mail"));
                startActivity(intent);
            }
        });
        OKbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivitySignIn.class);
                startActivity(intent);
            }
        });

    }
}