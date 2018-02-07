package com.example.android.iak_popular_movies.utilities;

import com.example.android.iak_popular_movies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Filino on 06/02/2018.
 */

public final class NetworkUtils {

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    public static URL buildUrl(String category) {
        String urlRequest = BASE_URL + category + "?api_key=" + BuildConfig.THE_MOVIE_DB_API_TOKEN;

        URL url = null;
        try {
            url = new URL(urlRequest);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
