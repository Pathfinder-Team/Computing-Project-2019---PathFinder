package com.example.pathfinder;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class GetMapActivity extends AppCompatActivity {

    private String TAG = GetMapActivity.class.getSimpleName();


    ArrayList<Node> mapPointsArray = new ArrayList<>();
    ArrayList<Node> specialPointsArray = new ArrayList<>();
    private ProgressDialog pDialog;
    SQLiteDatabase db;
    static String orgName = "";
    static String org_building ="";
    // URL to get contacts JSON
    // internet
    //private static String url = "https://pathsearcher.azurewebsites.net/ActionJson?org_name="+orgName+"&org_building="+org_building;

    // local database
    private static String url = "http://10.0.2.2:8080/PathFinder/ActionJson?org_name="+orgName+"&org_building="+org_building+"";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_map);

        Bundle extras = getIntent().getExtras();
        String orgName =  extras.getString("orgName");
        String org_building = extras.getString("org_building");

        // local database
        url = "http://10.0.2.2:8080/PathFinder/ActionJson?org_name="+orgName+"&org_building="+org_building;
        // internet database
        //url = "https://pathsearcher.azurewebsites.net/ActionJson?org_name="+orgName+"&org_building="+org_building+"";

        new GetMapPoints().execute();
    }
    private class GetMapPoints extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(GetMapActivity.this);
            pDialog.setMessage("Updating Map Please wait...");
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
                    JSONArray PathFinderMap = jsonObj.getJSONArray("map_points");
                    db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
                    SpecialClass specialClass = new SpecialClass();
                    specialClass.WipeDB(db);
                    for (int i = 0; i < PathFinderMap.length(); i++) {
                        JSONObject c = PathFinderMap.getJSONObject(i);
                        Node specialNode1 = new Node(c.getInt("current_point_id"),
                                c.getString("point_name"),
                                c.getInt("maps_map_id"));
                        // adding all details to this array to be sorted later
                        mapPointsArray.add(specialNode1);
                        if (c != null) {
                            JSONArray PathFinderMap2 = c.getJSONArray("special_points");
                            if(PathFinderMap2 != null) {
                                for (int j = 0; j < PathFinderMap2.length(); j++)
                                {
                                    JSONObject cc = PathFinderMap2.getJSONObject(j);
                                    Node specialNode2 = new Node(cc.getInt("point_id"),
                                            cc.getInt("point_from_id"),
                                            cc.getInt("point_to_id"),
                                            cc.getInt("point_weight"),
                                            cc.getString("point_direction"));
                                    // adding all details to this array to be sorted later
                                    specialPointsArray.add(specialNode2);
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
            callCommitter();

            if (db == null)
            {
                txtView.setText("The Map has been not been updated, check building or organisations you have chosen");
                Intent inten = new Intent(GetMapActivity.this, OrgActivity.class);
                startActivity(inten);
            }
            else if(db != null) {
                    txtView.setText("The Map has been updated");
                    Intent inten = new Intent(GetMapActivity.this, PathFinder.class);
                    startActivity(inten);
            }
        }
        public void callCommitter()
        {
            callValueSorter();
            for(int i = 0; i < mapPointsArray.size(); i++)
            {
                db.execSQL("INSERT INTO map_points VALUES('"
                        + mapPointsArray.get(i).current_point_id + "','"
                        + mapPointsArray.get(i).point_name + "','"
                        + mapPointsArray.get(i).maps_map_id + "');");

                //System.out.println("From: "+specialPointsArray.get(i).point_from_id+", To: "+specialPointsArray.get(i).point_to_id);
            }

            for(int i = 0; i < specialPointsArray.size(); i++)
            {
                db.execSQL("INSERT INTO special_points VALUES('"
                        + specialPointsArray.get(i).point_id + "','"
                        + specialPointsArray.get(i).point_from_id + "','"
                        + specialPointsArray.get(i).point_to_id + "','"
                        + specialPointsArray.get(i).point_weight + "','"
                        + specialPointsArray.get(i).point_direction + "');");
                //System.out.println("From: "+specialPointsArray.get(i).point_from_id+", To: "+specialPointsArray.get(i).point_to_id);
            }
            // clearing both arrays since so there empty if i come back and enter a new map
            mapPointsArray.clear();
            specialPointsArray.clear();
        }
        public void callValueSorter()
        {
            // arraylist that takes in a new number and an old number
            ArrayList<MapNode> sortMapPoints = new ArrayList<>();
            int counter = 1;
            for(int i = 0; i < mapPointsArray.size(); i++)
            {
                // take in a number from 1-N and the old current point value
                MapNode mapPointOne = new MapNode(counter++, mapPointsArray.get(i).current_point_id);
                sortMapPoints.add(mapPointOne);
                // if the old num matchs a number in the MPA array then replace it
                if(sortMapPoints.get(i).oldNum == mapPointsArray.get(i).current_point_id)
                {
                    mapPointsArray.get(i).current_point_id = sortMapPoints.get(i).newNum;
                }
            }
            for(int i = 0; i < specialPointsArray.size(); i++)
            {
                for(int j = 0; j < sortMapPoints.size(); j++ )
                {
                    // if the array contains a matching value
                    if (specialPointsArray.get(i).point_from_id == sortMapPoints.get(j).oldNum) {
                        /*
                        System.out.println(" ");
                        System.out.println("I: "+i+",Does From: "+specialPointsArray.get(i).point_from_id);
                        System.out.println("Match This: "+sortMapPoints.get(j).oldNum);
                        System.out.println("Then replace From with: "+sortMapPoints.get(j).newNum);
                         */
                        // swapping the old number for the new number
                        specialPointsArray.get(i).point_from_id = sortMapPoints.get(j).newNum;
                        // breaking out so it doesnt replace any thing it shouldnt
                        break;
                    }
                }
            }
            for(int i = 0; i < specialPointsArray.size(); i++)
            {
                for(int j = 0; j < sortMapPoints.size(); j++ )
                {
                    if (specialPointsArray.get(i).point_to_id == sortMapPoints.get(j).oldNum) {
                        /*
                        System.out.println(" ");
                        System.out.println("I: "+i+", Does To: "+specialPointsArray.get(i).point_to_id);
                        System.out.println("Match This: "+sortMapPoints.get(j).oldNum);
                        System.out.println("Then replace To with: "+sortMapPoints.get(j).newNum);
                         */
                        specialPointsArray.get(i).point_to_id = sortMapPoints.get(j).newNum;
                        break;
                    }
                }
            }
        }
    }
}
