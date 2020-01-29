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
import java.util.HashMap;


public class GetMapActivity extends AppCompatActivity {

    private String TAG = GetMapActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    SQLiteDatabase db;
    static String orgName = "Limerick Institute of Technology";
    static String org_building ="LIT Thurles";
    // URL to get contacts JSON
<<<<<<< HEAD
    //private static String url = "https://pathsearcher.azurewebsites.net/ActionJson?org_name="+orgName+"&org_building="+org_building;
    private static String url = "http://10.0.2.2:8080/PathFinder/ActionJson?org_name="+orgName+"&org_building="+org_building;
=======
    private static String url = "https://pathsearcher.azurewebsites.net/ActionJson?org_name="+orgName+"&org_building="+org_building;
    //private static String url = "http://10.0.2.2:8080/PathFinder/ActionJson?org_name="+orgName+"&org_building="+org_building;
>>>>>>> f1e529aef7d155b0bce6395396de9fceef00be9a

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
                    db.execSQL("DROP TABLE IF EXISTS map_points");
                    db.execSQL("DROP TABLE IF EXISTS special_points");

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
                    //System.out.println("Check: "+PathFinderMap.length());
                    for (int i = 0; i < PathFinderMap.length(); i++) {
                        JSONObject c = PathFinderMap.getJSONObject(i);
                        int current_point_id = c.getInt("current_point_id");
                        String point_name = c.getString("point_name");
                        int maps_map_id = c.getInt("maps_map_id");
                        db.execSQL("INSERT INTO map_points VALUES('"
                                + current_point_id + "','"
                                + point_name + "','"
                                + maps_map_id + "');");

                        if (c != null) {
                            JSONArray PathFinderMap2 = c.getJSONArray("special_points");
                            if(PathFinderMap2 != null) {
                                for (int j = 0; j < PathFinderMap2.length(); j++)
                                {
                                    JSONObject cc = PathFinderMap2.getJSONObject(j);
                                    int point_id = cc.getInt("point_id");
                                    int point_from_id = cc.getInt("point_from_id");
                                    int point_to_id = cc.getInt("point_to_id");
                                    int point_weight = cc.getInt("point_weight");
                                    String point_direction = cc.getString("point_direction");

                                    db.execSQL("INSERT INTO special_points VALUES('"
                                            + point_id + "','"
                                            + point_from_id + "','"
                                            + point_to_id + "','"
                                            + point_weight + "','"
                                            + point_direction + "');");
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
                txtView.setText("The Map has been updated");
            }
            else if (db == null)
            {
                txtView.setText("The Map has been not been updated");
            }
        }
    }
}
