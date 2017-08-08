package com.wendy.popularmovieapp.feature.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity implements
        MovieDetailsView, View.OnClickListener, VideoListAdapter.ListVideoClickListener
{
    @BindView(R.id.tv_title_and_year_details) TextView tvTitleYear;
    @BindView(R.id.iv_poster_details) ImageView ivPoster;
    @BindView((R.id.tv_runtime_details)) TextView tvRuntime;
    @BindView(R.id.tv_release_date_details) TextView tvReleaseDate;
    @BindView(R.id.tv_rating_details) TextView tvRating;
    @BindView(R.id.iv_favorite_detail) ImageView ivFavorite;
    @BindView(R.id.iv_unfavorite_detail) ImageView ivUnfavorite;
    @BindView(R.id.tv_synopsis) TextView tvSynopsis;
    @BindView(R.id.tv_review) TextView tvReview;
    @BindView(R.id.tv_video) TextView tvVideo;
    @BindView(R.id.tv_synopsis_detail) TextView tvSynopsisDetail;
    @BindView(R.id.rv_review_list) RecyclerView rvReview;
    @BindView(R.id.rv_video_list) RecyclerView rvVideoList;
    @BindView(R.id.no_data_detail) View vNoData;

    private MovieDetailsViewModel viewModel;
    private Context mContext;
    private long movieId;
    private Movie mMovie;
    private ReviewListAdapter reviewListAdapter;
    private VideoListAdapter videoListAdapter;
    private int selectedDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieId = getIntent().getLongExtra(Constant.MOVIE_ID, 0);
        viewModel = new MovieDetailsViewModel(this, movieId);
        mContext = getBaseContext();

        ButterKnife.bind(this);
        setToolBar();
        setContent();
        setLayoutManager();

        ivFavorite.setOnClickListener(this);
        ivUnfavorite.setOnClickListener(this);
        tvSynopsis.setOnClickListener(this);
        tvReview.setOnClickListener(this);
        tvVideo.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        viewModel.attach();
        super.onResume();
        loadMovieDetails();
        loadReviews();
        loadVideos();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_movie, menu);
        return true;
    }


    private void setToolBar() {
        getSupportActionBar().setTitle(viewModel.getTitleAndYear());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setContent() {
        tvRating.setText(String.valueOf(viewModel.movie.rating));
        tvReleaseDate.setText(String.valueOf(viewModel.movie.releaseDate));
        tvTitleYear.setText(viewModel.getTitleAndYear());
        tvSynopsisDetail.setText(viewModel.movie.synopsis);

        if(viewModel.movie.isFavorite == Constant.FAVORITE) {
            setFavoriteVisibility(true);
        } else {
            setFavoriteVisibility(false);
        }

        Picasso.with(mContext)
                .load(PopularMovieApp.API_POSTER_HEADER_LARGE + viewModel.movie.poster)
                .placeholder(R.drawable.ic_sentiment_dissatisfied)
                .into(ivPoster);
    }

    private void setFavoriteVisibility(boolean isFavorite) {
        if(isFavorite) {
            ivUnfavorite.setVisibility(View.GONE);
            ivFavorite.setVisibility(View.VISIBLE);
        } else {
            ivFavorite.setVisibility(View.GONE);
            ivUnfavorite.setVisibility(View.VISIBLE);
        }
    }

    private void setLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvReview.setLayoutManager(layoutManager);
        rvReview.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(mContext);
        rvVideoList.setLayoutManager(layoutManager);
        rvVideoList.setHasFixedSize(true);
    }

    private void setReviewAdapter(List<Review> reviews) {
        reviewListAdapter = new ReviewListAdapter(mContext, reviews);
        rvReview.setAdapter(reviewListAdapter);
    }

    private void setVideoAdapter(List<Video> videos) {
        videoListAdapter = new VideoListAdapter(mContext, videos, this);
        rvVideoList.setAdapter(videoListAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        if(item.getItemId() == R.id.action_share) {
            shareMovie();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadMovieDetails() {
        viewModel.loadMovieDetails();
    }

    @Override
    public void loadReviews() {
        viewModel.loadReviews();
    }

    @Override
    public void loadVideos() {
        viewModel.loadVideos();
    }

    @Override
    public void updateView(Movie movie) {
        tvRuntime.setText(Utils.convertRuntime(viewModel.movie.runtime));
        tvReleaseDate.setText(Utils.convertDateToString(viewModel.movie.releaseDate));
        tvRating.setText(String.valueOf(viewModel.movie.rating));
    }

    @Override
    public void updateReview(List<Review> reviews) {
        setReviewAdapter(viewModel.reviews);
    }

    @Override
    public void updateVideo(List<Video> videos) {
        setVideoAdapter(viewModel.videos);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_favorite_detail) {
            setFavoriteVisibility(false);
            viewModel.setFavorite(Constant.UNFAVORITE);
        }
        if(view.getId() == R.id.iv_unfavorite_detail) {
            setFavoriteVisibility(true);
            viewModel.setFavorite(Constant.FAVORITE);
        }
        if(view.getId() == R.id.tv_synopsis) {
            onSynopsisSelected();
            selectedDetail = Constant.DETAIL_SYNOPSIS;
        }
        if(view.getId() == R.id.tv_review) {
            onReviewSelected();
            selectedDetail = Constant.DETAIL_REVIEW;
            if(viewModel.reviews != null && reviewListAdapter != null) {
                reviewListAdapter.notifyDataSetChanged();
                if(viewModel.reviews.size() == 0) {
                    rvReview.setVisibility(View.GONE);
                    vNoData.setVisibility(View.VISIBLE);
                }
            }
        }
        if(view.getId() == R.id.tv_video) {
            onVideoSelected();
            selectedDetail = Constant.DETAIL_VIDEO;
            if(viewModel.videos != null && videoListAdapter != null) {
                videoListAdapter.notifyDataSetChanged();
                if(viewModel.videos.size() == 0) {
                    rvVideoList.setVisibility(View.GONE);
                    vNoData.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void onSynopsisSelected() {
        tvSynopsisDetail.setVisibility(View.VISIBLE);
        rvReview.setVisibility(View.GONE);
        rvVideoList.setVisibility(View.GONE);
        vNoData.setVisibility(View.GONE);
    }

    private void onReviewSelected() {
        rvReview.setVisibility(View.VISIBLE);
        rvVideoList.setVisibility(View.GONE);
        tvSynopsisDetail.setVisibility(View.GONE);
        vNoData.setVisibility(View.GONE);
    }

    private void onVideoSelected() {
        rvVideoList.setVisibility(View.VISIBLE);
        rvReview.setVisibility(View.GONE);
        tvSynopsisDetail.setVisibility(View.GONE);
        vNoData.setVisibility(View.GONE);
    }

    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void shareMovie() {
        String mimeType = "text/plain";
        String title = viewModel.movie.title;
        String textToShare;
        if(viewModel.videos.size() > 0) {
            textToShare = "Let's checkout new trailer " + viewModel.movie.title + " http://www.youtube.com/watch?v=" + viewModel.videos.get(0).urlKey;
        } else {
            textToShare = "Sorry there is no trailer for this movie";
        }

        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(title)
                .setText(textToShare)
                .startChooser();
    }
    @Override
    public void onVideoListClick(int clickedVideoIndex) {
        Video video = viewModel.videos.get(clickedVideoIndex);

        String urlString = "http://www.youtube.com/watch?v=" + video.urlKey;
        openWebPage(urlString);
    }
}