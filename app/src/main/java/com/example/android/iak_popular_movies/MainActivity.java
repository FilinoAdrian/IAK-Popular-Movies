package com.example.android.iak_popular_movies;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.iak_popular_movies.adapter.MovieAdapter;
import com.example.android.iak_popular_movies.model.Movie;
import com.example.android.iak_popular_movies.utilities.MovieJsonUtils;
import com.example.android.iak_popular_movies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private SwipeRefreshLayout swipeContainer;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.main_content);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
            }
        });
    }

    public void initViews(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        GridLayoutManager layoutManager =
                new GridLayoutManager(MainActivity.this, 2);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this, new ArrayList<Movie>());

        mRecyclerView.setAdapter(mMovieAdapter);

        new FetchMovieTask().execute();
    }

    public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>>{

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {

//            if (params.length == 0){
//                return null;
//            }
//
//            String category = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl();

            try{
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                ArrayList<Movie> jsonMovieData = MovieJsonUtils
                        .extractMovieFromJson(jsonMovieResponse);

                return jsonMovieData;
            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            if (swipeContainer.isRefreshing()){
                swipeContainer.setRefreshing(false);
            }
            progressDialog.dismiss();
            mMovieAdapter.setMovieData(movies);
        }
    }
}
