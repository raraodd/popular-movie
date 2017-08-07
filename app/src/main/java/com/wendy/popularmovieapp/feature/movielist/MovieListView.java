package com.wendy.popularmovieapp.feature.movielist;

import com.wendy.popularmovieapp.data.database.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 7/6/17.
 */

public interface MovieListView {
    void loadMovie(String sortBy);
    void updateMovie(List<Movie> newMovies);
}