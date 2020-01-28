package com.example.pathfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener {


    public static int current_selected_id = 0;
    public static String current_selected_name = "";
    public static int current_selected_map_id = 0;

    public static int selected_destination_id = 0;
    public static String selected_name = "";
    public static int    selected_map_id = 0;
    public ArrayList<Node> getCurrentLocationDetails = null;
    public ArrayList<Node> getNextLocationDetails = null;
    SQLiteDatabase db;
    Setup setup = new Setup();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        /*
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        current_selected = extras.getString("current_selected");
        selected_destination = extras.getString("selected_destination");
        */

        Bundle extras = getIntent().getExtras();

        getCurrentLocationDetails = (ArrayList<Node>) extras.getSerializable("current_selected");
        current_selected_id = getCurrentLocationDetails.get(0).current_point_id;
        current_selected_name = getCurrentLocationDetails.get(0).point_name;
        current_selected_map_id = getCurrentLocationDetails.get(0).maps_map_id;

        getNextLocationDetails = (ArrayList<Node>) extras.getSerializable("selected_destination");
        selected_destination_id = getNextLocationDetails.get(0).current_point_id;
        selected_name = getNextLocationDetails.get(0).point_name;
        selected_map_id = getNextLocationDetails.get(0).maps_map_id;



        try {
            db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
            setup.setUpMap(current_selected_name,selected_name,db);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected void onStart()
    {
        super.onStart();

        setImage();

        TextView mes1 = (TextView)findViewById(R.id.display_current);
        TextView mes2 =  (TextView)findViewById(R.id.display_next);
        TextView mes3 =  (TextView)findViewById(R.id.display_path_information);
        mes1.setText(current_selected_name);
        mes2.setText(selected_name);
        //ArrayList<String > myArray = new ArrayList<>();
        //myArray.add("Straight");

        for(int i = 0; i <  setup.getDirect().size(); i++)
        {
            mes3.append( setup.getDirect().get(i));
            mes3.append("\n");
        }
    }

    public void onClick(View view)
    {
        Intent intent;
        switch(view.getId()) {
            case R.id.btn_scan:
                intent = new Intent(this, ScanActivity.class);
                startActivity(intent);
                break;
        }
    }
    public void setImage()
    {
        ImageView imageView = findViewById(R.id.map_image);

        Bitmap bittymap = OrgActivity.allOrgBuildingDetails.get(0).map_image;
        imageView.setImageBitmap(bittymap);
        System.out.println("Check: "+bittymap);
    }
}
