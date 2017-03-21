package com.example.soheiln.popularmovies;

import android.os.Bundle;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    public static String ID_KEY = "ID_KEY";
    public static String TITLE_KEY = "TITLE_KEY";
    public static String RATING_KEY = "RATING_KEY";
    public static String RELEASE_DATE_KEY = "RELEASE_DATE_KEY";
    public static String PLOT_KEY = "PLOT_KEY";
    public static String IMAGE_URL_KEY = "IMAGE_URL_KEY";
    public static String REVIEWS_KEY = "REVIEWS_KEY";
    public static String VIDEO_URLS_KEY = "VIDEO_URLS_KEY";
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

    Movie(int id, String title, String rating, String release_date, String plot, String image_URL) {
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
        b.putInt(Movie.ID_KEY, id);
        b.putString(Movie.TITLE_KEY, title);
        b.putString(Movie.RATING_KEY, rating);
        b.putString(Movie.RELEASE_DATE_KEY, release_date);
        b.putString(Movie.PLOT_KEY, plot);
        b.putString(Movie.IMAGE_URL_KEY, image_url);
        b.putStringArrayList(Movie.REVIEWS_KEY, (ArrayList<String>) reviews);
        b.putStringArrayList(Movie.VIDEO_URLS_KEY, (ArrayList<String>) video_urls);
        b.putBoolean(Movie.FAVORITE_KEY, favorite);
        return b;
    }

    public static Movie extractMovieFromBundle(Bundle bundle) {
        Movie movie = new Movie();
        movie.id = bundle.getInt(Movie.ID_KEY);
        movie.title = bundle.getString(Movie.TITLE_KEY);
        movie.rating = bundle.getString(Movie.RATING_KEY);
        movie.release_date = bundle.getString(Movie.RELEASE_DATE_KEY);
        movie.plot = bundle.getString(Movie.PLOT_KEY);
        movie.image_url = bundle.getString(Movie.IMAGE_URL_KEY);
        movie.reviews = bundle.getStringArrayList(Movie.REVIEWS_KEY);
        movie.video_urls = bundle.getStringArrayList(Movie.VIDEO_URLS_KEY);
        movie.favorite = bundle.getBoolean(Movie.FAVORITE_KEY);
        return movie;
    }

}
