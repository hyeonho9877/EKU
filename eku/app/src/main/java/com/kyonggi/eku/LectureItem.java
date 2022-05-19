package com.kyonggi.eku;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class LectureItem extends LinearLayout {

    public LectureItem(Context context, String Title, String professor, String rating) {
        super(context);
        init(context, Title, professor, rating);
    }

    public LectureItem(Context context, String Title, String professor, String rating, String writer) {
        super(context);
        init(context, Title, professor, rating, writer);
    }

    public LectureItem(Context context, String content, String grade, float star, String writer) {
        super(context);
        init(context, content, grade, star, writer);
    }



    private void init(Context context, String Title, String professor, String rating, String writer){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_lecture,this,true);
        TextView textView = findViewById(R.id.textView22);
        textView.setText(Title);
        textView = findViewById(R.id.textView24);
        textView.setText(professor);
        textView = findViewById(R.id.textView23);
        textView.setText(writer);
        RatingBar Rating = findViewById(R.id.ratingBar22);
        Rating.setRating(Float.valueOf(rating));
    }

    private void init(Context context, String Title, String professor, String rating){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_lecture,this,true);
        TextView textView = findViewById(R.id.textView27);
        textView.setText(Title);
        textView = findViewById(R.id.textView28);
        textView.setText(professor);
        RatingBar Rating = findViewById(R.id.ratingBar2);
        Rating.setRating(Float.valueOf(rating));
    }

    private void init(Context context, String content, String grade, float star, String writer){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_lecturedetail,this,true);
        TextView textView = findViewById(R.id.textView29);
        textView.setText(content);
        textView = findViewById(R.id.textView30);
        String Gradetext = grade.replace("P", "+");
        textView.setText(Gradetext);
        textView = findViewById(R.id.textView31);
        textView.setText(writer);
        RatingBar Rating = findViewById(R.id.ratingBar32);
        Rating.setRating(Float.valueOf(star));
    }
}
