package com.wendy.popularmovieapp.data.api;

import com.google.gson.annotations.SerializedName;
import com.wendy.popularmovieapp.data.Movie;

import java.util.ArrayList;

/**
 * Created by wendy on 7/7/2017.
 */

public class MovieResponse {
    public long page;
    public long total_results;
    public long total_pages;
    @SerializedName("results")
    public ArrayList<Movie> movies;
}
