package com.example.soheiln.popularmovies;

import android.app.Activity;
import android.os.AsyncTask;


public class AddToFavoritesDBTask extends AsyncTask<Movie, Void, Void> {

    public MovieDetailsActivity mMovieDetailsActivity;

    AddToFavoritesDBTask(MovieDetailsActivity movieDetailsActivity) {
        this.mMovieDetailsActivity = movieDetailsActivity;
    }

    @Override
    protected Void doInBackground(Movie... movies) {
        Movie movie = movies[0];
        MovieDBHelper dbHelper = MovieDBHelper.getDBHelper(mMovieDetailsActivity);
        dbHelper.addMovie(movie);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mMovieDetailsActivity.movieFavorited(true);
    }
}
