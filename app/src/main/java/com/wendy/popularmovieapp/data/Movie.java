package com.wendy.popularmovieapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by wendy on 7/7/2017.
 */

public class Movie implements Parcelable {

    private long id;
    @SerializedName("vote_average")
    private float rating;
    @SerializedName("original_title")
    private String title;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("backdrop_path")
    private String backdrop;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("overview")
    private String synopsis;
    @SerializedName("runtime")
    private String runtime;

    public int isFavorite;

    protected Movie(Parcel in) {
        id = in.readLong();
        rating = in.readFloat();
        title = in.readString();
        poster = in.readString();
        backdrop = in.readString();
        releaseDate = in.readString();
        synopsis = in.readString();
        runtime = in.readString();
        isFavorite = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeFloat(rating);
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(backdrop);
        dest.writeString(releaseDate);
        dest.writeString(synopsis);
        dest.writeString(runtime);
        dest.writeInt(isFavorite);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public long getId() {
        return id;
    }

    public float getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getRuntime() {
        return runtime;
    }

}
