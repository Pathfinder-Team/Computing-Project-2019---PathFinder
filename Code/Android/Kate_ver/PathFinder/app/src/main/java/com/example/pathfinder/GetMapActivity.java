package com.example.pathfinder;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
    private ListView lv;
    SQLiteDatabase db;

    // URL to get contacts JSON
    private static String url = "https://pathsearcher.azurewebsites.net/ActionJson";


    ArrayList<HashMap<String, String>> contactList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_map);

        contactList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(GetMapActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            System.out.println("Check:: "+url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray PathFinderMap = jsonObj.getJSONArray("map_points");
                    //JSONArray PathFinderMap2 = jsonObj.getJSONArray("special_points");


                    db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
                    db.execSQL("DROP TABLE IF EXISTS map_points");
                    db.execSQL("DROP TABLE IF EXISTS special_points");

                    db.execSQL("CREATE TABLE IF NOT EXISTS " +
                            "map_points(" +
                            "current_point_id int ," +
                            "point_name varhar," +
                            "maps_map_id int);");

                    db.execSQL("CREATE TABLE IF NOT EXISTS " +
                            "special_points(" +
                            "point_id int ," +
                            "point_from_id int," +
                            "point_to_id int," +
                            "point_weight int," +
                            "point_direction varhar);");

                    // looping through All Contacts
                    int counter = 0;
                    int counter2 = 0;
                    for (int i = 0; i < PathFinderMap.length(); i++) {
                        JSONObject c = PathFinderMap.getJSONObject(i);
                        counter2++;
                        System.out.println("Counter2: "+counter2);

                        int current_point_id = c.getInt("current_point_id");
                        String point_name = c.getString("point_name");
                        int maps_map_id = c.getInt("maps_map_id");
                        //System.out.println("Check: " + point_name);
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
                                    counter++;
                                    System.out.println("PathFinderMap2.length(): "+PathFinderMap2.length());
                                    //System.out.println("Counter: "+counter);
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
                    HashMap<String, String> contact = new HashMap<>();
                    String old="old name";

                    contact.put("new",old);

                    contactList.add(contact);
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
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    GetMapActivity.this, contactList,
                    R.layout.list_item, new String[]{"point_name"}, new int[]{R.id.point_name});

            lv.setAdapter(adapter);
        }

    }
}
