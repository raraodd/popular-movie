package com.wendy.popularmovieapp.service;

import android.util.Log;

import com.wendy.popularmovieapp.BuildConfig;
import com.wendy.popularmovieapp.data.Review;
import com.wendy.popularmovieapp.data.Video;
import com.wendy.popularmovieapp.data.api.Api;
import com.wendy.popularmovieapp.data.Movie;
import com.wendy.popularmovieapp.data.api.MovieResponse;
import com.wendy.popularmovieapp.data.api.ReviewResponse;
import com.wendy.popularmovieapp.data.api.VideoResponse;

import java.util.ArrayList;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wendy on 7/6/17.
 */

public class PopularMovieApp {

    public static final String API_BASE_URL = "http://api.themoviedb.org/3/";
    public static final String API_POSTER_HEADER_LARGE = "http://image.tmdb.org/t/p/w185";
    public static final String API_POSTER_HEADER_SMALL = "http://image.tmdb.org/t/p/w92";
    public static final String API_BACKDROP_HEADER = "http://image.tmdb.org/t/p/w780";

    public static final String API_KEY = BuildConfig.API_KEY;

    private static PopularMovieApp instance;
    public static PopularMovieApp getInstance() {
        if(instance == null) {
            instance = new PopularMovieApp();
        }
        return instance;
    }

    public PopularMovieApp() {
    }

    private Api api;
    private Api getApi() {
        if(api == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            api = retrofit.create(Api.class);
        }
        return api;
    }

    public void loadMovie(String sortBy) {
        // get from api
        Call<MovieResponse> call = getApi().getMovieList(sortBy, API_KEY);
        Log.d("WENDY", "Call api");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.isSuccessful()) {
                    Log.d("WENDY", "Dapet response");
                    ArrayList<Movie> newMovies = response.body().movies;
                    MovieListStatus.getInstance().notifyMovieUpdated(newMovies);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("WENDY", t.getMessage());
            }
        });
    }

    public void loadMovieDetails(long movieId) {
        Call<Movie> call = getApi().getMovieDetails(movieId, API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(response.isSuccessful()) {
                    Movie movieDetails = response.body();
                    MovieDetailsStatus.getInstance().notifyMovieUpdated(movieDetails);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }

    public void loadReviews(long movieId) {
        Call<ReviewResponse> call = getApi().getReviewList(movieId, API_KEY);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if(response.isSuccessful()) {
                    ArrayList<Review> reviews = response.body().reviews;
                    Log.d("WENDY", "Get response review " + reviews.size());
                    MovieDetailsStatus.getInstance().notifyReviewUpdate(reviews);
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.d("WENDY", t.getMessage());
            }
        });
    }

    public void loadVideos(long movieId) {
        Call<VideoResponse> call = getApi().getVideoList(movieId, API_KEY);
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if(response.isSuccessful()) {
                    ArrayList<Video> videos = response.body().videos;
                    Log.d("WENDY", "Get response video " + videos.size());
                    MovieDetailsStatus.getInstance().notifyVideoUpdate(videos);
                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Log.d("WENDY", t.getMessage());
            }
        });
    }
}