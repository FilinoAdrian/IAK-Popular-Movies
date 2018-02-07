package com.example.android.iak_popular_movies.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.iak_popular_movies.R;
import com.example.android.iak_popular_movies.fragment.NowPlayingFragment;
import com.example.android.iak_popular_movies.fragment.PopularFragment;
import com.example.android.iak_popular_movies.fragment.TopRatedFragment;
import com.example.android.iak_popular_movies.fragment.UpcomingFragment;

/**
 * Created by Filino on 07/02/2018.
 */

public class CategoryAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fragmentManager){
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PopularFragment();
        } else if (position == 1) {
            return new TopRatedFragment();
        } else if (position == 2) {
            return new UpcomingFragment();
        } else {
            return new NowPlayingFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.category_popular);
        } else if (position == 1) {
            return mContext.getString(R.string.category_top_rated);
        } else if (position == 2) {
            return mContext.getString(R.string.category_upcoming);
        } else {
            return mContext.getString(R.string.category_now_playing);
        }
    }
}
