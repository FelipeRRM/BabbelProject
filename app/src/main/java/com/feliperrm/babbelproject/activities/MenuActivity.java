package com.feliperrm.babbelproject.activities;

import android.os.Bundle;
import android.transition.Transition;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.feliperrm.babbelproject.R;
import com.feliperrm.babbelproject.utils.Util;

public class MenuActivity extends BaseActivity {

    ImageView backgroundImage;
    FrameLayout backgroudOpacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarAndNavBarTranslucent(true);
        setContentView(R.layout.activity_menu);
        findViews();
        personalizeEnterTransition();
    }

    private void findViews(){
        backgroundImage = (ImageView) findViewById(R.id.bgImgView);
        backgroudOpacity = (FrameLayout) findViewById(R.id.bgFrameForOpacity);
    }

    private void personalizeEnterTransition(){
        getWindow().setEnterTransition(Util.makeEnterTransition(backgroudOpacity, backgroundImage));
        Transition sharedElementEnterTransition = getWindow().getSharedElementEnterTransition();
        sharedElementEnterTransition.setDuration(1500);
        sharedElementEnterTransition.setInterpolator(new DecelerateInterpolator());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
