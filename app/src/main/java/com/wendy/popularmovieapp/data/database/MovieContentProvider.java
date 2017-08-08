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
    public static final int MOVIE_WITH_ID = 101;
    public static final int MOVIE_SORT_BY = 102;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(Constant.AUTHORITY, MovieContract.PATH_MOVIE, MOVIE);
        uriMatcher.addURI(Constant.AUTHORITY, MovieContract.PATH_MOVIE + "/#", MOVIE_WITH_ID);

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

        switch (match) {
            case MOVIE:
                long id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if(id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
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
            case MOVIE_SORT_BY:
                String type = uri.getPathSegments().get(9);
                String mSelection = "type = ?";
                String[] mSelectionArgs = new String[] {type};

                returnCursor = db.query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
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
