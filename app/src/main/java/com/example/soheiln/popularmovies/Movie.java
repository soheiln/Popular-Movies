package com.example.soheiln.popularmovies;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    public static String TITLE_KEY = "TITLE_KEY";
    public static String RATING_KEY = "RATING_KEY";
    public static String RELEASE_DATE_KEY = "RELEASE_DATE_KEY";
    public static String PLOT_KEY = "PLOT_KEY";
    public static String IMAGE_URL_KEY = "IMAGE_URL_KEY";
    public static String REVIEWS_KEY = "REVIEWS_KEY";
    public static String VIDEO_URLS_KEY = "VIDEO_URLS_KEY";

    public int id = 0;
    public String title = "";
    public String rating = "";
    public String release_date = "";
    public String plot = "";
    public String image_URL = "";
    public List<String> video_urls = null;
    public List<String> reviews = null;

    Movie(int id, String title, String rating, String release_date, String plot, String image_URL) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.release_date = release_date;
        this.plot = plot;
        this.image_URL = image_URL;
    }

    Movie(int id, String title, String rating, String release_date, String plot, String image_URL, List<String> video_urls, List<String> reviews) {
        this(id, title, rating, release_date, plot, image_URL);
        this.video_urls = video_urls;
        this.reviews = reviews;
    }
}
