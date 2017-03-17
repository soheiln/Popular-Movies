package com.example.soheiln.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MovieAdapter extends BaseAdapter {

    private Context mContext;
    private List<Movie> mGridData = new ArrayList<Movie>();

    MovieAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mGridData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        view = inflater.inflate(R.layout.movie_grid_item, parent, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_movie_item);

        Movie movie = mGridData.get(position);

        Picasso.with(mContext).load(movie.image_URL).into(imageView);
        return view;

    }

    public void setGridData(List<Movie> data) {
        mGridData = data;
        notifyDataSetChanged();
    }
}
