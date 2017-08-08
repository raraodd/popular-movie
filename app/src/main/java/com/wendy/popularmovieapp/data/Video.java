package com.wendy.popularmovieapp.data;

import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Ignore;
import com.orm.dsl.Table;

/**
 * Created by SRIN on 7/10/2017.
 */

@Table
public class Video {
    @Ignore
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
