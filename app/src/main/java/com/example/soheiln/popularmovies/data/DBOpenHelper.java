package com.example.soheiln.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBOpenHelper extends SQLiteOpenHelper {

    // Singleton Instance
    private static DBOpenHelper mOpenHelper = null;

    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;
    private static final String MOVIE_TABLE_NAME = "movie";
    private static final String REVIEW_TABLE_NAME = "review";
    private static final String VIDEO_TABLE_NAME = "video";

    private static final String MOVIE_TABLE_CREATE =
            "CREATE TABLE " + MOVIE_TABLE_NAME + " (" +
                    MovieContract.MovieEntry.COL_ID+ " INT PRIMARY KEY, " +
                    MovieContract.MovieEntry.COL_TITLE + " TEXT, " +
                    MovieContract.MovieEntry.COL_RATING + " TEXT, " +
                    MovieContract.MovieEntry.COL_RELEASE_DATE + " TEXT, " +
                    MovieContract.MovieEntry.COL_PLOT + " TEXT, " +
                    MovieContract.MovieEntry.COL_IMAGE_URL + " TEXT);";

    private static final String REVIEW_TABLE_CREATE =
            "CREATE TABLE " + REVIEW_TABLE_NAME + " (" +
                    MovieContract.MovieEntry.COL_ID + " INT, " +
                    MovieContract.ReviewEntry.COL_REVIEW + " TEXT, " +
                    "FOREIGN KEY(" + MovieContract.MovieEntry.COL_ID + ") REFERENCES " + MOVIE_TABLE_NAME + "(" + MovieContract.MovieEntry.COL_ID + ")" +
                    ");";

    private static final String VIDEO_TABLE_CREATE =
            "CREATE TABLE " + VIDEO_TABLE_NAME + " (" +
                    MovieContract.MovieEntry.COL_ID + " INT, " +
                    MovieContract.VideoEntry.COL_VIDEO_URL + " TEXT, " +
                    "FOREIGN KEY(" + MovieContract.MovieEntry.COL_ID + ") REFERENCES " + MOVIE_TABLE_NAME + "(" + MovieContract.MovieEntry.COL_ID + ")" +
                    ");";


    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Returns the singleton DBOpenHelper instance
    public static DBOpenHelper getDBHelper(Context context) {
        if (mOpenHelper == null) {
            mOpenHelper = new DBOpenHelper(context);
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

}
