package com.feliperrm.babbelproject.activities;

import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by felip on 30/08/2016.
 */
public class BaseActivity extends AppCompatActivity {

    protected void setStatusBarAndNavBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
