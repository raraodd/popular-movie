package com.wendy.popularmovieapp.feature.movielist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wendy.popularmovieapp.R;
import com.wendy.popularmovieapp.data.Movie;
import com.wendy.popularmovieapp.service.PopularMovieApp;

import java.util.List;

/**
 * Created by SRIN on 7/7/2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private Context mContext;
    private final ListMovieClickListener mOnClickListener;

    public MovieListAdapter(Context context, List<Movie> movies, ListMovieClickListener listener) {
        this.mContext = context;
        this.mOnClickListener = listener;
        this.movies = movies;
    }

    public interface ListMovieClickListener {
        void onMovieListClick(int clickedMovieIndex);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        int layoutIdForMovieItem = R.layout.movie_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutIdForMovieItem, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mMovieTitle;
        ImageView mPoster;

        public MovieViewHolder(View itemView) {
            super(itemView);

            mMovieTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
            mPoster = (ImageView) itemView.findViewById(R.id.iv_poster);

            itemView.setOnClickListener(this);
        }

        void bind(Movie movie) {
            mMovieTitle.setText(String.valueOf(movie.title));

            Picasso.with(mContext)
                    .load(PopularMovieApp.API_POSTER_HEADER_LARGE + movie.poster)
                    .placeholder(R.drawable.ic_sentiment_dissatisfied)
                    .into(mPoster);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onMovieListClick(clickedPosition);
        }
    }
}
