package com.wendy.popularmovieapp.feature.movielist;

import com.wendy.popularmovieapp.data.database.Movie;
import com.wendy.popularmovieapp.service.MovieListObserver;
import com.wendy.popularmovieapp.service.MovieListStatus;
import com.wendy.popularmovieapp.service.PopularMovieApp;

import java.util.ArrayList;

/**
 * Created by wendy on 7/6/17.
 */

public class MovieListViewModel implements MovieListObserver {

    public ArrayList<Movie> movies = new ArrayList<>();
    public ArrayList<Movie> favMovies = new ArrayList<>();

    private MovieListView view;
    public MovieListViewModel(MovieListView view) {
        this.view = view;
    }

    public void loadMovie(String sortBy) {
        PopularMovieApp.getInstance().loadMovie(sortBy);
    }

    public void attach() {
        MovieListStatus.getInstance().subscribe(this);
    }

    public void detach() {
        MovieListStatus.getInstance().unSubscribe(this);
    }

    @Override
    public void onMoviesLoaded(ArrayList<Movie> newMovies) {
        this.movies = newMovies;
        view.updateMovie(movies);
    }
}