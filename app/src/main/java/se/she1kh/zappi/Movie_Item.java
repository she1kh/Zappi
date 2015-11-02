package se.she1kh.zappi;

/**
 * Created by Usman on 11/1/2015.
 */
public class Movie_Item {
    private String title;
    private String year;
    private String urlPoster;
    private String idIMDB;
    private String rating;
    private String ranking;

    public Movie_Item(String title, String year, String urlPoster, String idIMDB, String rating, String ranking) {
        this.title = title;
        this.year = year;
        this.urlPoster = urlPoster;
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

    public String getUrlPoster() {
        return urlPoster;
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
