package com.example.pathfinder;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class OrgActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    SQLiteDatabase db;
    String selectedOrgName = "";
    String selectedBuildingName = "";
    ArrayList<String> orgNames = null;
    ArrayList<String> orgBuildings = null;
    public static ArrayList<OrgNode> allOrgBuildingDetails = null;
    Spinner spin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org);

        db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
        // if the db isnt null then wipe the db so its fresh
        if(db != null) {
            SpecialClass specialClass = new SpecialClass();
            specialClass.WipeDBRunning(db);
            populateTables();
        }

        orgNames = new ArrayList<>();
        orgBuildings = new ArrayList<>();
        allOrgBuildingDetails = new ArrayList<>();

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin2 = (Spinner) findViewById(R.id.spinner2);

        // if the orgName has been selected the display the second spinner
        if(getOrgNames() != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getOrgNames());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spin.setAdapter(adapter);
            spin.setOnItemSelectedListener(this);

            Button btn_update_org = (Button) findViewById(R.id.btn_update_org);
            btn_update_org.setOnClickListener(this);

            Button btn_update_map = (Button) findViewById(R.id.btn_update_map);
            btn_update_map.setOnClickListener(this);
        }
        else
        {
            TextView textView3 = findViewById(R.id.status_message);
            textView3.setText("Please Update Your Organisations");
        }
    }

    // if you have selectd an item
    @Override
    public void onItemSelected(@org.jetbrains.annotations.NotNull AdapterView<?> arg0, View arg1, int position, long id) {

        // if you select something from the first spinner
        if(arg0.getId() == R.id.spinner)
        {
            selectedOrgName = getOrgNames().get(position);
            getBuildingNames(selectedOrgName);
        }
        // if you select something from the second spinner
        if(arg0.getId() == R.id.spinner2)
        {
            selectedBuildingName = getOrgBuildings(selectedOrgName).get(position);
        }
    }

    // restarting the activity on back
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
    public void getBuildingNames(String special1)
    {
        // setting up second spinner to display the organisation buildings
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getOrgBuildings(special1));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin2.setAdapter(adapter2);
        spin2.setOnItemSelectedListener(this);
    }
    // getting the organisation names
    public ArrayList<String> getOrgNames()
    {
        db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
        if(db!= null)
        {
            Cursor c = db.rawQuery("select organisation_name from org_details", null);
            if( c != null) {
                if (c.getCount() != orgNames.size() && c.getCount() > 0) {
                    while (c.moveToNext()) {
                        // if the orgNames doesnt contain a building name then add it
                        if(!orgNames.contains(c.getString(0))) {
                            orgNames.add(c.getString(0));
                        }
                    }
                }
            }
            c.close();
            return orgNames;
        }
        else
        {
            return null;
        }

    }

    public ArrayList<String> getOrgBuildings(String special1)
    {
        // clearing the buildings so everytime you select a new org it displays only the ones that are connected to the database
        orgBuildings.clear();
        db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
        if(db != null)
        {
            Cursor cc = db.rawQuery("select * from map_details where org_name = ?", new String[]{special1});
            if( cc != null) {
                if (cc.getCount() != orgBuildings.size() && cc.getCount() > 0) {
                    while (cc.moveToNext()) {
                        byte[] decodedString = Base64.decode(cc.getString(5), Base64.DEFAULT);
                        Bitmap map_image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        //System.out.println(map_image);
                        OrgNode addEdge = new OrgNode(cc.getInt(0),
                                cc.getString(1),
                                cc.getString(2),
                                cc.getString(3),
                                cc.getString(4),
                                map_image);
                        allOrgBuildingDetails.add(addEdge);
                        if(!orgBuildings.contains(cc.getString(2))) {
                            orgBuildings.add(cc.getString(2));
                        }
                    }
                }
            }
            cc.close();
            return orgBuildings;
        }
        else
        {
            return null;
        }

    }
    public void onClick(View view)
    {
        Intent intent;
        switch(view.getId()) {
            case R.id.btn_update_org:
                intent = new Intent(this, GetOrgActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_update_map:
                // if both points arent null move to display activity and pass org name and building along
                if(selectedOrgName != null && selectedBuildingName != null) {
                    if(selectedOrgName != "" && selectedBuildingName != "") {
                        buildMapImage();
                        intent = new Intent(this, GetMapActivity.class);
                        Bundle extras = new Bundle();
                        extras.putString("orgName", selectedOrgName);
                        extras.putString("org_building", selectedBuildingName);
                        intent.putExtras(extras);
                        startActivity(intent);
                    }
                }
                break;
        }
    }
    // populating the tables
    public void populateTables()
    {
        Cursor buildCur = db.rawQuery("select organisation_building_name from org_details", null);
        if(buildCur != null)
        {
            while (buildCur.moveToNext())
            {
                String organisation_building_name = buildCur.getString(0);
                db.execSQL("INSERT INTO building_details VALUES('"+organisation_building_name+"')");
            }
        }
        buildCur.close();

    }
    public void buildMapImage()
    {
        Cursor buildImage = db.rawQuery("select map_image from map_details where org_name = ?", new String[] {selectedOrgName});

        if(buildImage != null)
        {
            while (buildImage.moveToNext())
            {
                String map_image = buildImage.getString(0);
                //System.out.println("map_image: "+map_image);
                db.execSQL("INSERT INTO map_information VALUES('"+map_image+"')");
            }
        }
        buildImage.close();
    }
}
