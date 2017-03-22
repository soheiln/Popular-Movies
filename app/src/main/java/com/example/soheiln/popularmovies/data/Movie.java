package com.example.soheiln.popularmovies.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable {

    public static final String FAVORITE_KEY = "FAVORITE";

    public int id = 0;
    public String title = "";
    public String rating = "";
    public String release_date = "";
    public String plot = "";
    public String image_url = "";
    public List<String> video_urls = new ArrayList<String>();
    public List<String> reviews = new ArrayList<String>();
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



    /*
     * Implementation of the Percelable interface
     */
    public static final String PARCEL_NAME = "MOVIE";

    Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        rating = in.readString();
        release_date = in.readString();
        plot = in.readString();
        image_url = in.readString();
        reviews = in.createStringArrayList();
        video_urls = in.createStringArrayList();
        favorite = (in.readInt() == 1);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(rating);
        parcel.writeString(release_date);
        parcel.writeString(plot);
        parcel.writeString(image_url);
        parcel.writeStringList(reviews);
        parcel.writeStringList(video_urls);
        parcel.writeInt(favorite ? 1 : 0);
    }

    static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
