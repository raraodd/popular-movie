package com.wendy.popularmovieapp.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 7/25/17.
 */

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie")
    public List<Movie> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertMovies(Movie... movies);

}
