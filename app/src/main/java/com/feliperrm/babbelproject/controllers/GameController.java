package com.feliperrm.babbelproject.controllers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feliperrm.babbelproject.R;
import com.feliperrm.babbelproject.activities.GameActivity;
import com.feliperrm.babbelproject.activities.MenuActivity;
import com.feliperrm.babbelproject.adapters.LifeAdapter;
import com.feliperrm.babbelproject.models.Word;
import com.feliperrm.babbelproject.models.WordSet;
import com.feliperrm.babbelproject.utils.MyApp;
import com.feliperrm.babbelproject.utils.Singleton;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by felip on 01/09/2016.
 */
public class GameController {

    private static final int START_LIVES = 3;
    RecyclerView heartsRecyclerView;
    LifeAdapter lifeAdapter;
    FrameLayout bgNewWord;
    TextView txtCurrentWord;
    LinearLayout layoutForCurrentWord;
    TextView scoreNumber;
    Word currentWord;
    Word fallingWord;
    WordSet wordsPool;
    TextView txtFalling;
    Subscription fallingSubs;
    GameActivity gameActivity;
    int centerWordY;
    int topWordY;

    int fallingWordStartY;

    int screenHeight;

    int score;

    MediaPlayer correctSound;
    MediaPlayer incorrectSound;

