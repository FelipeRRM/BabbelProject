package com.feliperrm.babbelproject.activities;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Display;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.feliperrm.babbelproject.R;
import com.feliperrm.babbelproject.utils.MyApp;
import com.feliperrm.babbelproject.utils.Util;
import com.mikhaellopez.circularimageview.CircularImageView;

public class AboutActivity extends BaseActivity {

    CircularImageView myImage;
    TextView aboutTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarAndNavBarTranslucent(true);
        setContentView(R.layout.activity_about);
        findViews();
        setUpViews();
    }

    private void findViews(){
        myImage = (CircularImageView) findViewById(R.id.imageAbout);
        aboutTxt = (TextView) findViewById(R.id.textViewAbout);
    }

    private void setUpViews(){
       aboutTxt.setMovementMethod(LinkMovementMethod.getInstance());
        Util.circularReveal(myImage, 0, 0);
    }

}
