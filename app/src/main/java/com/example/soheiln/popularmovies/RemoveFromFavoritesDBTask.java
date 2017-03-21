package com.example.soheiln.popularmovies;

import android.os.AsyncTask;


public class RemoveFromFavoritesDBTask extends AsyncTask<Movie, Void, Void> {
    public MovieDetailsActivity mMovieDetailsActivity;

    RemoveFromFavoritesDBTask(MovieDetailsActivity movieDetailsActivity) {
        this.mMovieDetailsActivity = movieDetailsActivity;
    }

    @Override
    protected Void doInBackground(Movie... movies) {
        Movie movie = movies[0];
        MovieDBHelper dbHelper = MovieDBHelper.getDBHelper(mMovieDetailsActivity);
        dbHelper.deleteMovie(movie);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mMovieDetailsActivity.movieFavorited(false);
    }

}
