package com.example.soheiln.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;

public class TMDBMovieDetailsTask extends AsyncTask<Movie, Void, Movie> {

    public MainActivity mMainActivity;

    TMDBMovieDetailsTask(MainActivity mainActivity) {
        this.mMainActivity = mainActivity;
    }

    @Override
    protected Movie doInBackground(Movie... movies) {
        Movie movie = movies[0];
        NetworkUtils.loadReviewsForMovie(movie);
        NetworkUtils.loadVideoUrlsForMovie(movie);
        return movie;
    }

    @Override
    protected void onPostExecute(Movie movie) {
        Intent movieDetailsActivityIntent = new Intent(mMainActivity.getApplicationContext(), MovieDetailsActivity.class);
        movieDetailsActivityIntent.putExtra(Movie.TITLE_KEY, movie.title);
        movieDetailsActivityIntent.putExtra(Movie.RATING_KEY, movie.rating);
        movieDetailsActivityIntent.putExtra(Movie.RELEASE_DATE_KEY, movie.release_date);
        movieDetailsActivityIntent.putExtra(Movie.PLOT_KEY, movie.plot);
        movieDetailsActivityIntent.putExtra(Movie.IMAGE_URL_KEY, movie.image_URL);
        mMainActivity.startActivity(movieDetailsActivityIntent);
    }
}
