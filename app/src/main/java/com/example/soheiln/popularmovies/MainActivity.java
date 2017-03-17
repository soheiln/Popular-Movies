package com.example.soheiln.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GridView mGridView;
    List<Movie> mMovies = new ArrayList<Movie>();
    MovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);

        mGridView = (GridView) findViewById(R.id.gv_movies);
        mAdapter = new MovieAdapter(this);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = mMovies.get(position);
                Intent movieDetailsActivityIntent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
                movieDetailsActivityIntent.putExtra(Movie.TITLE_KEY, movie.title);
                movieDetailsActivityIntent.putExtra(Movie.RATING_KEY, movie.rating);
                movieDetailsActivityIntent.putExtra(Movie.RELEASE_DATE_KEY, movie.release_date);
                movieDetailsActivityIntent.putExtra(Movie.PLOT_KEY, movie.plot);
                movieDetailsActivityIntent.putExtra(Movie.IMAGE_URL_KEY, movie.image_URL);
                startActivity(movieDetailsActivityIntent);
            }
        });

        new TMDBQueryTask(this).execute(NetworkUtils.MOVIE_ORDER_BY_POPULARITY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();
        if (menuItemSelected == R.id.action_settings) {
            // Setting menu item selected
            Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsActivityIntent);
        }
        return true;
    }
}
