package com.example.soheiln.popularmovies;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private Menu mMenu;
    private MovieDBHelper mMovieDBHelper;
    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Set up private variables
        mMovieDBHelper = MovieDBHelper.getDBHelper(this);

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
                mMovie = Movie.extractMovieFromBundle(extras);

                // Update UI elements with movie data
                tv_title.setText(mMovie.title);
                tv_rating.setText("Rating: " + mMovie.rating + " / 10");
                tv_release_date.setText("Released: " + mMovie.release_date);
                tv_plot.setText(mMovie.plot);
                Picasso.with(this).setLoggingEnabled(true);
                Picasso.with(this).load(mMovie.image_url).into(iv_image);

                // Add video links to the view
                if (mMovie.video_urls.size() == 0) {
                    ll_videos.setVisibility(View.INVISIBLE);
                } else {
                    for (int i = 0; i < mMovie.video_urls.size(); i++) {
                        TextView tv_video = new TextView(this);
                        final String video_url = mMovie.video_urls.get(i);
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
                if (mMovie.reviews.size() == 0) {
                    ll_reviews.setVisibility(View.INVISIBLE);
                } else {
                    for (String review: mMovie.reviews) {
                        TextView tv_review = new TextView(this);
                        tv_review.setText("- \"" + review + "\"\n");
                        tv_review.setTypeface(null, Typeface.ITALIC);
                        ll_reviews.addView(tv_review);
                    }
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_details, menu);

        // Adjust the visibility of favorite and unfavorite action icons
        menu.findItem(R.id.action_favorite).setVisible(!mMovie.favorite);
        menu.findItem(R.id.action_unfavorite).setVisible(mMovie.favorite);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                new AddToFavoritesDBTask(this).execute(mMovie);
                return true;
            case R.id.action_unfavorite:
                new RemoveFromFavoritesDBTask(this).execute(mMovie);
                return true;
            // When the home button is pressed, take the user back to the VisualizerActivity
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return true;
    }


    // called by async task after adding/removing movie to/from favorites db
    public void movieFavorited(boolean favorite) {
        mMovie.favorite = favorite;
        invalidateOptionsMenu();
    }


}
