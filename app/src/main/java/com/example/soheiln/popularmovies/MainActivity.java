package com.example.soheiln.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    GridView mGridView;
    List<Movie> mMovies = new ArrayList<Movie>();
    MovieAdapter mAdapter;
    SharedPreferences mSharedPreferences;
    String mOrder;
    MainActivity self = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);

        // Read movies sort order from preferences
        mOrder = mSharedPreferences.getString(getString(R.string.pref_order_key),
                                              getString(R.string.pref_order_key_popularity));

        mAdapter = new MovieAdapter(this);
        mGridView = (GridView) findViewById(R.id.gv_movies);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = mMovies.get(position);
                new TMDBMovieDetailsTask(self).execute(movie);
            }
        });

        new TMDBMoviesQueryTask(this).execute(mOrder);
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
        if (key.equals(getString(R.string.pref_order_key))) {
            mOrder = mSharedPreferences.getString(key, getString(R.string.pref_order_key_popularity));
            new TMDBMoviesQueryTask(this).execute(mOrder);
        }
    }
}
