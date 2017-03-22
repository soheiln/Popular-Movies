package com.example.soheiln.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MovieContentProvider extends ContentProvider {

    // path integers used for Uri matching
    public static final int MOVIES = 100;
    public static final int MOVIE_WITH_ID = 101;
    public static final int REVIEWS = 200;
    public static final int REVIEWS_FOR_MOVIE_ID = 201;
    public static final int VIDEOS = 300;
    public static final int VIDEOS_FOR_MOVIE_ID = 301;


    private DBOpenHelper mDBOpenHelper;
    private static UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_REVIEWS, REVIEWS);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_REVIEWS_FOR_MOVIE_ID, REVIEWS_FOR_MOVIE_ID);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_VIDEOS, VIDEOS);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_VIDEOS_FOR_MOVIE_ID, VIDEOS_FOR_MOVIE_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDBOpenHelper = new DBOpenHelper(context);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mDBOpenHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        String mTableName;
        String[] mProjection = projection;
        String mSelection = selection;
        String[] mSelectionArgs = selectionArgs;
        String movieId;
        Cursor retCursor;
        switch (match) {
            case MOVIES:
                mTableName = MovieContract.MovieEntry.TABLE_NAME;
                break;
            case REVIEWS:
                mTableName = MovieContract.ReviewEntry.TABLE_NAME;
                break;
            case VIDEOS:
                mTableName = MovieContract.VideoEntry.TABLE_NAME;
                break;
            case MOVIE_WITH_ID:
                mTableName = MovieContract.MovieEntry.TABLE_NAME;
                movieId = uri.getPathSegments().get(1);
                mSelection = MovieContract.MovieEntry.COL_ID + "=?";
                mSelectionArgs = new String[]{movieId};
                break;
            case REVIEWS_FOR_MOVIE_ID:
                mTableName = MovieContract.ReviewEntry.TABLE_NAME;
                movieId = uri.getPathSegments().get(1);
                mSelection = MovieContract.MovieEntry.COL_ID + "=?";
                mSelectionArgs = new String[]{movieId};
                break;
            case VIDEOS_FOR_MOVIE_ID:
                mTableName = MovieContract.VideoEntry.TABLE_NAME;
                movieId = uri.getPathSegments().get(1);
                mSelection = MovieContract.MovieEntry.COL_ID + "=?";
                mSelectionArgs = new String[]{movieId};
                break;
            default:
                throw new UnsupportedOperationException("Unkown Uri: " + uri);
        }
        retCursor = db.query(mTableName,
                mProjection,
                mSelection,
                mSelectionArgs,
                null,
                null,
                sortOrder);
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        long id;
        switch (match) {
            case MOVIES:
                id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
                    break;
                } else {
                    throw new SQLiteException("Failed to insert row into " + uri);
                }
            case REVIEWS:
                id = db.insert(MovieContract.ReviewEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieContract.ReviewEntry.CONTENT_URI, id);
                    break;
                } else {
                    throw new SQLiteException("Failed to insert row into " + uri);
                }
            case VIDEOS:
                id = db.insert(MovieContract.VideoEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieContract.VideoEntry.CONTENT_URI, id);
                    break;
                } else {
                    throw new SQLiteException("Failed to insert row into " + uri);
                }
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int numDeleted;
        String mTableName;
        String mSelection = selection;
        String[] mSelectionArgs = selectionArgs;
        String movieId;
        switch (match) {
            case MOVIES:
                mTableName = MovieContract.MovieEntry.TABLE_NAME;
                break;
            case REVIEWS:
                mTableName = MovieContract.ReviewEntry.TABLE_NAME;
                break;
            case VIDEOS:
                mTableName = MovieContract.VideoEntry.TABLE_NAME;
                break;
            case MOVIE_WITH_ID:
                mTableName = MovieContract.MovieEntry.TABLE_NAME;
                movieId = uri.getPathSegments().get(1);
                mSelection = MovieContract.MovieEntry.COL_ID + "=?";
                mSelectionArgs = new String[]{movieId};
                break;
            case REVIEWS_FOR_MOVIE_ID:
                mTableName = MovieContract.ReviewEntry.TABLE_NAME;
                movieId = uri.getPathSegments().get(1);
                mSelection = MovieContract.MovieEntry.COL_ID + "=?";
                mSelectionArgs = new String[]{movieId};
                break;
            case VIDEOS_FOR_MOVIE_ID:
                mTableName = MovieContract.VideoEntry.TABLE_NAME;
                movieId = uri.getPathSegments().get(1);
                mSelection = MovieContract.MovieEntry.COL_ID + "=?";
                mSelectionArgs = new String[]{movieId};
                break;
            default:
                throw new UnsupportedOperationException("Unkown Uri: " + uri);
        }
        numDeleted = db.delete(mTableName,
                mSelection,
                mSelectionArgs);
        return numDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int numUpdated;
        String mTableName;
        String mSelection = selection;
        String[] mSelectionArgs = selectionArgs;
        String movieId;
        switch (match) {
            case MOVIES:
                mTableName = MovieContract.MovieEntry.TABLE_NAME;
                break;
            case REVIEWS:
                mTableName = MovieContract.ReviewEntry.TABLE_NAME;
                break;
            case VIDEOS:
                mTableName = MovieContract.VideoEntry.TABLE_NAME;
                break;
            case MOVIE_WITH_ID:
                mTableName = MovieContract.MovieEntry.TABLE_NAME;
                movieId = uri.getPathSegments().get(1);
                mSelection = MovieContract.MovieEntry.COL_ID + "=?";
                mSelectionArgs = new String[]{movieId};
                break;
            case REVIEWS_FOR_MOVIE_ID:
                mTableName = MovieContract.ReviewEntry.TABLE_NAME;
                movieId = uri.getPathSegments().get(1);
                mSelection = MovieContract.MovieEntry.COL_ID + "=?";
                mSelectionArgs = new String[]{movieId};
                break;
            case VIDEOS_FOR_MOVIE_ID:
                mTableName = MovieContract.VideoEntry.TABLE_NAME;
                movieId = uri.getPathSegments().get(1);
                mSelection = MovieContract.MovieEntry.COL_ID + "=?";
                mSelectionArgs = new String[]{movieId};
                break;
            default:
                throw new UnsupportedOperationException("Unkown Uri: " + uri);
        }
        numUpdated = db.update(mTableName,
                contentValues,
                mSelection,
                mSelectionArgs);
        return numUpdated;
    }
}
