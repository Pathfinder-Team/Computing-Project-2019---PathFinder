package com.example.pathfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btn_org = (Button) findViewById(R.id.btn_org);
        btn_org.setOnClickListener(this);


        Button btn_contact = (Button) findViewById(R.id.btn_contact);
        btn_contact.setOnClickListener(this);

        Button btn_path_finder = (Button) findViewById(R.id.btn_path_finder);
        btn_path_finder.setOnClickListener(this);

        createBuildingTable();
    }
    public void createBuildingTable()
    {
        db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
        db.execSQL("DROP TABLE IF EXISTS building_details");
        db.execSQL("DROP TABLE IF EXISTS map_information");
        db.execSQL("DROP TABLE IF EXISTS map_points");
        db.execSQL("DROP TABLE IF EXISTS special_points");
        db.execSQL("DROP TABLE IF EXISTS org_details");
        db.execSQL("DROP TABLE IF EXISTS map_details");

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

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "building_details(" +
                "building_name varchar);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "map_information(" +
                "map_image varchar);");


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


    public void onClick(View view)
    {
        Intent intent;
        switch(view.getId()) {
            case R.id.btn_org:
                intent = new Intent(this, OrgActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_scan:
                intent = new Intent(this, ScanActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_contact:
                intent = new Intent(this, ContactActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_path_finder:
                intent = new Intent(this, PathFinder.class);
                startActivity(intent);
                break;
        }
    }
}
