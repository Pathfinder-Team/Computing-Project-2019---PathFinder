package com.example.pathfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener {


    public static int current_selected_id = 0;
    public static String current_selected_name = "";
    public static int current_selected_map_id = 0;
    public static int selected_destination_id = 0;
    public static String selected_name = "";
    public static int selected_map_id = 0;
    public ArrayList<Node> getCurrentLocationDetails = null;
    public ArrayList<Node> getNextLocationDetails = null;
    ImageView imageView;
    int currentImage = 0;
    SQLiteDatabase db;
    Setup setup = new Setup();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Bundle extras = getIntent().getExtras();

        getCurrentLocationDetails = (ArrayList<Node>) extras.getSerializable("current_selected");
        current_selected_id = getCurrentLocationDetails.get(0).current_point_id;
        current_selected_name = getCurrentLocationDetails.get(0).point_name;
        current_selected_map_id = getCurrentLocationDetails.get(0).maps_map_id;

        getNextLocationDetails = (ArrayList<Node>) extras.getSerializable("selected_destination");
        selected_destination_id = getNextLocationDetails.get(0).current_point_id;
        selected_name = getNextLocationDetails.get(0).point_name;
        selected_map_id = getNextLocationDetails.get(0).maps_map_id;

        db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
        setup.setUpMap(db);
        setInitialImage();
        setImageRotateListener();
    }

    private void setImageRotateListener() {
        final Button buttonRotate = (Button) findViewById(R.id.btn_change_image);
        buttonRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                currentImage++;
                if (currentImage == OrgActivity.allOrgBuildingDetails.size()) {
                    currentImage = 0;
                }
                setCurrentImage();
            }
        });
    }

    private void setInitialImage() {
        setCurrentImage();
    }

    private void setCurrentImage() {
        imageView = findViewById(R.id.map_image);
        Bitmap bittymap = OrgActivity.allOrgBuildingDetails.get(currentImage).map_image;
        imageView.setImageBitmap(bittymap);
    }

    protected void onStart()
    {
        super.onStart();

        TextView mes1 = (TextView)findViewById(R.id.display_current);
        TextView mes2 =  (TextView)findViewById(R.id.display_next);
        TextView mes3 =  (TextView)findViewById(R.id.display_path_information);
        mes1.setText(current_selected_name);
        mes2.setText(selected_name);
        ArrayList<Node> foundPointNames = new ArrayList<>();
        foundPointNames = findPointNames(foundPointNames);
        for(int i = 0; i <  setup.getDirect().size(); i++) {
            String combo = "Location: " + foundPointNames.get(i).fromPointName + "\nDirection:" + foundPointNames.get(i).pointDirectionName + "\nNext Location:" + foundPointNames.get(i).toPointName + "\n";
            mes3.append(combo);
            mes3.append("\n");
        }
    }
    public ArrayList<Node> findPointNames(ArrayList<Node> foundPointNames)
    {
        //setup.getDirect()
        ArrayList<Node> nameArray = new ArrayList<>();
        db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
        if(db != null)
        {
            System.out.println("Ceke 1");
            Cursor cur = db.rawQuery("select * from map_points", null);
            System.out.println("Ceke 2");
            while (cur.moveToNext()) {

                    Node nameNode = new Node(cur.getInt(0),cur.getString(1));
                    nameArray.add(nameNode);
                    //String fromPointName ;
                    //String toPointName;
            }
            for (int i = 0; i < nameArray.size();i++)
            {
                for (int j = 0; j < nameArray.size();j++) {
                    if (setup.getDirect(i). == ) {
                        if(foundPointNames.get(i).toPointName == setup.getDirect())
                        {

                        }
                    }
                }
                Node edge = new Node("Entrance","Reception", "Canteen");
                foundPointNames.add(edge);
            }
        }
        System.out.println("Check: "+nameArray);
        return foundPointNames;

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
}
