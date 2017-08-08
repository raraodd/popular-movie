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
                MovieContract.MovieEntry.COLUMN_RATING + " DOUBLE, " +
                MovieContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_POSTER + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_BACKDROP + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_SYNOPSIS + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_RUNTIME + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_TYPE + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_FAVORITE + " TEXT);";

        db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
