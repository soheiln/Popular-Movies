package com.example.soheiln.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Enable the Up button in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get reference to UI elements
        TextView tv_title = (TextView) findViewById(R.id.movie_title);
        TextView tv_rating = (TextView) findViewById(R.id.movie_rating);
        TextView tv_release_date = (TextView) findViewById(R.id.movie_date);
        TextView tv_plot = (TextView) findViewById(R.id.movie_plot);
        ImageView iv_image = (ImageView) findViewById(R.id.movie_image);

        // Read movie data from intent
        Intent intent = getIntent();
        String title = intent.getStringExtra(Movie.TITLE_KEY);
        String rating = intent.getStringExtra(Movie.RATING_KEY);
        String release_date = intent.getStringExtra(Movie.RELEASE_DATE_KEY);
        String plot = intent.getStringExtra(Movie.PLOT_KEY);
        String image_url = intent.getStringExtra(Movie.IMAGE_URL_KEY);

        // Update UI elements with movie data
        tv_title.setText(title);
        tv_rating.setText("Rating: " + rating + " / 10");
        tv_release_date.setText("Released: " + release_date);
        tv_plot.setText(plot);
        Picasso.with(this).setLoggingEnabled(true);
        Picasso.with(this).load(image_url).into(iv_image);

    }
}
