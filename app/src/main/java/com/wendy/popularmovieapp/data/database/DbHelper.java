package com.wendy.popularmovieapp.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by SRIN on 8/8/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "popular_movie_app.db";

    private static final int VERSION  = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_TABLE_MOVIE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY, " +
                MovieContract.MovieEntry.COLUMN_RATING + " REAL, " +
                MovieContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_POSTER + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_BACKDROP + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_SYNOPSIS + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_RUNTIME + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_TYPE + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_FAVORITE + " INTEGER);";

        final String CREATE_TABLE_REVIEW = "CREATE TABLE " + ReviewContract.ReviewEntry.TABLE_NAME + " (" +
                ReviewContract.ReviewEntry._ID + " INTEGER PRIMARY KEY, " +
                ReviewContract.ReviewEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                ReviewContract.ReviewEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +
                ReviewContract.ReviewEntry.COLUMN_CONTENT + " TEXT NOT NULL);";

        final String CREATE_TABLE_VIDEO = "CREATE TABLE " + VideoContract.VideoEntry.TABLE_NAME + " (" +
                VideoContract.VideoEntry._ID + " INTEGER PRIMARY KEY, " +
                VideoContract.VideoEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                VideoContract.VideoEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                VideoContract.VideoEntry.COLUMN_URL_KEY + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE_MOVIE);
        db.execSQL(CREATE_TABLE_REVIEW);
        db.execSQL(CREATE_TABLE_VIDEO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + MovieContract.MovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + ReviewContract.ReviewEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + VideoContract.VideoEntry.TABLE_NAME);
        onCreate(db);
    }
}
