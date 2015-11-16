package com.niksplay.moviesland.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.niksplay.moviesland.R;
import com.niksplay.moviesland.model.IMovie;
import com.niksplay.moviesland.utils.ImageUrls;
import com.niksplay.moviesland.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nikita on 16.11.15.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<? extends IMovie> mData;

    public void setData(List<? extends IMovie> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IMovie movie = mData.get(position);
        int year = Utils.getYear(movie.getReleaseDate());
        holder.mTitleView.setText(movie.getTitle());
        holder.mDescriptionView.setText(movie.getOverview());
        holder.mRateView.setText(String.valueOf(movie.getVoteAverage()));
        holder.mReleaseDate.setText(year == 0 ? "" : String.valueOf(year));
        holder.mGenresView.setText(Utils.formatGenres(movie));
        Picasso.with(holder.mImageView.getContext()).load(ImageUrls.getPosterUrl(movie.getPosterPath())).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.movie_title)
        TextView mTitleView;
        @Bind(R.id.movie_description)
        TextView mDescriptionView;
        @Bind(R.id.movie_thumbnail)
        ImageView mImageView;
        @Bind(R.id.movie_rate)
        TextView mRateView;
        @Bind(R.id.movie_release_year)
        TextView mReleaseDate;
        @Bind(R.id.movie_genres)
        TextView mGenresView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
