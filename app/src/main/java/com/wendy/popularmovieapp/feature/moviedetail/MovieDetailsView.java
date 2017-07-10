package com.wendy.popularmovieapp.feature.moviedetail;

import com.wendy.popularmovieapp.data.Movie;
import com.wendy.popularmovieapp.data.Review;
import com.wendy.popularmovieapp.data.Video;

import java.util.ArrayList;

/**
 * Created by wendy on 7/6/17.
 */

public interface MovieDetailsView {
    void loadMovieDetails(long movieId);
    void loadReviews(long movieId);
    void loadVideos(long movieId);
    void updateView(Movie movie);
    void updateReview(ArrayList<Review> reviews);
    void updateVideo(ArrayList<Video> videos);
}