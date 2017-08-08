package com.wendy.popularmovieapp.data;

import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Ignore;
import com.orm.dsl.Table;

/**
 * Created by SRIN on 7/10/2017.
 */

@Table
public class Review {
    @Ignore
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