package com.wendy.popularmovieapp.data.database;


import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by wendy on 7/7/2017.
 */

@Table
public class Movie {

    private Long id;
    @SerializedName("vote_average")
    public float rating;
    @SerializedName("original_title")
    public String title;
    @SerializedName("poster_path")
    public String poster;
    @SerializedName("release_date")
    public String releaseDate;
    @SerializedName("backdrop_path")
    public String backdrop;
    @SerializedName("overview")
    public String synopsis;
    @SerializedName("runtime")
    public String runtime;
    public String type;
    public int isFavorite;

    public Movie() {
    }

    public Long getId() {
        return id;
    }
}
