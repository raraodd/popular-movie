package com.wendy.popularmovieapp.service;

/**
 * Created by SRIN on 7/7/2017.
 */

public interface MovieDetailsObserver {
    void onMovieLoaded();
    void onReviewsLoaded();
    void onVideosLoaded();
}
