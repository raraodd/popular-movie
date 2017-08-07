package com.wendy.popularmovieapp.service;

import com.wendy.popularmovieapp.data.database.Movie;

import java.util.ArrayList;

/**
 * Created by SRIN on 7/7/2017.
 */

public interface MovieListObserver {
    void onMoviesLoaded(String sortBy);

}