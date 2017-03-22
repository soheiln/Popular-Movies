package com.example.soheiln.popularmovies.data;


import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.soheiln.popularmovies";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    public static final String PATH_MOVIES = "movies";
    public static final String PATH_REVIEWS = "reviews";
    public static final String PATH_REVIEWS_FOR_MOVIE_ID = "reviews/#";
    public static final String PATH_VIDEOS = "videos";
    public static final String PATH_VIDEOS_FOR_MOVIE_ID = "videos/#";

    // MovieEntry is an inner class that defines the contents of the movie table
    public static final class MovieEntry implements BaseColumns {

        // MovieEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        // Movie table and column names
        public static final String TABLE_NAME = "movie";

        // Since MovieEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String COL_ID = "movie_id";
        public static final String COL_TITLE = "title";
        public static final String COL_RATING = "rating";
        public static final String COL_RELEASE_DATE = "release_date";
        public static final String COL_PLOT = "plot";
        public static final String COL_IMAGE_URL = "image_url";
    }


    // ReviewEntry is an inner class that defines the contents of the review table
    public static final class ReviewEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEWS).build();

        public static final String TABLE_NAME = "review";

        public static final String COL_ID = "movie_id";
        public static final String COL_REVIEW = "review";
    }

    // VideoEntry is an inner class that defines the contents of the review table
    public static final class VideoEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_VIDEOS).build();

        public static final String TABLE_NAME = "video";

        public static final String COL_ID = "movie_id";
        public static final String COL_VIDEO_URL = "video_url";
    }
}
