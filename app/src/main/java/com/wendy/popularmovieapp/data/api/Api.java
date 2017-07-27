package com.wendy.popularmovieapp.data.api;

import com.wendy.popularmovieapp.data.database.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by wendy on 7/7/2017.
 */

public interface Api {

    @GET("movie/{sort_by}")
    Call<MovieResponse> getMovieList(@Path("sort_by") String sortBy, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetails(@Path("movie_id") long movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResponse> getReviewList(@Path("movie_id") long movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<VideoResponse> getVideoList(@Path("movie_id") long movieId, @Query("api_key") String apiKey);
}