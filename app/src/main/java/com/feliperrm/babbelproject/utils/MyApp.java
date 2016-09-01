package com.feliperrm.babbelproject.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by felip on 31/08/2016.
 */
public class MyApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext(); // Static context for some useful methods that don't require an Activity as a context (for example, shared preferences).
    }

    public static Context getContext() {
        return context;
    }

}
