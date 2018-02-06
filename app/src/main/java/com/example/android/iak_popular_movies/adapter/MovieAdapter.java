package com.example.android.iak_popular_movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.iak_popular_movies.DetailActivity;
import com.example.android.iak_popular_movies.R;
import com.example.android.iak_popular_movies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Filino on 06/02/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    public Context context;
    public ArrayList<Movie> movieList;

    public MovieAdapter(Context context, ArrayList<Movie> movieList){
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.movie_item_list, null);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        // ItemObject.Results item = itemList.get(position);
        Picasso.with(context).load
                ("https://image.tmdb.org/t/p/w185" + movieList.get(position).getPosterPath())
                .placeholder(R.drawable.logo_item)
                .into(holder.img);

        holder.originalTitle.setText(movieList.get(position).getOriginalTitle());
    }

    @Override
    public int getItemCount() {
        if (movieList == null){
            return 0;
        }
        return movieList.size();
    }

    public void setMovieData(ArrayList<Movie> movieList){
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView originalTitle;
        public ImageView img;

        public MovieViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            img = (ImageView) itemView.findViewById(R.id.list_avatar);
            originalTitle = (TextView)itemView.findViewById(R.id.list_title);
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(), DetailActivity.class);
            view.getContext().startActivity(i);
        }
    }
}
