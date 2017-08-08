package com.wendy.popularmovieapp.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SRIN on 7/10/2017.
 */

public class Video {
    private String id;

    public long movieId;

    @SerializedName("name")
    public String title;
    @SerializedName("key")
    public String urlKey;

    public Video () {
    }

    public String getId() {
        return id;
    }
}
