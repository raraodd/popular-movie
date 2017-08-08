package com.wendy.popularmovieapp.data.api;

import com.google.gson.annotations.SerializedName;
import com.wendy.popularmovieapp.data.Video;

import java.util.ArrayList;

/**
 * Created by SRIN on 7/10/2017.
 */

public class VideoResponse {
    @SerializedName("results")
    public ArrayList<Video> videos;
}
