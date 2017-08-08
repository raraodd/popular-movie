package com.wendy.popularmovieapp.data.database;

import android.net.Uri;
import android.provider.BaseColumns;

import com.wendy.popularmovieapp.Constant;

/**
 * Created by SRIN on 8/8/2017.
 */

public class MovieContract {

    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                Constant.BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE)
                .build();

        // table and column names
        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_BACKDROP = "backdrop";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_RUNTIME = "runtime";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_FAVORITE = "is_favorite";
    }
}
