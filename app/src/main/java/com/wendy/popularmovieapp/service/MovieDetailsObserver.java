package com.wendy.popularmovieapp.service;

import com.wendy.popularmovieapp.data.database.Movie;
import com.wendy.popularmovieapp.data.database.Review;
import com.wendy.popularmovieapp.data.database.Video;

import java.util.ArrayList;

/**
 * Created by SRIN on 7/7/2017.
 */

public interface MovieDetailsObserver {
    void onMovieLoaded(Movie movie);
    void onReviewsLoaded(ArrayList<Review> reviews);
    void onVideosLoaded(ArrayList<Video> videos);
}
