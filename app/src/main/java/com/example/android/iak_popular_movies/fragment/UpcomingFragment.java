package com.example.android.iak_popular_movies.fragment;


import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.iak_popular_movies.R;
import com.example.android.iak_popular_movies.adapter.MovieAdapter;
import com.example.android.iak_popular_movies.model.Movie;
import com.example.android.iak_popular_movies.utilities.MovieJsonUtils;
import com.example.android.iak_popular_movies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private SwipeRefreshLayout swipeContainer;
    private ProgressDialog progressDialog;

    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.movie_list, container, false);

        initViews(rootView);

        swipeContainer = (SwipeRefreshLayout)rootView.findViewById(R.id.main_content);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews(rootView);
            }
        });

        return rootView;
    }

    public void initViews(View view){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        }

        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());

        mRecyclerView.setAdapter(mMovieAdapter);

        String category = "upcoming";
        new FetchMovieTask().execute(category);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {

            if (params.length == 0){
                return null;
            }

            String category = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(category);

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
