package com.wendy.popularmovieapp.feature.moviedetail;

import com.wendy.popularmovieapp.data.Movie;
import com.wendy.popularmovieapp.data.Review;
import com.wendy.popularmovieapp.data.Video;
import com.wendy.popularmovieapp.service.MovieDetailsObserver;
import com.wendy.popularmovieapp.service.MovieDetailsStatus;
import com.wendy.popularmovieapp.service.PopularMovieApp;
import com.wendy.popularmovieapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 7/6/17.
 */

public class MovieDetailsViewModel implements MovieDetailsObserver {

    public Long movieId;
    public Movie movie;
    public List<Review> reviews = new ArrayList<>();
    public List<Video> videos = new ArrayList<>();

    private MovieDetailsView view;
    public MovieDetailsViewModel(MovieDetailsView view, Long movieId) {
        this.view = view;
        this.movieId= movieId;
        this.movie = PopularMovieApp.getInstance().getMovieById(this.movieId);
    }

    public void loadMovieDetails() {
        if(PopularMovieApp.getInstance().getMovieById(movieId).runtime == null)
            PopularMovieApp.getInstance().loadMovieDetails(movieId);
        else
            onMovieLoaded();
    }

    public void setFavorite(int isFavorite) {
        PopularMovieApp.getInstance().setFavorite(isFavorite, movieId);
    }

    public void loadReviews() {
        if(PopularMovieApp.getInstance().getReviews(movieId).size() == 0)
            PopularMovieApp.getInstance().loadReviews(movieId);
        else
            onReviewsLoaded();
    }

    public void loadVideos() {
        if(PopularMovieApp.getInstance().getVideos(movieId).size() == 0)
            PopularMovieApp.getInstance().loadVideos(movieId);
        else
            onVideosLoaded();
    }

    public String getTitleAndYear() {
        return movie.title + " (" + Utils.getYear(movie.releaseDate)+")";
    }

    public void attach() {
        MovieDetailsStatus.getInstance().subscribe(this);
    }

    public void detach() {
        MovieDetailsStatus.getInstance().unSubscribe(this);
    }

    @Override
    public void onMovieLoaded() {
        this.movie = PopularMovieApp.getInstance().getMovieById(movieId);
        view.updateView(this.movie);
    }

    @Override
    public void onReviewsLoaded() {
        reviews = PopularMovieApp.getInstance().getReviews(movieId);
        view.updateReview(reviews);
    }

    @Override
    public void onVideosLoaded() {
        videos = PopularMovieApp.getInstance().getVideos(movieId);
        view.updateVideo(videos);
    }
}
