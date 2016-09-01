package com.feliperrm.babbelproject.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.feliperrm.babbelproject.R;
import com.feliperrm.babbelproject.models.WordSet;
import com.feliperrm.babbelproject.utils.Singleton;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {

    private static final long TIME_TO_CHANGE_SCREEN = 1500;
    Subscription subscription;

    ImageView ukFlag, spainFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarAndNavBarTranslucent(true);
        setContentView(R.layout.activity_splash);
        findViews();
        loadJson("words.json");
    }

    private void findViews(){
        ukFlag = (ImageView) findViewById(R.id.uk_flag);
        spainFlag = (ImageView) findViewById(R.id.spain_flag);
    }

    private void loadJson(String fileName){
        Observable.just(fileName).map(new Func1<String, WordSet>() {
            @Override
            public WordSet call(String string) {
                try {
                    InputStream is = getAssets().open(string);

                    int size = is.available();

                    byte[] buffer = new byte[size];

                    is.read(buffer);

                    is.close();

                    String json = new String(buffer, "UTF-8");
                    return new Gson().fromJson(json, WordSet.class);
                }
                catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WordSet>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WordSet words) {
                        Singleton.getSingleton().setWordSet(words);
                        goToMenuActivity();
                    }
                });
    }

    private void goToMenuActivity(){
        Observable<Long> observable = Observable.timer(TIME_TO_CHANGE_SCREEN, TimeUnit.MILLISECONDS);

         subscription = observable
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Intent menu = new Intent(SplashActivity.this, MenuActivity.class);
                        ArrayList<Pair<View, String>> pairs = new ArrayList<>();
                        pairs.add(new Pair<View, String>(ukFlag, ukFlag.getTransitionName()));
                        pairs.add(new Pair<View, String>(spainFlag, spainFlag.getTransitionName()));
                        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs.get(0), pairs.get(1));
                        startActivity(menu, transitionActivityOptions.toBundle());
                    }
                });
    }

    @Override
    protected void onStop() {
        subscription.unsubscribe();
        super.onStop();
        finish(); // So this Activity is not accessed by back pressing when on the MenuActivity
    }
}
