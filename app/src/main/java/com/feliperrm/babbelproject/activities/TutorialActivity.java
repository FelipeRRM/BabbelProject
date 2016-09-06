package com.feliperrm.babbelproject.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.feliperrm.babbelproject.R;
import com.feliperrm.babbelproject.utils.Singleton;

public class TutorialActivity extends BaseActivity {

    Button playGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarAndNavBarTranslucent(true);
        setContentView(R.layout.activity_tutorial);
        findViews();
        setUpViews();
        Singleton.getSingleton().setTutorialSeen(true);
    }

    private void findViews(){
        playGame = (Button) findViewById(R.id.btnPlay);
    }

    private void setUpViews(){
        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent play = new Intent(TutorialActivity.this, GameActivity.class);
                startActivity(play);
                finish();
            }
        });
    }
}
