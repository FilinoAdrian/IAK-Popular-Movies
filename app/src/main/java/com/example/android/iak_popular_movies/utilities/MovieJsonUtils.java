package com.example.android.iak_popular_movies.utilities;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.iak_popular_movies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Filino on 06/02/2018.
 */

public class MovieJsonUtils {

    public static ArrayList<Movie> extractMovieFromJson(String movieJson)
            throws JSONException{

        if (TextUtils.isEmpty(movieJson)){
            return null;
        }

        ArrayList<Movie> movieList = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(movieJson);
            JSONArray movieArray = baseJsonResponse.getJSONArray("results");

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject currentMovie = movieArray.getJSONObject(i);

                String posterPath = currentMovie.getString("poster_path");
                String originalTitle = currentMovie.getString("original_title");
                String overview = currentMovie.getString("overview");
                String releaseDate = currentMovie.getString("release_date");
                String vote = currentMovie.getString("vote_average");
                String backdropPath = currentMovie.getString("backdrop_path");

                Movie movie = new Movie(posterPath, originalTitle, overview, releaseDate, vote, backdropPath);
                movieList.add(movie);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return movieList;
    }
}
