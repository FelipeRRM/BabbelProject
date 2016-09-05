package com.feliperrm.babbelproject.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.feliperrm.babbelproject.R;

/**
 * Created by felip on 01/09/2016.
 */
public class LifeAdapter extends RecyclerView.Adapter<LifeAdapter.HeartViewHolder> {

    Integer lives;

    public LifeAdapter(Integer lives) {
        this.lives = lives;
    }

    @Override
    public HeartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_heart, parent, false);
        HeartViewHolder vh = new HeartViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(HeartViewHolder holder, int position) {
        // Probably won't do anything as all the items will look the same
    }

    @Override
    public int getItemCount() {
        return lives;
    }

    public static class HeartViewHolder extends RecyclerView.ViewHolder{

        ImageView heart;

        public HeartViewHolder(View itemView) {
            super(itemView);
            heart = (ImageView) itemView.findViewById(R.id.heartImage);
        }
    }

    public Integer getLives() {
        return lives;
    }

    public void setLives(Integer lives) {
        this.lives = lives;
    }
}
