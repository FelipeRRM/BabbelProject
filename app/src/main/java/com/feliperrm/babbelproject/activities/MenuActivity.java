package com.feliperrm.babbelproject.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.feliperrm.babbelproject.R;
import com.feliperrm.babbelproject.utils.Singleton;
import com.feliperrm.babbelproject.utils.Util;

public class MenuActivity extends BaseActivity {

    FrameLayout backgroudOpacity;
    ImageView ukFlag, spainFlag, ukBg, spainBg;
    TextView titleTxt;
    Button play, tutorial, leaderboard, about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarAndNavBarTranslucent(true);
        setContentView(R.layout.activity_menu);
        findViews();
        personalizeEnterTransition();
        setUpViews();
    }

    private void findViews(){
        backgroudOpacity = (FrameLayout) findViewById(R.id.bgFrameForOpacity);
        ukFlag = (ImageView) findViewById(R.id.uk_flag);
        spainFlag = (ImageView) findViewById(R.id.spain_flag);
        titleTxt = (TextView) findViewById(R.id.titleTxt);
        play = (Button) findViewById(R.id.btnPlay);
        tutorial = (Button) findViewById(R.id.btnTutorial);
        leaderboard = (Button) findViewById(R.id.btnLeaderboards);
        about = (Button) findViewById(R.id.btnAbout);
        ukBg = (ImageView) findViewById(R.id.bgImgViewUk);
        spainBg = (ImageView) findViewById(R.id.bgImgViewSpain);
    }

    private void setUpViews(){
        setUpBackgroundAnimation();
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Singleton.getSingleton().isTutorialSeen()) {
                    Intent play = new Intent(MenuActivity.this, GameActivity.class);
                    startActivity(play);
                }
                else{
                    Intent tut = new Intent(MenuActivity.this, TutorialActivity.class);
                    startActivity(tut);
                }
            }
        });

        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tut = new Intent(MenuActivity.this, TutorialActivity.class);
                startActivity(tut);
            }
        });
    }

    private void setUpBackgroundAnimation(){
        hideLondon();
    }

    private void hideLondon(){
        ukBg.animate().alpha(0f).setDuration(2000).setInterpolator(new AccelerateDecelerateInterpolator()).setStartDelay(5000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                showLondon();
            }
        }).start();
    }

    private void showLondon(){
        ukBg.animate().alpha(1f).setDuration(2000).setInterpolator(new AccelerateDecelerateInterpolator()).setStartDelay(5000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                hideLondon();
            }
        }).start();
    }

    private void personalizeEnterTransition(){
        getWindow().setEnterTransition(Util.makeEnterTransition(backgroudOpacity, spainBg, ukBg));
        Transition sharedElementEnterTransition = getWindow().getSharedElementEnterTransition();
        sharedElementEnterTransition.setDuration(1000);
        sharedElementEnterTransition.setInterpolator(new DecelerateInterpolator());
        titleTxt.setTranslationY(-200);
        titleTxt.setAlpha(0f);
        titleTxt.setScaleX(0.8f);
        titleTxt.setScaleY(0.8f);
        play.setVisibility(View.INVISIBLE);
        tutorial.setVisibility(View.INVISIBLE);
        leaderboard.setVisibility(View.INVISIBLE);
        about.setVisibility(View.INVISIBLE);
        sharedElementEnterTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                titleTxt.animate().alpha(1f).translationY(0f).scaleY(1f).scaleX(1f).setInterpolator(new DecelerateInterpolator()).setDuration(1000).start();
                enterFromRight(play,550);
                enterFromLeft(tutorial,700);
                enterFromRight(leaderboard, 850);
                enterFromLeft(about, 1000);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void enterFromRight(View v, int delay){
        v.setTranslationX(500);
        v.setAlpha(0f);
        v.setVisibility(View.VISIBLE);
        v.animate().translationX(0).alpha(1f).setInterpolator(new DecelerateInterpolator()).setStartDelay(delay).setDuration(1000).start();
    }

    private void enterFromLeft(View v, int delay){
        v.setTranslationX(-500);
        v.setAlpha(0f);
        v.setVisibility(View.VISIBLE);
        v.animate().translationX(0).alpha(1f).setInterpolator(new DecelerateInterpolator()).setStartDelay(delay).setDuration(1000).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
