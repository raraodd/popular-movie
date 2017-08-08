package com.wendy.popularmovieapp.service;

/**
 * Created by SRIN on 7/7/2017.
 */

public interface MovieListObserver {
    void onMoviesLoaded(String sortBy);

}