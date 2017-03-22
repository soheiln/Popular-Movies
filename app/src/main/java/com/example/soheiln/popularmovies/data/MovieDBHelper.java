package com.example.soheiln.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MovieDBHelper {

    private static MovieDBHelper sMovieDBHelper = new MovieDBHelper();
    private Context mContext;
    private ContentResolver mContentResolver;

    /*
     * Returns the singleton instance
     */
    public static MovieDBHelper getInstance(Context context) {
        sMovieDBHelper.mContext = context;
        sMovieDBHelper.mContentResolver = context.getContentResolver();
        return sMovieDBHelper;
    }


    /*
     * Returns a full list of Movie objects including populated fields reviews and videos from the database
     */
    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<Movie>();
        Cursor cursor = mContentResolver.query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        // iterate through the movie records
        while(cursor.moveToNext()) {
            Movie movie = new Movie();
            movie.id = cursor.getInt(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COL_ID));
            movie.title = cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COL_TITLE));
            movie.rating = cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COL_RATING));
            movie.release_date = cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COL_RELEASE_DATE));
            movie.plot = cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COL_PLOT));
            movie.image_url = cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COL_IMAGE_URL));

            movie.reviews = getReviewListForMovieId(movie.id);
            movie.video_urls = getVideoListForMovieId(movie.id);
            movie.favorite = true;
            movies.add(movie);
        }
        return movies;
    }


    /*
     * Saves a single Movie object into the database
     */
    public void addMovie(Movie movie) {
        // Return if movie already exists
        if (containsMovie(movie)) {
            return;
        }

        // Create a new map of values where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COL_ID, movie.id);
        values.put(MovieContract.MovieEntry.COL_TITLE, movie.title);
        values.put(MovieContract.MovieEntry.COL_RATING, movie.rating);
        values.put(MovieContract.MovieEntry.COL_RELEASE_DATE, movie.release_date);
        values.put(MovieContract.MovieEntry.COL_PLOT, movie.plot);
        values.put(MovieContract.MovieEntry.COL_IMAGE_URL, movie.image_url);

        // Insert the new row into the movie table
        mContentResolver.insert(MovieContract.MovieEntry.CONTENT_URI, values);

        // Insert movie reviews into the review table
        for (String review: movie.reviews) {
            ContentValues cv = new ContentValues();
            cv.put(MovieContract.MovieEntry.COL_ID, movie.id);
            cv.put(MovieContract.ReviewEntry.COL_REVIEW, review);
            mContentResolver.insert(MovieContract.ReviewEntry.CONTENT_URI, cv);
        }

        // Insert movie video urls into the video table
        for (String video_url: movie.video_urls) {
            ContentValues cv = new ContentValues();
            cv.put(MovieContract.MovieEntry.COL_ID, movie.id);
            cv.put(MovieContract.VideoEntry.COL_VIDEO_URL, video_url);
            mContentResolver.insert(MovieContract.VideoEntry.CONTENT_URI, cv);
        }
    }

    /*
     * Removes a single Movie object from the database
     */
    public void deleteMovie(Movie movie) {
        String selection = MovieContract.MovieEntry.COL_ID + " = ?";
        String[] selectionArgs = { Integer.toString(movie.id) };

        // delete review records
        mContentResolver.delete(MovieContract.ReviewEntry.CONTENT_URI, selection, selectionArgs);

        // delete video records
        mContentResolver.delete(MovieContract.VideoEntry.CONTENT_URI, selection, selectionArgs);

        // delete movie records
        mContentResolver.delete(MovieContract.MovieEntry.CONTENT_URI, selection, selectionArgs);
    }


    /*
     * Returns true if the provided Movie object is already present in the database
     */
    public boolean containsMovie(Movie movie) {
        String selection = MovieContract.MovieEntry.COL_ID + " = ?";
        String[] selectionArgs = { Integer.toString(movie.id) };
        Cursor cursor = mContentResolver.query(MovieContract.MovieEntry.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                null);
        return cursor.getCount() > 0;
    }


    private List<String> getReviewListForMovieId(int movieId) {
        String[] projection = { MovieContract.ReviewEntry.COL_REVIEW };
        String selection = MovieContract.MovieEntry.COL_ID + " = ?";
        String[] selectionArgs = { Integer.toString(movieId) };
        Cursor cursor = mContentResolver.query(MovieContract.ReviewEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
        List<String> reviews = new ArrayList<String>();
        while(cursor.moveToNext()) {
            String review = cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.ReviewEntry.COL_REVIEW));
            reviews.add(review);
        }
        return reviews;
    }


    private List<String> getVideoListForMovieId(int movieId) {
        String[] projection = { MovieContract.VideoEntry.COL_VIDEO_URL };
        String selection = MovieContract.MovieEntry.COL_ID + " = ?";
        String[] selectionArgs = { Integer.toString(movieId) };
        Cursor cursor = mContentResolver.query(MovieContract.VideoEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
        List<String> video_urls = new ArrayList<String>();
        while(cursor.moveToNext()) {
            String video_url = cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.VideoEntry.COL_VIDEO_URL));
            video_urls.add(video_url);
        }
        return video_urls;
    }
}
