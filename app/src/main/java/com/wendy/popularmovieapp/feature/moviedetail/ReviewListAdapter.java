package com.wendy.popularmovieapp.feature.moviedetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wendy.popularmovieapp.R;
import com.wendy.popularmovieapp.data.Review;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wendy on 7/10/17.
 */

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewViewHolder> {

    private List<Review> reviews;
    private Context mContext;

    public ReviewListAdapter(Context context, List<Review> reviews) {
        this.mContext = context;
        this.reviews = reviews;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        int reviewIdItem = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(reviewIdItem, parent, false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name_review) TextView tvName;
        @BindView(R.id.tv_content_review) TextView tvContent;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Review review) {
            tvName.setText(review.author);
            tvContent.setText(review.content);
        }
    }
}
