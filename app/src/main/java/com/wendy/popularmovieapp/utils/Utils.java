package com.wendy.popularmovieapp.utils;

import android.content.Context;
import android.database.Cursor;
import android.util.DisplayMetrics;

import com.wendy.popularmovieapp.data.Movie;
import com.wendy.popularmovieapp.data.Review;
import com.wendy.popularmovieapp.data.Video;
import com.wendy.popularmovieapp.data.database.MovieContract;
import com.wendy.popularmovieapp.data.database.ReviewContract;
import com.wendy.popularmovieapp.data.database.VideoContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wendy on 7/7/2017.
 */

public class Utils {

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        if(noOfColumns < 2) return 2;
        return noOfColumns;
    }

    public static String getYear(String dateString){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = parser.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    public static String convertDateToString(String dateString){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateString);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd MMMM yyyy");
            return newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertRuntime(String duration){
        String timeToDisplay = "";
        if (duration == null || duration.isEmpty())
            return "";
        else {
            int runtime = Integer.parseInt(duration);
            int hours = runtime / 60;
            int min = runtime % 60;
            if(min < 10)
                timeToDisplay = hours + "h 0" + min + "min";
            else
                timeToDisplay = hours + "h " + min + "min";

            return timeToDisplay;
        }
    }

    public static List<Movie> convertMovieCursorToList(Cursor cursor) {
        List<Movie> result = new ArrayList<>();

        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Movie movie = new Movie();
                movie.setId(cursor.getLong(cursor.getColumnIndex(MovieContract.MovieEntry._ID)));
                movie.rating = cursor.getFloat(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING));
                movie.title = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
                movie.poster = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER));
                movie.releaseDate = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE));
                movie.backdrop = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP));
                movie.synopsis = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_SYNOPSIS));
                movie.runtime = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RUNTIME));
                movie.type = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TYPE));
                movie.isFavorite = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_FAVORITE));
                result.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public static List<Review> convertReviewCursorToList(Cursor cursor) {
        List<Review> result = new ArrayList<>();

        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Review review = new Review();
                review.movieId = cursor.getLong(cursor.getColumnIndex(ReviewContract.ReviewEntry.COLUMN_MOVIE_ID));
                review.author = cursor.getString(cursor.getColumnIndex(ReviewContract.ReviewEntry.COLUMN_AUTHOR));
                review.content = cursor.getString(cursor.getColumnIndex(ReviewContract.ReviewEntry.COLUMN_CONTENT));
                result.add(review);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public static List<Video> convertVideoCursorToList(Cursor cursor) {
        List<Video> result = new ArrayList<>();

        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Video video = new Video();
                video.movieId = cursor.getLong(cursor.getColumnIndex(VideoContract.VideoEntry.COLUMN_MOVIE_ID));
                video.title = cursor.getString(cursor.getColumnIndex(VideoContract.VideoEntry.COLUMN_TITLE));
                video.urlKey = cursor.getString(cursor.getColumnIndex(VideoContract.VideoEntry.COLUMN_URL_KEY));
                result.add(video);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

}