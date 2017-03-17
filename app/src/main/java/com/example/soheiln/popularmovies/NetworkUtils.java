package com.example.soheiln.popularmovies;

import android.content.Context;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NetworkUtils {

    public static final String MOVIE_ORDER_BY_POPULARITY = "popular";
    public static final String MOVIE_ORDER_BY_RATING = "top_rated";

    private static final String BASE_URL_MOVIES = "https://api.themoviedb.org/3/movie/";
    private static final String BASE_URL_MOVIE_IMAGE = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w185";
    private static final String PARAM_API_KEY = "api_key";
    private static final String PARAM_LANGUAGE = "language";
    private static final String LANGUAGE = "en-US";
    private static final String PARAM_PAGE = "page";
    private static final String PAGE = "1";

    private static final String TMDB_API_KEY = BuildConfig.TMDB_API_KEY;


    public static List<Movie> getMoviesOrderedBy(String order) {
        OkHttpClient client = new OkHttpClient();
        String urlString = getUrlForMoviesOrderedBy(order).toString();
        Request request = new Request.Builder().url(urlString).build();
        List<Movie> movies = new ArrayList<Movie>();
        try {
            Response response = client.newCall(request).execute();
            String jsonString = response.body().string();
            movies = extractMoviesFromJSON(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }

    private static URL getUrlForMoviesOrderedBy(String order) {
        Uri uri = Uri.parse(BASE_URL_MOVIES).buildUpon().appendPath(order)
                .appendQueryParameter(PARAM_API_KEY, TMDB_API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE)
                .appendQueryParameter(PARAM_PAGE, PAGE)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    private static List<Movie> extractMoviesFromJSON(String jsonString) {
        List<Movie> output = new ArrayList<Movie>();
        try {
            JSONObject json = new JSONObject(jsonString);
            JSONArray results = json.getJSONArray("results");
            for (int i=0; i<results.length(); i++) {
                JSONObject jsonMovie = results.getJSONObject(i);
                int id = jsonMovie.getInt("id");
                String title = jsonMovie.getString("original_title");
                String poster_path = jsonMovie.getString("poster_path");
                String image_url = getImageUrlFromPosterPath(poster_path);
                String plot = jsonMovie.getString("overview");
                String release_date = jsonMovie.getString("release_date");
                double vote_average = jsonMovie.getDouble("vote_average");
                String rating = new Double(vote_average).toString();
                Movie movie = new Movie(id, title, rating, release_date, plot, image_url);
                output.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return output;
    }

    private static String getImageUrlFromPosterPath(String poster_path) {
        String poster_path_without_slash = "";
        if (poster_path.length()>0) {
            poster_path_without_slash = poster_path.substring(1);
        }
        Uri uri = Uri.parse(BASE_URL_MOVIE_IMAGE).buildUpon()
                .appendPath(IMAGE_SIZE)
                .appendPath(poster_path_without_slash)
                .build();
        return uri.toString();
    }


}
