package com.example.happyghost.widgetstudy.recycleviewhelper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.happyghost.widgetstudy.R;

/**
 * @author Zhao Chenping
 * @creat 2017/7/8.
 * @description
 */

public class RecycleViewHolder extends RecyclerView.ViewHolder{


    public final ImageView avatarImageView;
    public final ImageView likeImageView;
    public final ImageView dislikeImageView;

    public RecycleViewHolder(View itemView) {
        super(itemView);
        avatarImageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
        likeImageView = (ImageView) itemView.findViewById(R.id.iv_like);
        dislikeImageView = (ImageView) itemView.findViewById(R.id.iv_dislike);
    }
}
