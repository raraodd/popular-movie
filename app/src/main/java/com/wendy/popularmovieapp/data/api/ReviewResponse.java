package com.wendy.popularmovieapp.data.api;

import com.google.gson.annotations.SerializedName;
import com.wendy.popularmovieapp.data.Movie;
import com.wendy.popularmovieapp.data.Review;

import java.util.ArrayList;

/**
 * Created by SRIN on 7/10/2017.
 */

public class ReviewResponse {
    public long page;
    public long total_results;
    public long total_pages;
    @SerializedName("results")
    public ArrayList<Review> reviews;
}
