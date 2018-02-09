package com.example.happyghost.widgetstudy.recycleviewhelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.happyghost.widgetstudy.R;

import java.util.List;

/**
 * @author Zhao Chenping
 * @creat 2017/7/8.
 * @description
 */

public class RecycleViewAdapter<T> extends RecyclerView.Adapter<RecycleViewHolder>{
    private final Context context;
    private final List<Integer> list;

    public RecycleViewAdapter(Context context, List<Integer> list){

        this.context = context;
        this.list = list;
    }
    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecycleViewHolder holder = new RecycleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_holder, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        ImageView avatarImageView = ((RecycleViewHolder) holder).avatarImageView;
        avatarImageView.setImageResource(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
