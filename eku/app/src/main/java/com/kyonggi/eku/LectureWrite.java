package com.kyonggi.eku;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class LectureWrite extends AppCompatActivity {

    //작성 화면
    ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_write);
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        Button saveButton = (Button) findViewById(R.id.lecture_write_SaveButton);
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LecureMain.class);
                int count = PreferenceManagers.getInt(getApplicationContext(), "count");
                // Toast.makeText(getApplicationContext(),String.valueOf(count), Toast.LENGTH_SHORT).show();
                count++;
                PreferenceManagers.setInt(getApplicationContext(), "count", count);
                PreferenceManagers.setInt(getApplicationContext(), "name0", count);
                PreferenceManagers.setInt(getApplicationContext(), "professor0", count);
                PreferenceManagers.setInt(getApplicationContext(), "content0", count);
                PreferenceManagers.setInt(getApplicationContext(), "writer0", count);
                PreferenceManagers.setInt(getApplicationContext(), "rating0", count);
                PreferenceManagers.setInt(getApplicationContext(), "score0", count);

                String name = "name"+count;
                EditText text = findViewById(R.id.lecture_write_NameText);
                String nametext = text.getText().toString();
                PreferenceManagers.setString(getApplicationContext(), name, nametext);

                String professor = "professor"+count;
                text = findViewById(R.id.lecture_write_ProfessorText);
                String professortext = text.getText().toString();
                PreferenceManagers.setString(getApplicationContext(), professor, professortext);

                String content = "content"+count;
                text = findViewById(R.id.lecture_write_ContentText);
                String contenttext = text.getText().toString();
                PreferenceManagers.setString(getApplicationContext(), content, contenttext);

                String writer = "writer"+count;
                String writertext = "컴공16";
                PreferenceManagers.setString(getApplicationContext(), writer, writertext);

                String rating = "rating"+count;
                RatingBar ratingStar = findViewById(R.id.lecture_write_ratingBar);
                float ratingscore = ratingStar.getRating();
                PreferenceManagers.setFloat(getApplicationContext(), rating, ratingscore);

                String score = "score"+count;
                RadioGroup scoreGroup = findViewById( R.id.lecture_write_radioGroup );
                RadioButton scoreId = findViewById(scoreGroup.getCheckedRadioButtonId());
                String scoreText = scoreId.getText().toString();
                PreferenceManagers.setString(getApplicationContext(), score, scoreText);

                activityResultLauncher.launch(intent);
                finish();

            }
        });
        Button closeButton = (Button) findViewById(R.id.lecture_write_CloseButton);
        closeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LecureMain.class);
                startActivity(intent);
                finish();
            }
        });


    }
}