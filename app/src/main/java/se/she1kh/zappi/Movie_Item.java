package se.she1kh.zappi;

import android.graphics.Bitmap;

/**
 * Created by Usman on 11/1/2015.
 *
 */
public class Movie_Item {
    private String title;
    private String year;
    private Bitmap bitmapPoster;
    private String idIMDB;
    private String rating;
    private String ranking;

    public Movie_Item(String title, String year, Bitmap bitmapPoster, String idIMDB, String rating, String ranking) {
        this.title = title;
        this.year = year;
        this.bitmapPoster = bitmapPoster;
        this.idIMDB = idIMDB;
        this.rating = rating;
        this.ranking = ranking;
    }

    public String getTitle() {
        return title;
    }


    public String getYear() {
        return year;
    }

    public Bitmap getBitmapPoster() {
        return bitmapPoster;
    }

    public String getIdIMDB() {
        return idIMDB;
    }

    public String getRating() {
        return rating;
    }


    public String getRanking() {
        return ranking;
    }


}
