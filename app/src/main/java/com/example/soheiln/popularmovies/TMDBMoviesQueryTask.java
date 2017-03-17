package com.example.soheiln.popularmovies;

import android.app.Activity;
import android.os.AsyncTask;

import java.util.List;

public class TMDBMoviesQueryTask extends AsyncTask<String, Void, List<Movie>> {

    public MainActivity mMainActivity;

    TMDBMoviesQueryTask(MainActivity mainActivity) {
        this.mMainActivity = mainActivity;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        String order = params[0];
        return NetworkUtils.getMoviesOrderedBy(order);
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        mMainActivity.mMovies = movies;
        mMainActivity.mAdapter.setGridData(movies);
    }
}
