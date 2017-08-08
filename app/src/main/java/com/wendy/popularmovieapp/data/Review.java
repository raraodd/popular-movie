package com.wendy.popularmovieapp.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SRIN on 7/10/2017.
 */

public class Review {
    private String id;

    public long reviewId;
    public long movieId;

    @SerializedName("author")
    public String author;
    @SerializedName("content")
    public String content;
    @SerializedName("url")
    public String url;

    public Review() {
    }

    public String getId() {
        return id;
    }
}