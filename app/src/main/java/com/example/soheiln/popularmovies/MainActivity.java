package com.example.soheiln.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.soheiln.popularmovies.data.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    GridView mGridView;
    List<Movie> mMovies = new ArrayList<Movie>();
    MovieAdapter mAdapter;
    SharedPreferences mSharedPreferences;
    String mShowMoviesMode;
    MainActivity self = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);

        // Read show movies mode from preferences, if not present default to popularity
        mShowMoviesMode = mSharedPreferences.getString(getString(R.string.pref_show_movies_key),
                                              getString(R.string.pref_show_movies_key_popularity));

        mAdapter = new MovieAdapter(this);
        mGridView = (GridView) findViewById(R.id.gv_movies);
        mGridView.setAdapter(mAdapter);

        initActivityForMode(mShowMoviesMode);

    }

    private void initActivityForMode(String mShowMoviesMode) {
        // if show movies mode is polularity or rating
        if (mShowMoviesMode.equals(getString(R.string.pref_show_movies_key_popularity)) ||
                mShowMoviesMode.equals(getString(R.string.pref_show_movies_key_rating))) {

            // Set click listeners
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Movie movie = mMovies.get(position);
                    new TMDBMovieDetailsTask(self).execute(movie);
                }
            });

            // kick off async task to retreive movies from TMDB over internet
            new TMDBMoviesQueryTask(this).execute(mShowMoviesMode);
        }

        // if show movies mode is favorites only
        if (mShowMoviesMode.equals(getString(R.string.pref_show_movies_key_favorites))) {

            // Set click listeners
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Movie movie = mMovies.get(position);
                    Intent movieDetailsActivityIntent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Movie.PARCEL_NAME, movie);
                    movieDetailsActivityIntent.putExtras(bundle);
                    startActivity(movieDetailsActivityIntent);
                }
            });

            // kick off async task to retreive movies from local DB
            new GetMoviesFromDBTask(this).execute();
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        // if show movies mode is favorites, repopulate the grid view data to account for any
        // potential recent adds/removes to favorites list if user is visiting back from
        // MovieDetailsActivity
        if (mShowMoviesMode.equals(getString(R.string.pref_show_movies_key_favorites))) {
            mMovies = new ArrayList<Movie>();
            mAdapter.setGridData(mMovies);
            new GetMoviesFromDBTask(this).execute();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_show_movies_key))) {
            mShowMoviesMode = mSharedPreferences.getString(key, getString(R.string.pref_show_movies_key_popularity));
            initActivityForMode(mShowMoviesMode);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Movie.PARCEL_NAME, (ArrayList<Movie>) mMovies);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMovies = savedInstanceState.getParcelableArrayList(Movie.PARCEL_NAME);
        mAdapter.setGridData(mMovies);
    }
}