    public GameController(GameActivity gameActivity, RecyclerView recyclerViewHearts, FrameLayout bgNewWord, TextView txtCurrent, LinearLayout layoutForCurrent,
                          TextView scoreNumber, TextView txtViewFalling) {
        this.gameActivity = gameActivity;
        this.heartsRecyclerView = recyclerViewHearts;
        heartsRecyclerView.setLayoutManager(new LinearLayoutManager(heartsRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        this.txtFalling = txtViewFalling;
        this.bgNewWord = bgNewWord;
        this.txtCurrentWord = txtCurrent;
        this.layoutForCurrentWord = layoutForCurrent;
        this.scoreNumber = scoreNumber;
        this.wordsPool = new WordSet();
        this.wordsPool.addAll(Singleton.getSingleton().getWordSet());

        txtCurrentWord.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                txtCurrentWord.getViewTreeObserver().removeOnPreDrawListener(this);
                int[] screenLocation = new int[2];
                txtCurrentWord.getLocationOnScreen(screenLocation);
                centerWordY = screenLocation[1];
                return true;
            }
        });
        layoutForCurrentWord.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                layoutForCurrentWord.getViewTreeObserver().removeOnPreDrawListener(this);
                int[] screenLocation = new int[2];
                layoutForCurrentWord.getLocationOnScreen(screenLocation);
                topWordY = screenLocation[1];
                return true;
            }
        });
        txtFalling.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                txtFalling.getViewTreeObserver().removeOnPreDrawListener(this);
                int[] screenLocation = new int[2];
                txtFalling.getLocationOnScreen(screenLocation);
                fallingWordStartY = screenLocation[1];
                return true;
            }
        });

        WindowManager wm = (WindowManager) MyApp.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenHeight = size.y;
        correctSound = MediaPlayer.create(gameActivity, R.raw.correct);
        incorrectSound = MediaPlayer.create(gameActivity, R.raw.wrong);
    }

    public void restart(){
        this.wordsPool.addAll(Singleton.getSingleton().getWordSet());
        txtFalling.setVisibility(View.VISIBLE);
        start();
    }

    public void start(){
        score = 0;
        lifeAdapter = new LifeAdapter(new Integer(START_LIVES));
        heartsRecyclerView.setAdapter(lifeAdapter);
        currentWord = wordsPool.removeRandomWord();
        fallingWord = wordsPool.getFallingWord(currentWord);
        animateNewWord();
    }

    private void animateNewWord(){
        txtFalling.setY(fallingWordStartY);
        txtFalling.setText(fallingWord.getText_spa());
        txtCurrentWord.setText(currentWord.getText_eng());
        bgNewWord.setVisibility(View.GONE);
        bgNewWord.setAlpha(0f);
        txtCurrentWord.setVisibility(View.GONE);
        txtCurrentWord.setY(centerWordY);
        txtCurrentWord.setAlpha(0f);
        txtCurrentWord.setScaleX(0.4f);
        txtCurrentWord.setScaleY(0.4f);

        bgNewWord.setVisibility(View.VISIBLE);
        bgNewWord.animate().alpha(1f).setDuration(1000).setListener(null).start();

        txtCurrentWord.setVisibility(View.VISIBLE);
        txtCurrentWord.animate().setDuration(1000).setStartDelay(150).alpha(1f).scaleX(1f).scaleY(1f).setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        txtCurrentWord.animate()
                                .y(topWordY - txtCurrentWord.getHeight()/5)
                                .scaleX(0.6f).scaleY(0.6f).setInterpolator(new AccelerateDecelerateInterpolator()).setStartDelay(250).setDuration(750).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                initiateCurrentWordFall();
                            }
                        }).start();
                        bgNewWord.animate().alpha(0f).setStartDelay(250).setDuration(750)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        bgNewWord.setVisibility(View.GONE);
                                    }
                                })
                                .start();
                    }
                }).start();

    }

    private void initiateCurrentWordFall(){
        Observable<Long> observable = Observable.interval(40, TimeUnit.MILLISECONDS);
        fallingSubs = observable
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe(new Observer<Long>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if(txtFalling.getY()<=screenHeight) {
                            txtFalling.setY(txtFalling.getY() + 1 + score);
                           // Log.d("Txt Y", String.valueOf(txtFalling.getY()));
                        }
                        else
                            gameActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    wrongAnswerOrWordFeel();
                                }
                            });
                    }
                });
    }

    private void wrongAnswerOrWordFeel(){
        try {
            fallingSubs.unsubscribe();
            if (incorrectSound == null)
                incorrectSound = MediaPlayer.create(gameActivity, R.raw.wrong);
            incorrectSound.start();
            Integer lives = lifeAdapter.getLives() - 1;
            lifeAdapter.setLives(lives);
            lifeAdapter.notifyItemRemoved(lives);
            currentWord = wordsPool.removeRandomWord();
            fallingWord = wordsPool.getFallingWord(currentWord);
            if (lives > 0) {
                animateNewWord();
            } else {
                gameOver();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void correctAnswer(){
        try {
            fallingSubs.unsubscribe();
            if (correctSound == null)
                correctSound = MediaPlayer.create(gameActivity, R.raw.correct);
            correctSound.start();
            currentWord = wordsPool.removeRandomWord();
            fallingWord = wordsPool.getFallingWord(currentWord);
            score++;
            scoreNumber.setText(String.valueOf(score));
            animateNewWord();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void gameOver(){
        txtFalling.setVisibility(View.GONE);
        gameActivity.gameOver(score);
    }

    public void positveClicked(){
        if(fallingSubs!=null && !fallingSubs.isUnsubscribed()){
            if(Word.isTranslationCorrect(currentWord,fallingWord))
                correctAnswer();
            else
                wrongAnswerOrWordFeel();
        }
    }

    public void negativeClicked(){
        if(fallingSubs!=null && !fallingSubs.isUnsubscribed()){
            if(!Word.isTranslationCorrect(currentWord,fallingWord))
                correctAnswer();
            else
                wrongAnswerOrWordFeel();
        }
    }

    public void resume(){
        if(currentWord==null || fallingWord == null)
            start();
        else
            initiateCurrentWordFall();
    }

    public void pause(){
        if(fallingSubs!=null && !fallingSubs.isUnsubscribed())
            fallingSubs.unsubscribe();
    }

    public void destroy(){
        if(fallingSubs!=null && !fallingSubs.isUnsubscribed())
            fallingSubs.unsubscribe();
        if(incorrectSound!=null)
            incorrectSound.release();
        if(correctSound!=null)
            correctSound.release();
    }

}
