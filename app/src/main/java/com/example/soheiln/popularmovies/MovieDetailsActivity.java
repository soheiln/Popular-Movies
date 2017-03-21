package com.example.soheiln.popularmovies;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDetailsActivity extends AppCompatActivity {

    MovieDetailsActivity self = this;

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
        LinearLayout ll_reviews = (LinearLayout) findViewById(R.id.movie_reviews);
        LinearLayout ll_videos = (LinearLayout) findViewById(R.id.movie_videos);

        // Read movie data from intent
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if ( extras != null ) {
                String title = intent.getStringExtra(Movie.TITLE_KEY);
                String rating = intent.getStringExtra(Movie.RATING_KEY);
                String release_date = intent.getStringExtra(Movie.RELEASE_DATE_KEY);
                String plot = intent.getStringExtra(Movie.PLOT_KEY);
                String image_url = intent.getStringExtra(Movie.IMAGE_URL_KEY);
                ArrayList<String> reviews = intent.getStringArrayListExtra(Movie.REVIEWS_KEY);
                ArrayList<String> video_urls = intent.getStringArrayListExtra(Movie.VIDEO_URLS_KEY);

                // Update UI elements with movie data
                tv_title.setText(title);
                tv_rating.setText("Rating: " + rating + " / 10");
                tv_release_date.setText("Released: " + release_date);
                tv_plot.setText(plot);
                Picasso.with(this).setLoggingEnabled(true);
                Picasso.with(this).load(image_url).into(iv_image);

                // Add video links to the view
                if (video_urls.size() == 0) {
                    ll_videos.setVisibility(View.INVISIBLE);
                } else {
                    for (int i = 0; i < video_urls.size(); i++) {
                        TextView tv_video = new TextView(this);
                        final String video_url = video_urls.get(i);
                        tv_video.setText("Trailer " + (i+1) + "\n");
                        tv_video.setTypeface(null, Typeface.ITALIC);
                        tv_video.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(video_url));
                                startActivity(browserIntent);
                            }
                        });
                        ll_videos.addView(tv_video);
                    }
                }


                // Add reviews to the view
                if (reviews.size() == 0) {
                    ll_reviews.setVisibility(View.INVISIBLE);
                } else {
                    for (String review: reviews) {
                        TextView tv_review = new TextView(this);
                        tv_review.setText("- \"" + review + "\"\n");
                        tv_review.setTypeface(null, Typeface.ITALIC);
                        ll_reviews.addView(tv_review);
                    }
                }


            }
        }
    }
}
