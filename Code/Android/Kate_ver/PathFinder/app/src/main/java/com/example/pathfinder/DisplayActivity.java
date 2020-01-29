package com.example.pathfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
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
    public ArrayList<Bitmap> buildingMaps = null;
    ArrayList<Node> specialOmega = null;
    ImageView imageView;
    int currentImage = 0;
    SQLiteDatabase db;
    Setup setup = new Setup();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        buildingMaps = new ArrayList<>();
        specialOmega = new ArrayList<>();
        getBuildingMaps();

        Bundle extras = getIntent().getExtras();
        getCurrentLocationDetails = (ArrayList<Node>) extras.getSerializable("current_selected");

        if(getCurrentLocationDetails.size() > 0) {
            current_selected_id = getCurrentLocationDetails.get(0).current_point_id;
            current_selected_name = getCurrentLocationDetails.get(0).point_name;
            current_selected_map_id = getCurrentLocationDetails.get(0).maps_map_id;

            getNextLocationDetails = (ArrayList<Node>) extras.getSerializable("selected_destination");
            selected_destination_id = getNextLocationDetails.get(0).current_point_id;
            selected_name = getNextLocationDetails.get(0).point_name;
            selected_map_id = getNextLocationDetails.get(0).maps_map_id;
        }

        db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
        if(getCurrentLocationDetails.size() > 0) {
            setup.setUpMap(db);
        }
        //db.close();
    }

    private void setImageRotateListener() {
        final Button buttonRotate = (Button) findViewById(R.id.btn_change_image);
        buttonRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                currentImage++;
                if (currentImage == buildingMaps.size()) {
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
        if(buildingMaps.size() > 0) {
            Bitmap bittymap = buildingMaps.get(currentImage);
            imageView.setImageBitmap(bittymap);
        }
    }

    protected void onStart()
    {
        super.onStart();
        TextView mes1 = (TextView)findViewById(R.id.display_current);
        TextView mes2 =  (TextView)findViewById(R.id.display_next);
        TextView mes3 =  (TextView)findViewById(R.id.display_path_information);
        mes1.setText(current_selected_name);
        mes2.setText(selected_name);
        System.out.println(current_selected_name);
        System.out.println(selected_name);
        ArrayList<Node> foundPointNames = new ArrayList<>();
        foundPointNames = findPointNames(foundPointNames);
        for(int i = 0; i <  foundPointNames.size(); i++) {
            String combo = "Location: " + foundPointNames.get(i).fromPointName + "\nDirection:" + foundPointNames.get(i).pointDirectionName + "\nNext Location:" + foundPointNames.get(i).toPointName + "\n";
            //mes3.append("Location: " + foundPointNames.get(i).fromPointName);
            //mes3.append("\nDirection:" + foundPointNames.get(i).pointDirectionName);
            //mes3.append("\nNext Location:" + foundPointNames.get(i).toPointName);
            mes3.append(combo);
            mes3.append("\n\n");
        }
    }
    public ArrayList<Node> findPointNames(ArrayList<Node> foundPointNames)
    {
        specialOmega = setup.getDirect();

        ArrayList<Node> nameArray = new ArrayList<>();
        db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
        if(db != null)
        {
            Cursor cur = db.rawQuery("select * from map_points", null);
            while (cur.moveToNext()) {

                Node nameNode = new Node(cur.getInt(0),cur.getString(1));
                nameArray.add(nameNode);
            }
            String name1 = "";
            String name2 = "";
            for (int i = 0; i < specialOmega.size();i++)
            {
                int local_num_1 = specialOmega.get(i).fromPointId;
                int local_num_2 = specialOmega.get(i).toPointId;
                String local_direction_value = specialOmega.get(i).pointDirection;

                for(int j = 0; j < nameArray.size();j++)
                {
                    if(local_num_1 == nameArray.get(j).point)
                    {
                        name1 = "";
                        name1 = nameArray.get(j).name;
                    }
                    if(local_num_2 == nameArray.get(j).point)
                    {
                        name2="";
                        name2 = nameArray.get(j).name;
                    }

                }

                local_direction_value = makePretty(local_direction_value);
                Node edge = new Node(name1,name2,local_direction_value);
                foundPointNames.add(edge);
                //System.out.println("Size: "+specialOmega.size());
                //System.out.println("i: "+i);
            }
        }
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
    public String makePretty(String local_variable)
    {

        if(local_variable.equals("straight_ahead"))
        {
            local_variable = "Head Straight ahead to reach the next destination";
        }
        else if(local_variable.equals("turn_left"))
        {
            local_variable = "Turn left ahead to reach the next destination";
        }
        else if(local_variable.equals("turn_right"))
        {
            local_variable = "Turn right ahead to reach the next destination";
        }
        else if(local_variable.equals("upstairs"))
        {
            local_variable = "Head up stairs to reach the next destination";
        }
        else if(local_variable.equals("downstairs"))
        {
            local_variable = "Head down stairs to reach the next destination";
        }
        return local_variable;
    }
    public void getBuildingMaps()
    {
        //System.out.println("Check Here");
        db = openOrCreateDatabase("mapDB", Context.MODE_PRIVATE, null);
        if (db != null)
        {
            Cursor cc = db.rawQuery("select map_image from map_information", null);
            if (cc != null)
            {
                while (cc.moveToNext())
                {
                    byte[] decodedString = Base64.decode(cc.getString(0), Base64.DEFAULT);
                    Bitmap map_image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    //System.out.println("Map_Image: "+map_image);
                    buildingMaps.add(map_image);
                }
            }
            //db.close();
        }
        //System.out.println("before call");
        setInitialImage();
        setImageRotateListener();
    }
}
