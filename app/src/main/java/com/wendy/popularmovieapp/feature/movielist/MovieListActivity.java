package com.wendy.popularmovieapp.feature.movielist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.wendy.popularmovieapp.Constant;
import com.wendy.popularmovieapp.R;
import com.wendy.popularmovieapp.data.Movie;
import com.wendy.popularmovieapp.feature.moviedetail.MovieDetailsActivity;
import com.wendy.popularmovieapp.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListActivity extends AppCompatActivity
        implements MovieListView, MovieListAdapter.ListMovieClickListener {

    @BindView(R.id.rv_movie_list)
    RecyclerView rvMovieList;
    @BindView(R.id.no_data)
    View mNoData;

    private MovieListViewModel viewModel;
    private Context mContext;
    private MovieListAdapter adapter;
    private String selectedSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        viewModel = new MovieListViewModel(this);
        mContext = getBaseContext();
        ButterKnife.bind(this);
        setLayoutManager();

        selectedSort = Constant.SORT_BY_DEFAULT;
    }

    @Override
    protected void onResume() {
        viewModel.attach();
        super.onResume();
        Log.d("WENDY", "OnResume " + selectedSort);
        loadMovie(selectedSort);
    }

    @Override
    protected void onPause() {
        viewModel.detach();
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("WENDY", "OnSaveInstance " + selectedSort);
        outState.putString(Constant.SORT_BY, selectedSort);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState == null) return;
        selectedSort = savedInstanceState.getString(Constant.SORT_BY);
        Log.d("WENDY", "OnRestoreInstance " + selectedSort);

        invalidateOptionsMenu();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == Constant.CODE_MOVIE_FAVORITE) {
            Movie movie = data.getParcelableExtra(Constant.EXTRA_MOVIE_FAVORITE);
            Log.d("WENDY", "get result " + movie.isFavorite);
            if(movie != null && movie.isFavorite == 1) {
                viewModel.favMovies.add(movie);
            } else {
                viewModel.favMovies.remove(movie);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        if(selectedSort.equals(Constant.SORT_BY_POPULARITY)) {
            menu.getItem(Constant.POPULARITY).setChecked(true);
        }
        if(selectedSort.equals(Constant.SORT_BY_TOP_RATED)) {
            menu.getItem(Constant.TOP_RATED).setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.item_sort_by_popularity) {
            loadMovie(Constant.SORT_BY_POPULARITY);
            item.setChecked(true);
            selectedSort = Constant.SORT_BY_POPULARITY;
            return true;
        }
        if(item.getItemId() == R.id.item_sort_by_top_rated) {
            loadMovie(Constant.SORT_BY_TOP_RATED);
            item.setChecked(true);
            selectedSort = Constant.SORT_BY_TOP_RATED;
            return true;
        }
//        if(item.getItemId() == R.id.item_sort_by_favorite) {
//            item.setChecked(true);
//            viewModel.movies.clear();
//            updateMovie(viewModel.movies);
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadMovie(String sortBy) {
        viewModel.loadMovie(sortBy);
    }

    @Override
    public void updateMovie(ArrayList<Movie> newMovies) {
        if(newMovies.size() != 0) {
            mNoData.setVisibility(View.GONE);
            rvMovieList.setVisibility(View.VISIBLE);
            setAdapter(newMovies);
        } else {
            mNoData.setVisibility(View.VISIBLE);
            rvMovieList.setVisibility(View.GONE);
        }
    }

    public void setLayoutManager() {
        int numCell = Utils.calculateNoOfColumns(mContext);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, numCell);
        rvMovieList.setLayoutManager(layoutManager);
        rvMovieList.setHasFixedSize(true);
    }

    public void setAdapter(ArrayList<Movie> movies) {
        adapter = new MovieListAdapter(mContext, movies, this);
        rvMovieList.setAdapter(adapter);
    }

    @Override
    public void onMovieListClick(int clickedMovieIndex) {
        Movie movie = viewModel.movies.get(clickedMovieIndex);

        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(Constant.EXTRA_MOVIE, (Parcelable) movie);

        startActivityForResult(intent, Constant.CODE_MOVIE_FAVORITE);
    }
}