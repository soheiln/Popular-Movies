package com.example.soheiln.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.soheiln.popularmovies.data.DBOpenHelper;
import com.example.soheiln.popularmovies.data.Movie;
import com.example.soheiln.popularmovies.data.MovieDBHelper;

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

        // check if movie is already in favorites DB and if so set its favorite flag
        movie.favorite = MovieDBHelper.getInstance(mMainActivity.getApplicationContext()).containsMovie(movie);
        return movie;
    }

    @Override
    protected void onPostExecute(Movie movie) {
        Intent movieDetailsActivityIntent = new Intent(mMainActivity.getApplicationContext(), MovieDetailsActivity.class);
        Bundle bundle = movie.getBundle();
        movieDetailsActivityIntent.putExtras(bundle);
        mMainActivity.startActivity(movieDetailsActivityIntent);
    }
}
