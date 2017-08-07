package com.wendy.popularmovieapp.service;

import android.util.Log;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.orm.util.NamingHelper;
import com.wendy.popularmovieapp.BuildConfig;
import com.wendy.popularmovieapp.Constant;
import com.wendy.popularmovieapp.data.database.Review;
import com.wendy.popularmovieapp.data.database.Video;
import com.wendy.popularmovieapp.data.api.Api;
import com.wendy.popularmovieapp.data.database.Movie;
import com.wendy.popularmovieapp.data.api.MovieResponse;
import com.wendy.popularmovieapp.data.api.ReviewResponse;
import com.wendy.popularmovieapp.data.api.VideoResponse;

import java.util.ArrayList;
import java.util.List;

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


    public void loadMovie(final String sortBy) {
        // get from api
        Call<MovieResponse> call = getApi().getMovieList(sortBy, API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.isSuccessful()) {
                    ArrayList<Movie> newMovies = response.body().movies;

                    for(Movie item: newMovies) {
                        item.type = sortBy;
                        SugarRecord.save(item);
                    }

                    MovieListStatus.getInstance().notifyMovieUpdated(sortBy);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("WENDY", t.getMessage());
            }
        });
    }

    public List<Movie> getMovies(String sortBy) {
        String where;
        List<Movie> result;
        if (!sortBy.equals(Constant.SORT_BY_FAVORITE)) {
            where = NamingHelper.toSQLNameDefault("type") + " = ? ";
            result =  SugarRecord.find(Movie.class, where, new String[] {sortBy});
        } else {
            where = NamingHelper.toSQLNameDefault("isFavorite") + " = 1 ";
            result =  SugarRecord.find(Movie.class, where);
        }

        return result;
    }

    public Movie getMovieById(Long movieId) {
        return Select.from(Movie.class)
                .where(Condition.prop(NamingHelper.toSQLNameDefault("id")).eq(movieId))
                .first();
    }

    public void setFavorite(int isFavorite, Long movieId) {
        Movie movie = getMovieById(movieId);
        movie.isFavorite = isFavorite;
        SugarRecord.save(movie);
    }

    public void loadMovieDetails(final long movieId) {
        Call<Movie> call = getApi().getMovieDetails(movieId, API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(response.isSuccessful()) {
                    Movie movieDetails = response.body();

                    Movie movie = getMovieById(movieId);
                    movie.synopsis = movieDetails.synopsis;
                    SugarRecord.save(movie);

                    MovieDetailsStatus.getInstance().notifyMovieUpdated();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }

    public void loadReviews(final long movieId) {
        Call<ReviewResponse> call = getApi().getReviewList(movieId, API_KEY);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if(response.isSuccessful()) {
                    ArrayList<Review> reviews = response.body().reviews;

                    for(Review item: reviews) {
                        item.movieId = movieId;
                        SugarRecord.save(item);
                    }

                    MovieDetailsStatus.getInstance().notifyReviewUpdate();
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.d("WENDY", t.getMessage());
            }
        });
    }

    public List<Review> getReviews(long movieId) {
        String where = NamingHelper.toSQLNameDefault("movieId") + " = " + movieId;
        return SugarRecord.find(Review.class, where);
    }

    public void loadVideos(final long movieId) {
        Call<VideoResponse> call = getApi().getVideoList(movieId, API_KEY);
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if(response.isSuccessful()) {
                    ArrayList<Video> videos = response.body().videos;

                    for(Video item: videos) {
                        item.movieId = movieId;
                        SugarRecord.save(item);
                    }

                    MovieDetailsStatus.getInstance().notifyVideoUpdate();
                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Log.d("WENDY", t.getMessage());
            }
        });
    }

    public List<Video> getVideos(long movieId) {
        String where = NamingHelper.toSQLNameDefault("movieId") + " = " + movieId;
        return SugarRecord.find(Video.class, where);
    }
}