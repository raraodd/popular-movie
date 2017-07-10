package com.wendy.popularmovieapp.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SRIN on 7/10/2017.
 */

public class Review {
    private String id;
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;
    @SerializedName("url")
    private String url;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}