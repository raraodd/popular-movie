package com.wendy.popularmovieapp.data.database;

import android.net.Uri;
import android.provider.BaseColumns;

import com.wendy.popularmovieapp.Constant;

/**
 * Created by SRIN on 8/8/2017.
 */

public class VideoContract {

    public static final String PATH_VIDEO = "video";

    public static final class VideoEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                Constant.BASE_CONTENT_URI.buildUpon()
                        .appendPath(PATH_VIDEO)
                        .build();

        // table and column names
        public static final String TABLE_NAME = "video";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_URL_KEY = "url_key";
    }
}
