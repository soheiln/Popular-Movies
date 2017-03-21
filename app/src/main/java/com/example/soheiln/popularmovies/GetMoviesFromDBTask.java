package com.example.soheiln.popularmovies;

import android.os.AsyncTask;

import java.util.List;


public class GetMoviesFromDBTask extends AsyncTask <Void, Void, List<Movie>>{

    public MainActivity mMainActivity;

    GetMoviesFromDBTask(MainActivity mainActivity) {
        this.mMainActivity = mainActivity;
    }

    @Override
    protected List<Movie> doInBackground(Void... voids) {
        MovieDBHelper dbHelper = MovieDBHelper.getDBHelper(mMainActivity);
        return dbHelper.getMovies();
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        mMainActivity.mMovies = movies;
        mMainActivity.mAdapter.setGridData(movies);
    }
}
