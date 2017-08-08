package com.wendy.popularmovieapp.feature.movielist;

import com.wendy.popularmovieapp.Constant;
import com.wendy.popularmovieapp.data.Movie;
import com.wendy.popularmovieapp.service.MovieListObserver;
import com.wendy.popularmovieapp.service.MovieListStatus;
import com.wendy.popularmovieapp.service.PopularMovieApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 7/6/17.
 */

public class MovieListViewModel implements MovieListObserver {

    public List<Movie> movies = new ArrayList<>();

    private MovieListView view;
    public MovieListViewModel(MovieListView view) {
        this.view = view;
    }

    public void loadMovie(String sortBy) {
        if(PopularMovieApp.getInstance().getMovies(sortBy).size() == 0 && !sortBy.equals(Constant.SORT_BY_FAVORITE)) {
            PopularMovieApp.getInstance().loadMovie(sortBy);
        } else {
            onMoviesLoaded(sortBy);
        }
    }

    public void attach() {
        MovieListStatus.getInstance().subscribe(this);
    }

    public void detach() {
        MovieListStatus.getInstance().unSubscribe(this);
    }

    @Override
    public void onMoviesLoaded(String sortBy) {
        movies = PopularMovieApp.getInstance().getMovies(sortBy);
        view.updateMovie(movies);
    }
}