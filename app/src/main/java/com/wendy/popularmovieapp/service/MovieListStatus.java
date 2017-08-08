package com.wendy.popularmovieapp.service;

import java.util.ArrayList;

/**
 * Created by wendy on 7/7/2017.
 */

public class MovieListStatus {

    private static MovieListStatus instance;
    public static MovieListStatus getInstance() {
        if(instance == null) {
            instance = new MovieListStatus();
        }
        return instance;
    }
    private MovieListStatus() {
    }

    private ArrayList<MovieListObserver> observers = new ArrayList<>();

    public void subscribe(MovieListObserver observer) {
        observers.add(observer);
    }

    public void unSubscribe(MovieListObserver observer) {
        observers.remove(observer);
    }

    public void notifyMovieUpdated(String sortBy) {
        for(MovieListObserver observer : observers) {
            observer.onMoviesLoaded(sortBy);
        }
    }
}
