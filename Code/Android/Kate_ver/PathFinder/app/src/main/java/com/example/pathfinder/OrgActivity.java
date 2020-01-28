package com.example.pathfinder;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class OrgActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private String TAG = GetMapActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    SQLiteDatabase db;
    String special1 = "Empty";
    String special2 = "Empty";
    ArrayList<String> orgNames = null;
    ArrayList<String> orgBuildings = null;
    static String orgName = "Limerick Institute of Technology";
    static String org_building ="LIT Thurles";
    // URL to get contacts JSON
    private static String url = "https://pathsearcher.azurewebsites.net/ActionJsonOrg";
    //private static String url = "http://10.0.2.2:8080/PathFinder/ActionJson";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org);

        orgNames = new ArrayList<>();
        orgBuildings = new ArrayList<>();

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);

        if(getOrgNames() != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getOrgNames());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spin.setAdapter(adapter);
            spin.setOnItemSelectedListener(this);


            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getOrgBuildings());
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            System.out.println("Super: "+getOrgBuildings().get(0));
            spin2.setAdapter(adapter2);
            spin2.setOnItemSelectedListener(this);

            Button btn_update_map = (Button) findViewById(R.id.btn_update_map);
            btn_update_map.setOnClickListener(this);
        }
        else
        {
            TextView textView3 = findViewById(R.id.status_message);
            textView3.setText("Please Update Your Organisations");
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

        if(arg0.getId() == R.id.spinner)
        {
            special1 = getOrgNames().get(position);
        }
        if(arg0.getId() == R.id.spinner2)
        {
            special2 = getOrgBuildings().get(position);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
    public ArrayList<String> getOrgNames()
    {
        db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
        if(db != null)
        {
            Cursor c = db.rawQuery("select * from org_details", null);
            if( c != null) {
                if (c.getCount() != orgNames.size() && c.getCount() > 0) {
                    while (c.moveToNext()) {
                        orgNames.add(c.getString(0));
                    }
                }
            }
            return orgNames;
        }
        else
        {
            return null;
        }

    }

    public ArrayList<String> getOrgBuildings()
    {
        db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
        if(db != null)
        {
            Cursor cc = db.rawQuery("select org_building from map_details", null);
            if( cc != null) {
                if (cc.getCount() != orgBuildings.size() && cc.getCount() > 0) {
                    while (cc.moveToNext()) {
                        System.out.println("Check: "+cc.getString(0));
                        orgBuildings.add(cc.getString(0));
                    }
                }
            }
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
            case R.id.btn_update_map:
                intent = new Intent(this, GetMapActivity.class);
                startActivity(intent);
                break;
        }
    }
}
