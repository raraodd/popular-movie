package com.wendy.popularmovieapp.feature.moviedetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wendy.popularmovieapp.Constant;
import com.wendy.popularmovieapp.R;
import com.wendy.popularmovieapp.data.Movie;
import com.wendy.popularmovieapp.data.Review;
import com.wendy.popularmovieapp.data.Video;
import com.wendy.popularmovieapp.service.PopularMovieApp;
import com.wendy.popularmovieapp.utils.Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsView, View.OnClickListener
{
    @BindView(R.id.tv_title_and_year_details)
    TextView tvTitleYear;
    @BindView(R.id.iv_poster_details)
    ImageView ivPoster;
    @BindView((R.id.tv_runtime_details))
    TextView tvRuntime;
    @BindView(R.id.tv_release_date_details)
    TextView tvReleaseDate;
    @BindView(R.id.tv_rating_details)
    TextView tvRating;
    @BindView(R.id.iv_favorite_detail)
    ImageView ivFavorite;
    @BindView(R.id.iv_unfavorite_detail)
    ImageView ivUnfavorite;
    @BindView(R.id.tv_synopsis)
    TextView tvSynopsis;
    @BindView(R.id.tv_review)
    TextView tvReview;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.tv_synopsis_detail)
    TextView tvSynopsisDetail;
    @BindView(R.id.rv_review_list)
    RecyclerView rvReview;


    private MovieDetailsViewModel viewModel;
    private Context mContext;
    private Movie mMovie;
    private ReviewListAdapter adapter;
    private int selectedDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mMovie = getIntent().getParcelableExtra(Constant.EXTRA_MOVIE);

        viewModel = new MovieDetailsViewModel(this, mMovie);
        mContext = getBaseContext();

        ButterKnife.bind(this);
        setToolBar();
        setContent();
        setLayoutManager();

        ivFavorite.setOnClickListener(this);
        ivUnfavorite.setOnClickListener(this);
        tvSynopsis.setOnClickListener(this);
        tvReview.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        viewModel.attach();
        super.onResume();
        loadMovieDetails(viewModel.movie.getId());
        loadReviews(viewModel.movie.getId());
        loadVideos(viewModel.movie.getId());

        if(selectedDetail == Constant.DETAIL_SYNOPSIS) {
            onSynopsisSelected();
        }
        else if(selectedDetail == Constant.DETAIL_REVIEW) {
            onReviewSelected();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(Constant.SELECTED_DETAIL, selectedDetail);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState == null) return;
        selectedDetail = savedInstanceState.getInt(Constant.SELECTED_DETAIL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.detach();
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent().putExtra(Constant.EXTRA_MOVIE_FAVORITE, (Parcelable) viewModel.movie);
        Log.d("WENDY", "result " + viewModel.movie.isFavorite);

        if (getParent() == null) {
            setResult(Activity.RESULT_OK, intent);
        }
        else {
            getParent().setResult(Activity.RESULT_OK, intent);
        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setToolBar() {
        getSupportActionBar().setTitle(viewModel.getTitleAndYear());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setContent() {
        tvTitleYear.setText(viewModel.getTitleAndYear());
        tvSynopsisDetail.setText(viewModel.movie.getSynopsis());
        Picasso.with(mContext)
                .load(PopularMovieApp.API_POSTER_HEADER_LARGE + viewModel.movie.getPoster())
                .placeholder(R.drawable.ic_sentiment_dissatisfied)
                .into(ivPoster);
    }

    private void setLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvReview.setLayoutManager(layoutManager);
        rvReview.setHasFixedSize(true);
    }

    private void setAdapter(ArrayList<Review> reviews) {
        adapter = new ReviewListAdapter(mContext, reviews);
        rvReview.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent = getIntent().putExtra(Constant.EXTRA_MOVIE_FAVORITE, (Parcelable) viewModel.movie);
            Log.d("WENDY", "result " + viewModel.movie.isFavorite);

            if (getParent() == null) {
                setResult(Activity.RESULT_OK, intent);
            }
            else {
                getParent().setResult(Activity.RESULT_OK, intent);
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadMovieDetails(long movieId) {
        viewModel.loadMovieDetails(movieId);
    }

    @Override
    public void loadReviews(long movieId) {
        viewModel.loadReviews(movieId);
    }

    @Override
    public void loadVideos(long movieId) {
        viewModel.loadVideos(movieId);
    }

    @Override
    public void updateView(Movie movie) {
        tvRuntime.setText(Utils.convertRuntime(viewModel.movie.getRuntime()));
        tvReleaseDate.setText(Utils.convertDateToString(viewModel.movie.getReleaseDate()));
        tvRating.setText(String.valueOf(viewModel.movie.getRating()));
        Log.d("WENDY", movie.toString());
    }

    @Override
    public void updateReview(ArrayList<Review> reviews) {
        setAdapter(viewModel.reviews);
    }

    @Override
    public void updateVideo(ArrayList<Video> videos) {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_favorite_detail) {
            ivFavorite.setVisibility(View.GONE);
            ivUnfavorite.setVisibility(View.VISIBLE);
            viewModel.movie.isFavorite =0;
        }
        if(view.getId() == R.id.iv_unfavorite_detail) {
            ivUnfavorite.setVisibility(View.GONE);
            ivFavorite.setVisibility(View.VISIBLE);
            viewModel.movie.isFavorite = 1;
        }
        if(view.getId() == R.id.tv_synopsis) {
            onSynopsisSelected();
            selectedDetail = Constant.DETAIL_SYNOPSIS;
        }
        if(view.getId() == R.id.tv_review) {
            onReviewSelected();
            selectedDetail = Constant.DETAIL_REVIEW;
            adapter.notifyDataSetChanged();
        }
        if(view.getId() == R.id.tv_video) {

        }
        Log.d("WENDY", "FAVORITE " + viewModel.movie.isFavorite);
    }

    private void onSynopsisSelected() {
        tvSynopsisDetail.setVisibility(View.VISIBLE);
        rvReview.setVisibility(View.GONE);
    }

    private void onReviewSelected() {
        rvReview.setVisibility(View.VISIBLE);
        tvSynopsisDetail.setVisibility(View.GONE);
    }
}