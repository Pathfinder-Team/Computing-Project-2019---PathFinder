package com.example.pathfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    public void onPause () {

        super.onPause();

        TextView splash = (TextView) findViewById(R.id.splashTop);
        ImageView splash_image = (ImageView) findViewById(R.id.splashImage);
        TextView version = (TextView) findViewById(R.id.version);

        splash.clearAnimation();
        splash_image.clearAnimation();
        version.clearAnimation();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView splash = (TextView) findViewById(R.id.splashTop);
        ImageView splash_image = (ImageView) findViewById(R.id.splashImage);
        TextView version = (TextView) findViewById(R.id.version);

        Animation fade1 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fade2 = AnimationUtils.loadAnimation(this, R.anim.fade_in2);
        Animation spinin = AnimationUtils.loadAnimation(this, R.anim.custom_anim);

        fade2. setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        splash.startAnimation(fade1);
        splash_image.startAnimation(spinin);
        version.startAnimation(fade2);
    }
}
