package com.example.soheiln.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.soheiln.popularmovies.data.Movie;
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
        ViewHolder viewHolder;

        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.movie_grid_item, parent, false);

            // set up the viewHolder
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_movie_item);

            // store the holder with the view
            convertView.setTag(viewHolder);
        } else {
            // get the viewHolder that is tagged with convertView
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Movie movie = mGridData.get(position);
        if (movie != null) {
            Picasso.with(mContext).load(movie.image_url).into(viewHolder.imageView);
        }
        return convertView;
    }

    public void setGridData(List<Movie> data) {
        mGridData = data;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView imageView;
    }

    
}
