package com.example.soheiln.popularmovies;

/**
 * Created by distributionlab on 3/16/17.
 */

public class Movie {
    public int id = 0;
    public String title = "";
    public String rating = "";
    public String release_date = "";
    public String plot = "";
    public String image_URL = "";

    Movie(int id, String title, String rating, String release_date, String plot, String image_URL) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.release_date = release_date;
        this.plot = plot;
        this.image_URL = image_URL;
    }
}
