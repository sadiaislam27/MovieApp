package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieapp.model.CategoryItem;

public class MovieDetails extends AppCompatActivity {
    ImageView movieImage;
    TextView movieName;
    Button playButton;

    TextView titleDetail,desDetail,releaseDate;
    RatingBar ratingDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        titleDetail = findViewById(R.id.movie_title);
        desDetail=findViewById(R.id.movie_desc);
        ratingDetail=findViewById(R.id.ratingBar);
        releaseDate=findViewById(R.id.release);

        movieImage=findViewById(R.id.movie_image);
        movieName=findViewById(R.id.movie_name);
        playButton=findViewById(R.id.play_button);

        CategoryItem item = (CategoryItem) getIntent().getSerializableExtra("movie");
        if(item != null){
            Glide.with(this).load(item.getImageUrl()).into(movieImage);
            movieName.setText(item.getMovieName());
            titleDetail.setText(item.getMovieName());
            desDetail.setText(item.getDetails());
            releaseDate.setText(item.getReleaseDate());
            ratingDetail.setRating(item.getActualRating());
        }
        else{
            Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show();
        }


    }
}