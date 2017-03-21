package com.example.soheiln.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.soheiln.popularmovies.Movie.MovieColumns;

import java.util.ArrayList;
import java.util.List;


public class MovieDBHelper extends SQLiteOpenHelper {

    // Singleton Instance
    private static MovieDBHelper mOpenHelper = null;

    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;
    private static final String MOVIE_TABLE_NAME = "movie";
    private static final String REVIEW_TABLE_NAME = "review";
    private static final String VIDEO_TABLE_NAME = "video";

    private static final String MOVIE_TABLE_CREATE =
            "CREATE TABLE " + MOVIE_TABLE_NAME + " (" +
                    MovieColumns.ID + " INT PRIMARY KEY, " +
                    MovieColumns.TITLE + " TEXT, " +
                    MovieColumns.RATING + " TEXT, " +
                    MovieColumns.RELEASE_DATE + " TEXT, " +
                    MovieColumns.PLOT + " TEXT, " +
                    MovieColumns.IMAGE_URL + " TEXT);";

    private static final String REVIEW_TABLE_CREATE =
            "CREATE TABLE " + REVIEW_TABLE_NAME + " (" +
                    MovieColumns.ID + " INT, " +
                    MovieColumns.REVIEW + " TEXT, " +
                    "FOREIGN KEY(" + MovieColumns.ID + ") REFERENCES " + MOVIE_TABLE_NAME + "(" + MovieColumns.ID + ")" +
                    ");";

    private static final String VIDEO_TABLE_CREATE =
            "CREATE TABLE " + VIDEO_TABLE_NAME + " (" +
                    MovieColumns.ID + " INT, " +
                    MovieColumns.VIDEO_URL + " TEXT, " +
                    "FOREIGN KEY(" + MovieColumns.ID + ") REFERENCES " + MOVIE_TABLE_NAME + "(" + MovieColumns.ID + ")" +
                    ");";


    private MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Returns the singleton MovieDBHelper instance
    public static MovieDBHelper getDBHelper(Context context) {
        if (mOpenHelper == null) {
            mOpenHelper = new MovieDBHelper(context);
        }
        return mOpenHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MOVIE_TABLE_CREATE);
        db.execSQL(REVIEW_TABLE_CREATE);
        db.execSQL(VIDEO_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<Movie>();
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        Cursor cursor = db.query(
                MOVIE_TABLE_NAME,       // table name
                null,                   // projection
                null,                   // selection
                null,                   // selection arguments
                null,                   // group
                null,                   // filter by
                null                    // sort
        );

        // iterate through the movie records
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MovieColumns.ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(MovieColumns.TITLE));
            String rating = cursor.getString(cursor.getColumnIndexOrThrow(MovieColumns.RATING));
            String release_date = cursor.getString(cursor.getColumnIndexOrThrow(MovieColumns.RELEASE_DATE));
            String plot = cursor.getString(cursor.getColumnIndexOrThrow(MovieColumns.PLOT));
            String image_url = cursor.getString(cursor.getColumnIndexOrThrow(MovieColumns.IMAGE_URL));

            List<String> reviews = getReviewListForMovieId(id);
            List<String> video_urls = getVideoListForMovieId(id);

            Movie movie = new Movie(id, title, rating, release_date, plot, image_url, video_urls, reviews);
            movies.add(movie);
        }

        return movies;
    }


    public void addMovie(Movie movie) {
        //TODO: check to see if movie already exists

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        // Create a new map of values where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MovieColumns.ID, movie.id);
        values.put(MovieColumns.TITLE, movie.title);
        values.put(MovieColumns.RATING, movie.rating);
        values.put(MovieColumns.RELEASE_DATE, movie.release_date);
        values.put(MovieColumns.PLOT, movie.plot);
        values.put(MovieColumns.IMAGE_URL, movie.image_url);

        // Insert the new row into the movie table
        db.insert(MOVIE_TABLE_NAME, null, values);

        // Insert movie reviews into the review table
        for (String review: movie.reviews) {
            ContentValues cv = new ContentValues();
            cv.put(MovieColumns.ID, movie.id);
            cv.put(MovieColumns.REVIEW, review);
            db.insert(REVIEW_TABLE_NAME, null, cv);
        }

        // Insert movie video urls into the video table
        for (String video_url: movie.video_urls) {
            ContentValues cv = new ContentValues();
            cv.put(MovieColumns.ID, movie.id);
            cv.put(MovieColumns.VIDEO_URL, video_url);
            db.insert(VIDEO_TABLE_NAME, null, cv);
        }
    }


    public void deleteMovie(Movie movie) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        String selection = MovieColumns.ID + " = ?";
        String[] selectionArgs = { Integer.toString(movie.id) };

        // delete review records
        db.delete(REVIEW_TABLE_NAME, selection, selectionArgs);

        // delete video records
        db.delete(VIDEO_TABLE_NAME, selection, selectionArgs);

        // delete movie records
        db.delete(MOVIE_TABLE_NAME, selection, selectionArgs);
    }


    public boolean containsMovie(Movie movie) {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        String selection = MovieColumns.ID + " = ?";
        String[] selectionArgs = { Integer.toString(movie.id) };
        Cursor cursor = db.query(
                MOVIE_TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null
        );
        return cursor.getCount() > 0;
    }

    private List<String> getReviewListForMovieId(int movieId) {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        String[] projection = { MovieColumns.REVIEW };
        String selection = MovieColumns.ID + " = ?";
        String[] selectionArgs = { Integer.toString(movieId) };
        Cursor cursor = db.query(
                REVIEW_TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        List<String> reviews = new ArrayList<String>();
        while(cursor.moveToNext()) {
            String review = cursor.getString(cursor.getColumnIndexOrThrow(MovieColumns.REVIEW));
            reviews.add(review);
        }
        return reviews;
    }


    private List<String> getVideoListForMovieId(int movieId) {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        String[] projection = { MovieColumns.VIDEO_URL };
        String selection = MovieColumns.ID + " = ?";
        String[] selectionArgs = { Integer.toString(movieId) };
        Cursor cursor = db.query(
                VIDEO_TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        List<String> video_urls = new ArrayList<String>();
        while(cursor.moveToNext()) {
            String video_url = cursor.getString(cursor.getColumnIndexOrThrow(MovieColumns.REVIEW));
            video_urls.add(video_url);
        }
        return video_urls;
    }
}
