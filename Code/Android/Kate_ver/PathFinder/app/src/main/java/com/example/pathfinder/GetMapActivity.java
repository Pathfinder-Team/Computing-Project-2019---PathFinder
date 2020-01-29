package com.example.pathfinder;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


public class GetMapActivity extends AppCompatActivity {

    private String TAG = GetMapActivity.class.getSimpleName();


    ArrayList<Node> mp = new ArrayList<>();
    ArrayList<Node> sp = new ArrayList<>();
    private ProgressDialog pDialog;
    SQLiteDatabase db;
    static String orgName = "";
    static String org_building ="";
    // URL to get contacts JSON
    //private static String url = "https://pathsearcher.azurewebsites.net/ActionJson?org_name="+orgName+"&org_building="+org_building;
    private static String url = "http://10.0.2.2:8080/PathFinder/ActionJson?org_name="+orgName+"&org_building="+org_building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_map);

        Bundle extras = getIntent().getExtras();
        String orgName =  extras.getString("orgName");
        String org_building = extras.getString("org_building");

        url = "http://10.0.2.2:8080/PathFinder/ActionJson?org_name="+orgName+"&org_building="+org_building;

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
                    //System.out.println("Check: "+PathFinderMap.length());
                    for (int i = 0; i < PathFinderMap.length(); i++) {
                        JSONObject c = PathFinderMap.getJSONObject(i);
                        //int current_point_id = c.getInt("current_point_id");
                        //String point_name = c.getString("point_name");
                        //int maps_map_id = c.getInt("maps_map_id");

                        Node specialNode1 = new Node(c.getInt("current_point_id"),c.getString("point_name"),c.getInt("maps_map_id"));
                        mp.add(specialNode1);
                        /*
                        db.execSQL("INSERT INTO map_points VALUES('"
                                + current_point_id + "','"
                                + point_name + "','"
                                + maps_map_id + "');");

                         */


                        if (c != null) {
                            JSONArray PathFinderMap2 = c.getJSONArray("special_points");
                            if(PathFinderMap2 != null) {
                                for (int j = 0; j < PathFinderMap2.length(); j++)
                                {
                                    JSONObject cc = PathFinderMap2.getJSONObject(j);

                                    /*
                                    int point_id = cc.getInt("point_id");
                                    int point_from_id = cc.getInt("point_from_id");
                                    int point_to_id = cc.getInt("point_to_id");
                                    int point_weight = cc.getInt("point_weight");
                                    String point_direction = cc.getString("point_direction");

                                     */
                                    Node specialNode2 = new Node(cc.getInt("point_id"),
                                            cc.getInt("point_from_id"),
                                            cc.getInt("point_to_id"),
                                            cc.getInt("point_weight"),
                                            cc.getString("point_direction"));
                                    sp.add(specialNode2);
                                    /*

                                    db.execSQL("INSERT INTO special_points VALUES('"
                                            + point_id + "','"
                                            + point_from_id + "','"
                                            + point_to_id + "','"
                                            + point_weight + "','"
                                            + point_direction + "');");


                                     */
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

            callCommiter();
            if(db != null) {
                txtView.setText("The Map has been updated");
            }
            else if (db == null)
            {
                txtView.setText("The Map has been not been updated");
            }
        }
        public void callCommiter()
        {
            callValueSorter();

        }
        public void callValueSorter()
        {
            ArrayList<MapNode> sortMapPoints = new ArrayList<>();
            int counter = 1;
            for(int i = 0; i < mp.size();i++)
            {
                MapNode mp1 = new MapNode(counter++, mp.get(i).current_point_id);
                sortMapPoints.add(mp1);
                if(sortMapPoints.get(i).oldNum == mp.get(i).current_point_id)
                {
                    //System.out.println("Old: "+mp.get(i).current_point_id+". New: "+sortMapPoints.get(i).newNum);
                    mp.get(i).current_point_id = sortMapPoints.get(i).newNum;
                }

            }

            //
            //System.out.println("check stage 2");
            for(int i = 0; i < sp.size(); i++)
            {
                //System.out.println("from: "+sp.get(i).point_from_id+". to: "+sp.get(i).point_to_id);

                //System.out.println("size: "+sp.size());
                //System.out.println("old: "+sortMapPoints.get(i).oldNum);
                //System.out.println("point from: "+sp.get(i).point_from_id);
                for(int j = 0; j < mp.size();j++) {
                    if (sortMapPoints.get(j).oldNum == sp.get(i).point_from_id) {
                        //System.out.println("Old: "+sp.get(i).point_from_id+". New: "+sortMapPoints.get(i).newNum);
                        sp.get(i).point_from_id = sortMapPoints.get(j).newNum;
                    }
                    if(sortMapPoints.get(j).oldNum == sp.get(i).point_to_id)
                    {
                        //System.out.println("Old: "+sp.get(i).point_to_id+". New: "+sortMapPoints.get(i).newNum);
                        sp.get(i).point_to_id = sortMapPoints.get(j).newNum;
                    }
                }
            }
            for(int i = 0; i < mp.size(); i++)
            {
                db.execSQL("INSERT INTO map_points VALUES('"
                        + mp.get(i).current_point_id + "','"
                        + mp.get(i).point_name + "','"
                        + mp.get(i).maps_map_id + "');");

                //System.out.println("From: "+sp.get(i).point_from_id+", To: "+sp.get(i).point_to_id);
            }

            for(int i = 0; i < sp.size(); i++)
            {
                db.execSQL("INSERT INTO special_points VALUES('"
                        + sp.get(i).point_id + "','"
                        + sp.get(i).point_from_id + "','"
                        + sp.get(i).point_to_id + "','"
                        + sp.get(i).point_weight + "','"
                        + sp.get(i).point_direction + "');");
                //System.out.println("From: "+sp.get(i).point_from_id+", To: "+sp.get(i).point_to_id);
            }
        }
    }
}
