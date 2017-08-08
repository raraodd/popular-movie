package com.wendy.popularmovieapp.feature.moviedetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wendy.popularmovieapp.R;
import com.wendy.popularmovieapp.data.Video;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wendy on 8/7/17.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder> {

    private List<Video> videos;
    private Context mContext;
    private final ListVideoClickListener mOnClickListener;

    public VideoListAdapter(Context context, List<Video> videos, ListVideoClickListener listener) {
        this.mContext = context;
        this.videos = videos;
        this.mOnClickListener = listener;
    }

    public interface ListVideoClickListener {
        void onVideoListClick(int clickedVideoIndex);
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        int videoIdItem = R.layout.video_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(videoIdItem, parent, false);
        VideoViewHolder viewHolder = new VideoViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.bind(video);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_trailer_img) ImageView ivTrailerImage;
        @BindView(R.id.iv_play_button) ImageView ivPlayButton;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ivPlayButton.setOnClickListener(this);
        }

        void bind(Video video) {
            String id = video.urlKey;
            String thumbnailURL = "http://img.youtube.com/vi/".concat(id).concat("/maxresdefault.jpg");
            Picasso.with(mContext)
                    .load(thumbnailURL)
                    .placeholder(R.drawable.ic_sentiment_dissatisfied)
                    .into(ivTrailerImage);

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onVideoListClick(clickedPosition);
        }
    }
}
