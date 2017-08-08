package com.wendy.popularmovieapp.data.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wendy.popularmovieapp.Constant;

/**
 * Created by SRIN on 8/8/2017.
 */

public class MovieContentProvider extends ContentProvider{

    public static final int MOVIE = 100;
    public static final int REVIEW = 200;
    public static final int VIDEO = 300;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(Constant.AUTHORITY, MovieContract.PATH_MOVIE, MOVIE);
        uriMatcher.addURI(Constant.AUTHORITY, ReviewContract.PATH_REVIEW, REVIEW);
        uriMatcher.addURI(Constant.AUTHORITY, VideoContract.PATH_VIDEO, VIDEO);

        return uriMatcher;
    }

    private DbHelper mDbHelper;

    @Override
    public boolean onCreate() {

        Context context = getContext();
        mDbHelper = new DbHelper(context);

        return true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnUri;
        long id;

        switch (match) {
            case MOVIE:
                id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if(id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            case REVIEW:
                id = db.insert(ReviewContract.ReviewEntry.TABLE_NAME, null, values);
                if(id > 0) {
                    returnUri = ContentUris.withAppendedId(ReviewContract.ReviewEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            case VIDEO:
                id = db.insert(VideoContract.VideoEntry.TABLE_NAME, null, values);
                if(id > 0) {
                    returnUri = ContentUris.withAppendedId(VideoContract.VideoEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor returnCursor;

        switch (match) {
            case MOVIE:
                returnCursor = db.query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case REVIEW:
                returnCursor = db.query(ReviewContract.ReviewEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case VIDEO:
                returnCursor = db.query(VideoContract.VideoEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int tasksUpdated;

        int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIE:
                tasksUpdated = mDbHelper.getWritableDatabase().update(
                        MovieContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (tasksUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return tasksUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
