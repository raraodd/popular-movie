package com.wendy.popularmovieapp.data.database;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SRIN on 7/10/2017.
 */

public class Video {
    private String id;
    @SerializedName("name")
    private String title;
    @SerializedName("key")
    private String urlKey;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrlKey() {
        return urlKey;
    }
}
