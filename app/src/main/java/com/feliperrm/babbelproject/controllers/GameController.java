package com.feliperrm.babbelproject.controllers;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.feliperrm.babbelproject.adapters.LifeAdapter;

/**
 * Created by felip on 01/09/2016.
 */
public class GameController {

    RecyclerView heartsRecyclerView;
    LifeAdapter lifeAdapter;

    public GameController(RecyclerView heartsRecyclerView) {
        this.heartsRecyclerView = heartsRecyclerView;
        lifeAdapter = new LifeAdapter();
        heartsRecyclerView.setAdapter(lifeAdapter);
        heartsRecyclerView.setLayoutManager(new LinearLayoutManager(heartsRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
    }
}
