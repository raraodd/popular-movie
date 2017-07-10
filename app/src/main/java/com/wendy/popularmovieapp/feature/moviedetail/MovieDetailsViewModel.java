package com.wendy.popularmovieapp.feature.moviedetail;

import com.wendy.popularmovieapp.data.Movie;
import com.wendy.popularmovieapp.data.Review;
import com.wendy.popularmovieapp.data.Video;
import com.wendy.popularmovieapp.service.MovieDetailsObserver;
import com.wendy.popularmovieapp.service.MovieDetailsStatus;
import com.wendy.popularmovieapp.service.PopularMovieApp;
import com.wendy.popularmovieapp.utils.Utils;

import java.util.ArrayList;

/**
 * Created by wendy on 7/6/17.
 */

public class MovieDetailsViewModel implements MovieDetailsObserver {

    public Movie movie;
    public ArrayList<Review> reviews;
    public ArrayList<Video> videos;

    private MovieDetailsView view;
    public MovieDetailsViewModel(MovieDetailsView view, Movie movie) {
        this.view = view;
        this.movie = movie;
    }

    public void loadMovieDetails(long movieId) {
        PopularMovieApp.getInstance().loadMovieDetails(movieId);
    }

    public void loadReviews(long movieId) {
        PopularMovieApp.getInstance().loadReviews(movieId);
    }

    public void loadVideos(long movieId) {
        PopularMovieApp.getInstance().loadVideos(movieId);
    }

    public String getTitleAndYear() {
        return movie.getTitle() + " (" + Utils.getYear(movie.getReleaseDate())+")";
    }

    public void attach() {
        MovieDetailsStatus.getInstance().subscribe(this);
    }

    public void detach() {
        MovieDetailsStatus.getInstance().unSubscribe(this);
    }

    @Override
    public void onMovieLoaded(Movie movie) {
        this.movie = movie;
        view.updateView(this.movie);
    }

    @Override
    public void onReviewsLoaded(ArrayList<Review> reviews) {
        this.reviews = reviews;
        view.updateReview(this.reviews);
    }

    @Override
    public void onVideosLoaded(ArrayList<Video> videos) {
        this.videos = videos;
    }
}
