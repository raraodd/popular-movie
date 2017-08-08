package com.wendy.popularmovieapp;

import android.net.Uri;

/**
 * Created by wendy on 7/7/2017.
 */

public class Constant {

    public static final String SORT_BY = "sort_by";
    public static final String SORT_BY_TOP_RATED = "top_rated";
    public static final String SORT_BY_POPULARITY = "popular";
    public static final String SORT_BY_FAVORITE = "favorite";
    public static final String SORT_BY_DEFAULT = SORT_BY_POPULARITY;

    public static final int POPULARITY = 0;
    public static final int TOP_RATED = 1;
    public static final int SORT_FAVORITE = 2;

    public static final String MOVIE_ID = "movie_id";

    public static final int UNFAVORITE = 0;
    public static final int FAVORITE = 1;

    public static final String AUTHORITY = "com.wendy.popularmovieapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String SELECTED_DETAIL = "detail_selected";
    public static final int DETAIL_SYNOPSIS = 0;
    public static final int DETAIL_REVIEW = 1;
    public static final int DETAIL_VIDEO = 2;
}