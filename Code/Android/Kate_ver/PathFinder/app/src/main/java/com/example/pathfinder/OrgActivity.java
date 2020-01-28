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
    static String orgName = "Limerick Institute of Technology";
    static String org_building ="LIT Thurles";
    // URL to get contacts JSON
    private static String url = "https://pathsearcher.azurewebsites.net/ActionJsonOrg";
    //private static String url = "http://10.0.2.2:8080/PathFinder/ActionJson";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org);


        Spinner spin = (Spinner) findViewById(R.id.spinner);
        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getOrgNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getOrgNames());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);
        spin2.setAdapter(adapter2);
        spin2.setOnItemSelectedListener(this);

        Button btn_update_map = (Button) findViewById(R.id.btn_update_map);
        btn_update_map.setOnClickListener(this);

        new GetMapPoints().execute();
    }
    private class GetMapPoints extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(OrgActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray PathFinderMap = jsonObj.getJSONArray("Org_Details");

                    db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
                    db.execSQL("DROP TABLE IF EXISTS Org_Details");
                    db.execSQL("DROP TABLE IF EXISTS Map_Details");

                    db.execSQL("CREATE TABLE IF NOT EXISTS " +
                            "Org_Details(" +
                            "organisation_name varchar ," +
                            "organisation_address varchar ," +
                            "organisation_email varchar," +
                            "organisation_mobile varchar," +
                            "organisation_building_name varchar);");

                    db.execSQL("CREATE TABLE IF NOT EXISTS " +
                            "Map_Details(" +
                            "map_id int ," +
                            "org_name varchar," +
                            "org_building varchar," +
                            "map_name varchar," +
                            "map_comments varchar," +
                            "map_image varchar);");

                    for (int i = 0; i < PathFinderMap.length(); i++) {

                        JSONObject c = PathFinderMap.getJSONObject(i);

                        String organisation_name = c.getString("organisation_name");
                        String organisation_address = c.getString("organisation_address");
                        String organisation_email = c.getString("organisation_email");
                        String organisation_mobile = c.getString("organisation_mobile");
                        String organisation_building_name = c.getString("organisation_building_name");

                        db.execSQL("INSERT INTO Org_Details VALUES('"
                                + organisation_name + "','"
                                + organisation_address + "','"
                                + organisation_email + "','"
                                + organisation_mobile + "','"
                                + organisation_building_name + "');");

                        if (c != null) {
                            JSONArray PathFinderMap2 = c.getJSONArray("Map_Details");
                            if(PathFinderMap2 != null) {
                                for (int j = 0; j < PathFinderMap2.length(); j++)
                                {
                                    JSONObject cc = PathFinderMap2.getJSONObject(j);
                                    int map_id = cc.getInt("map_id");
                                    String org_name = cc.getString("org_name");
                                    String org_building = cc.getString("org_building");
                                    String map_name = cc.getString("map_name");
                                    String map_comments = cc.getString("map_comments");
                                    String map_image = cc.getString("map_image");

                                    db.execSQL("INSERT INTO special_points VALUES('"
                                            + map_id + "','"
                                            + org_name + "','"
                                            + org_building + "','"
                                            + map_name + "','"
                                            + map_comments + "','"
                                            + map_image + "');");
                                }
                            }
                        }
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
            {
                pDialog.dismiss();
            }
            TextView txtView = findViewById(R.id.updatedID);

            if(db != null) {
                //txtView.setText("The Map has been updated");

            }
            else if (db == null)
            {
                txtView.setText("There is a connection error");
            }
        }


        public ArrayList<String> getOrgNames()
        {
            db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
            if(db != null)
            {
                Cursor c = db.rawQuery("select * from Org_Details", null);
                if (c.getCount() != orgNames.size())
                {
                    while (c.moveToNext())
                    {
                        orgNames.add(c.getString(1));
                    }
                }
            }
            return orgNames;
        }

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

            if(arg0.getId() == R.id.spinner)
            {
                special1 = getOrgNames().get(position);
            }
            if(arg0.getId() == R.id.spinner2)
            {
                special2 = getOrgNames().get(position);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

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
}
