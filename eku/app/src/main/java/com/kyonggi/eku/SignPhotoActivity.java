package com.kyonggi.eku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class SignPhotoActivity extends AppCompatActivity {

    Button nextBtn;
    Button cancleBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_photo);

        nextBtn = (Button) findViewById(R.id.btn_info_sign);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignInfoActivity.class);
                startActivity(intent);
            }
        });
        cancleBtn = (Button) findViewById(R.id.btn_cancle);
    }
}

