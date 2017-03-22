package com.example.soheiln.popularmovies.data;

import android.os.Bundle;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    public static String FAVORITE_KEY = "FAVORITE";

    public int id = 0;
    public String title = "";
    public String rating = "";
    public String release_date = "";
    public String plot = "";
    public String image_url = "";
    public List<String> video_urls = null;
    public List<String> reviews = null;
    public boolean favorite = false;

    Movie() {}

    public Movie(int id, String title, String rating, String release_date, String plot, String image_URL) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.release_date = release_date;
        this.plot = plot;
        this.image_url = image_URL;
    }

    Movie(int id, String title, String rating, String release_date, String plot, String image_URL, List<String> video_urls, List<String> reviews) {
        this(id, title, rating, release_date, plot, image_URL);
        this.video_urls = video_urls;
        this.reviews = reviews;
    }

    public static final class MovieColumns implements BaseColumns {

        // This class cannot be instantiated
        private MovieColumns() {}

        // Database Column Names
        public static final String ID = "movie_id";
        public static final String TITLE = "title";
        public static final String RATING = "rating";
        public static final String RELEASE_DATE = "release_date";
        public static final String PLOT = "plot";
        public static final String IMAGE_URL = "image_url";
        public static final String REVIEW = "review";
        public static final String VIDEO_URL = "video_url";
    }

    public Bundle getBundle() {
        Bundle b = new Bundle();
        b.putInt(MovieContract.MovieEntry.COL_ID, id);
        b.putString(MovieContract.MovieEntry.COL_TITLE, title);
        b.putString(MovieContract.MovieEntry.COL_RATING, rating);
        b.putString(MovieContract.MovieEntry.COL_RELEASE_DATE, release_date);
        b.putString(MovieContract.MovieEntry.COL_RELEASE_DATE, plot);
        b.putString(MovieContract.MovieEntry.COL_IMAGE_URL, image_url);
        b.putStringArrayList(MovieContract.ReviewEntry.COL_REVIEW, (ArrayList<String>) reviews);
        b.putStringArrayList(MovieContract.VideoEntry.COL_VIDEO_URL, (ArrayList<String>) video_urls);
        b.putBoolean(Movie.FAVORITE_KEY, favorite);
        return b;
    }

    public static Movie extractMovieFromBundle(Bundle bundle) {
        Movie movie = new Movie();
        movie.id = bundle.getInt(MovieContract.MovieEntry.COL_ID);
        movie.title = bundle.getString(MovieContract.MovieEntry.COL_TITLE);
        movie.rating = bundle.getString(MovieContract.MovieEntry.COL_RATING);
        movie.release_date = bundle.getString(MovieContract.MovieEntry.COL_RELEASE_DATE);
        movie.plot = bundle.getString(MovieContract.MovieEntry.COL_PLOT);
        movie.image_url = bundle.getString(MovieContract.MovieEntry.COL_IMAGE_URL);
        movie.reviews = bundle.getStringArrayList(MovieContract.ReviewEntry.COL_REVIEW);
        movie.video_urls = bundle.getStringArrayList(MovieContract.VideoEntry.COL_VIDEO_URL);
        movie.favorite = bundle.getBoolean(Movie.FAVORITE_KEY);
        return movie;
    }

}
