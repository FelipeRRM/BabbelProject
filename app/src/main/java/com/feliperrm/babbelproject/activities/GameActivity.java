package com.feliperrm.babbelproject.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feliperrm.babbelproject.R;
import com.feliperrm.babbelproject.controllers.GameController;
import com.feliperrm.babbelproject.utils.Singleton;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class GameActivity extends BaseActivity {

    BlurView blurView;
    RecyclerView recyclerViewHearts;
    GameController gameController;
    TextView currentWord;
    FrameLayout bgNewWord;
    LinearLayout layoutForCurrentWord;
    TextView scoreNumber;
    TextView txtFalling;
    ImageView positiveButton, negativeButton;
    TextView txtHighScore, txtEndScore;
    Button playAgain, goBackMenu;
    LinearLayout gameOverLayout;

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
        currentWord = (TextView) findViewById(R.id.textCurrentWord);
        bgNewWord = (FrameLayout) findViewById(R.id.backgroundForNewWord);
        layoutForCurrentWord = (LinearLayout) findViewById(R.id.layoutForCurrentWord);
        scoreNumber = (TextView) findViewById(R.id.txtScoreNumber);
        txtFalling = (TextView) findViewById(R.id.txtFalling);
        positiveButton = (ImageView) findViewById(R.id.positiveButton);
        negativeButton = (ImageView) findViewById(R.id.negativeButton);
        txtHighScore = (TextView) findViewById(R.id.txtHighScore);
        txtEndScore = (TextView) findViewById(R.id.txtEndScore);
        playAgain = (Button) findViewById(R.id.btnPlayAgain);
        goBackMenu = (Button) findViewById(R.id.btnGoMenu);
        gameOverLayout = (LinearLayout) findViewById(R.id.layoutGameOver);
    }

    private void setUpViews(){
        setUpBlurView();

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameController.positveClicked();
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameController.negativeClicked();
            }
        });

        gameOverLayout.setVisibility(View.INVISIBLE);
        gameOverLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                gameOverLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                gameOverLayout.setTranslationX(-gameOverLayout.getWidth()- 10);
                return true;
            }
        });

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameOverLayout.animate().translationX(-gameOverLayout.getWidth() - 10).setDuration(750).setInterpolator(new AccelerateInterpolator()).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        scoreNumber.setText(String.valueOf(0));
                        gameController.restart();
                    }
                }).start();
            }
        });

        goBackMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setUpGame(){
        gameController = new GameController(GameActivity.this, recyclerViewHearts, bgNewWord, currentWord, layoutForCurrentWord, scoreNumber, txtFalling);
    }

    private void setUpBlurView(){
        final float radius = 1.5f;

        final View decorView = getWindow().getDecorView();
        final Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(findViewById(R.id.blurParent))
                .windowBackground(windowBackground)
                .blurAlgorithm(new RenderScriptBlur(this, true))
                .blurRadius(radius);
    }

    @Override
    protected void onPause() {
        gameController.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        gameController.destroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameController.resume();
    }

    public void gameOver(int score){
        txtEndScore.setText(getString(R.string.you_scored)+" "+score+" "+getString(R.string.points)+"!");
        int highScore = Singleton.getSingleton().getHighScore();
        if(score>highScore){
            Singleton.getSingleton().setHighScore(score);
            txtHighScore.setText(getString(R.string.new_high_score));
        }
        else{
            txtHighScore.setText(getString(R.string.high_score_is) + " " + highScore + " " + getString(R.string.points));
        }
        gameOverLayout.setVisibility(View.VISIBLE);
        gameOverLayout.animate().translationX(0).setDuration(750).setInterpolator(new DecelerateInterpolator()).setListener(null).start();
    }
}
