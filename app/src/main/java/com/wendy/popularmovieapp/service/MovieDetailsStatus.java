package com.wendy.popularmovieapp.service;

import com.wendy.popularmovieapp.data.database.Movie;
import com.wendy.popularmovieapp.data.database.Review;
import com.wendy.popularmovieapp.data.database.Video;

import java.util.ArrayList;

/**
 * Created by SRIN on 7/7/2017.
 */

public class MovieDetailsStatus
{
    private static MovieDetailsStatus instance;
    public static MovieDetailsStatus getInstance() {
        if(instance == null) {
            instance = new MovieDetailsStatus();
        }
        return instance;
    }
    private MovieDetailsStatus() {
    }

    private ArrayList<MovieDetailsObserver> observers = new ArrayList<>();

    public void subscribe(MovieDetailsObserver observer) {
        observers.add(observer);
    }

    public void unSubscribe(MovieDetailsObserver observer) {
        observers.remove(observer);
    }

    public void notifyMovieUpdated(Movie movie) {
        for(MovieDetailsObserver observer : observers) {
            observer.onMovieLoaded(movie);
        }
    }

    public void notifyReviewUpdate(ArrayList<Review> reviews) {
        for(MovieDetailsObserver observer : observers) {
            observer.onReviewsLoaded(reviews);
        }
    }

    public void notifyVideoUpdate(ArrayList<Video> videos) {
        for(MovieDetailsObserver observer : observers) {
            observer.onVideosLoaded(videos);
        }
    }
}