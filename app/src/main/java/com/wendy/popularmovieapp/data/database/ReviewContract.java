package com.wendy.popularmovieapp.data.database;

import android.net.Uri;
import android.provider.BaseColumns;

import com.wendy.popularmovieapp.Constant;

/**
 * Created by SRIN on 8/8/2017.
 */

public class ReviewContract {

    public static final String PATH_REVIEW = "review";

    public static final class ReviewEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                Constant.BASE_CONTENT_URI.buildUpon()
                        .appendPath(PATH_REVIEW)
                        .build();

        // table and column names
        public static final String TABLE_NAME = "review";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";
    }
}
