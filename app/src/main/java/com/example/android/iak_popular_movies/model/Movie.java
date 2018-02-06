package com.example.android.iak_popular_movies.model;

/**
 * Created by Filino on 06/02/2018.
 */

public class Movie {

    private String posterPath;
    private String originalTitle;

    public Movie(String posterPath, String originalTitle) {
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }
}
