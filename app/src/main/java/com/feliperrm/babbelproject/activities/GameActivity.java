package com.feliperrm.babbelproject.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.feliperrm.babbelproject.R;
import com.feliperrm.babbelproject.controllers.GameController;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class GameActivity extends BaseActivity {

    BlurView blurView;
    RecyclerView recyclerViewHearts;
    GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarAndNavBarTranslucent(true);
        setContentView(R.layout.activity_game);
        findViews();
        setUpViews();
        setUpGame();
    }

    private void findViews(){
        blurView = (BlurView) findViewById(R.id.blurView);
        recyclerViewHearts = (RecyclerView) findViewById(R.id.recyclerViewHearts);
    }

    private void setUpViews(){
        setUpBlurView();
    }

    private void setUpGame(){
        gameController = new GameController(recyclerViewHearts);
    }

    private void setUpBlurView(){
        final float radius = 1.5f;

        final View decorView = getWindow().getDecorView();
        //Activity's root View. Can also be root View of your layout
        final View rootView = decorView.findViewById(android.R.id.content);
        //set background, if your root layout doesn't have one
        final Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(rootView)
                .windowBackground(windowBackground)
                .blurAlgorithm(new RenderScriptBlur(this, true)) //Preferable algorithm, needs RenderScript support mode enabled
                .blurRadius(radius);
    }
}
