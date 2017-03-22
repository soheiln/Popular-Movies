package com.example.soheiln.popularmovies;

import android.os.AsyncTask;

import com.example.soheiln.popularmovies.data.DBOpenHelper;
import com.example.soheiln.popularmovies.data.Movie;
import com.example.soheiln.popularmovies.data.MovieDBHelper;


public class RemoveFromFavoritesDBTask extends AsyncTask<Movie, Void, Void> {
    public MovieDetailsActivity mMovieDetailsActivity;

    RemoveFromFavoritesDBTask(MovieDetailsActivity movieDetailsActivity) {
        this.mMovieDetailsActivity = movieDetailsActivity;
    }

    @Override
    protected Void doInBackground(Movie... movies) {
        Movie movie = movies[0];
        MovieDBHelper.getInstance(mMovieDetailsActivity.getApplicationContext()).deleteMovie(movie);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mMovieDetailsActivity.movieFavorited(false);
    }

}
