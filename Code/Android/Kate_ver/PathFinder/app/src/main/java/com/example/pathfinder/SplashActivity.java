package com.example.pathfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    SQLiteDatabase db;
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
        //createBuildingTable();
    }
    public void createBuildingTable()
    {
        db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);

        db.execSQL("DROP TABLE IF EXISTS building_details");
        db.execSQL("DROP TABLE IF EXISTS map_information");

        // main table
        db.execSQL("DROP TABLE IF EXISTS map_points");
        db.execSQL("DROP TABLE IF EXISTS special_points");

        // secondary table
        db.execSQL("DROP TABLE IF EXISTS org_details");
        db.execSQL("DROP TABLE IF EXISTS map_details");

        //
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "org_details(" +
                "organisation_name varchar ," +
                "organisation_address varchar ," +
                "organisation_email varchar," +
                "organisation_mobile varchar," +
                "organisation_building_name varchar);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "map_details(" +
                "map_id int ," +
                "org_name varchar," +
                "org_building varchar," +
                "map_name varchar," +
                "map_comments varchar," +
                "map_image varchar);");
        //

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "building_details(" +
                "building_name varchar);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "map_information(" +
                "map_image varchar);");


        //
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "map_points(" +
                "current_point_id int ," +
                "point_name varchar," +
                "maps_map_id int);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "special_points(" +
                "point_id int ," +
                "point_from_id int," +
                "point_to_id int," +
                "point_weight int," +
                "point_direction varchar);");
    }
}
