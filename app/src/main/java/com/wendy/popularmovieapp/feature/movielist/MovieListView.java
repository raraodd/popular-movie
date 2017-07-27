package com.wendy.popularmovieapp.feature.movielist;

import com.wendy.popularmovieapp.data.database.Movie;

import java.util.ArrayList;

/**
 * Created by wendy on 7/6/17.
 */

public interface MovieListView {
    void loadMovie(String sortBy);
    void updateMovie(ArrayList<Movie> newMovies);
}