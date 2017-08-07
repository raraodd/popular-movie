package com.wendy.popularmovieapp.feature.moviedetail;

import com.wendy.popularmovieapp.data.database.Movie;
import com.wendy.popularmovieapp.data.database.Review;
import com.wendy.popularmovieapp.data.database.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 7/6/17.
 */

public interface MovieDetailsView {
    void loadMovieDetails();
    void loadReviews();
    void loadVideos();
    void updateView(Movie movie);
    void updateReview(List<Review> reviews);
    void updateVideo(List<Video> videos);
}