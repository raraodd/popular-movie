package com.wendy.popularmovieapp.service;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.wendy.popularmovieapp.App;
import com.wendy.popularmovieapp.BuildConfig;
import com.wendy.popularmovieapp.Constant;
import com.wendy.popularmovieapp.data.Review;
import com.wendy.popularmovieapp.data.Video;
import com.wendy.popularmovieapp.data.api.Api;
import com.wendy.popularmovieapp.data.Movie;
import com.wendy.popularmovieapp.data.api.MovieResponse;
import com.wendy.popularmovieapp.data.api.ReviewResponse;
import com.wendy.popularmovieapp.data.api.VideoResponse;
import com.wendy.popularmovieapp.data.database.MovieContentProvider;
import com.wendy.popularmovieapp.data.database.MovieContract;
import com.wendy.popularmovieapp.data.database.ReviewContract;
import com.wendy.popularmovieapp.data.database.VideoContract;
import com.wendy.popularmovieapp.utils.*;

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
                        item.runtime = item.runtime == null ? null : item.runtime+"";

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(MovieContract.MovieEntry._ID, item.getId());
                        contentValues.put(MovieContract.MovieEntry.COLUMN_RATING, item.rating);
                        contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, item.title);
                        contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER, item.poster);
                        contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, item.releaseDate);
                        contentValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP, item.backdrop);
                        contentValues.put(MovieContract.MovieEntry.COLUMN_SYNOPSIS, item.synopsis);
                        contentValues.put(MovieContract.MovieEntry.COLUMN_RUNTIME, item.runtime);
                        contentValues.put(MovieContract.MovieEntry.COLUMN_TYPE, item.type);
                        contentValues.put(MovieContract.MovieEntry.COLUMN_FAVORITE, item.isFavorite);

                        Uri uri = App.getContext().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
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
        Cursor cursor;
        String[] mSelectionArgs;
        String mSelection;
        if (!sortBy.equals(Constant.SORT_BY_FAVORITE)) {
            mSelection = "type = ?";
            mSelectionArgs = new String[] {sortBy};
        } else {
            mSelection = "is_favorite = ?";
            mSelectionArgs = new String[] {"1"};
        }

        cursor = App.getContext().getContentResolver()
                .query(MovieContract.MovieEntry.CONTENT_URI,
                        null,
                        mSelection,
                        mSelectionArgs,
                        MovieContract.MovieEntry._ID);
        List<Movie> resultCursor = Utils.convertMovieCursorToList(cursor);

        return resultCursor;
    }

    public Movie getMovieById(Long movieId) {
        Movie result;
        String mSelection = "_id = " + movieId;
        Cursor cursor = App.getContext().getContentResolver()
                .query(MovieContract.MovieEntry.CONTENT_URI,
                        null,
                        mSelection,
                        null,
                        MovieContract.MovieEntry._ID);
        result = Utils.convertMovieCursorToList(cursor).get(0);
        return result;
    }

    public void setFavorite(int isFavorite, Long movieId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.COLUMN_FAVORITE, isFavorite);
        String selection = "_id = " + movieId;

        App.getContext().getContentResolver().update(MovieContract.MovieEntry.CONTENT_URI,
                contentValues, selection, null);
    }

    public void loadMovieDetails(final long movieId) {
        Call<Movie> call = getApi().getMovieDetails(movieId, API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(response.isSuccessful()) {
                    Movie movieDetails = response.body();

                    Movie movie = getMovieById(movieId);
                    movie.runtime = movieDetails.runtime;

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MovieContract.MovieEntry.COLUMN_RUNTIME, movie.runtime);
                    String selection = "_id = " + movieId;

                    App.getContext().getContentResolver().update(MovieContract.MovieEntry.CONTENT_URI,
                            contentValues, selection, null);

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

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(ReviewContract.ReviewEntry.COLUMN_MOVIE_ID, item.movieId);
                        contentValues.put(ReviewContract.ReviewEntry.COLUMN_AUTHOR, item.author);
                        contentValues.put(ReviewContract.ReviewEntry.COLUMN_CONTENT, item.content);

                        App.getContext().getContentResolver().insert(ReviewContract.ReviewEntry.CONTENT_URI, contentValues);
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
        Cursor cursor;
        String mSelection = "movie_id = " + movieId;
        cursor = App.getContext().getContentResolver()
                .query(ReviewContract.ReviewEntry.CONTENT_URI,
                        null,
                        mSelection,
                        null,
                        ReviewContract.ReviewEntry._ID);
        List<Review> resultCursor = Utils.convertReviewCursorToList(cursor);

        return resultCursor;
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

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(VideoContract.VideoEntry.COLUMN_MOVIE_ID, item.movieId);
                        contentValues.put(VideoContract.VideoEntry.COLUMN_TITLE, item.title);
                        contentValues.put(VideoContract.VideoEntry.COLUMN_URL_KEY, item.urlKey);

                        App.getContext().getContentResolver().insert(VideoContract.VideoEntry.CONTENT_URI, contentValues);
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
        Cursor cursor;
        String mSelection = "movie_id = " + movieId;
        cursor = App.getContext().getContentResolver()
                .query(VideoContract.VideoEntry.CONTENT_URI,
                        null,
                        mSelection,
                        null,
                        ReviewContract.ReviewEntry._ID);
        List<Video> resultCursor = Utils.convertVideoCursorToList(cursor);

        return resultCursor;
    }
}