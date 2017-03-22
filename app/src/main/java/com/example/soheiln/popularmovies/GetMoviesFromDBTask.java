package com.example.soheiln.popularmovies;

import android.os.AsyncTask;

import com.example.soheiln.popularmovies.data.Movie;
import com.example.soheiln.popularmovies.data.DBOpenHelper;
import com.example.soheiln.popularmovies.data.MovieDBHelper;

import java.util.List;


public class GetMoviesFromDBTask extends AsyncTask <Void, Void, List<Movie>>{

    public MainActivity mMainActivity;

    GetMoviesFromDBTask(MainActivity mainActivity) {
        this.mMainActivity = mainActivity;
    }

    @Override
    protected List<Movie> doInBackground(Void... voids) {
        return MovieDBHelper.getInstance(mMainActivity.getApplicationContext()).getMovies();
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        mMainActivity.mMovies = movies;
        mMainActivity.mAdapter.setGridData(movies);
    }
}
