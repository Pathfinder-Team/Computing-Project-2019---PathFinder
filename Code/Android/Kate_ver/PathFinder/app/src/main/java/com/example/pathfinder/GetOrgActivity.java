package com.example.pathfinder;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Blob;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;



public class GetOrgActivity extends AppCompatActivity
{

    private String TAG = GetMapActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    SQLiteDatabase db;
    static String orgName = "Limerick Institute of Technology";
    static String org_building ="LIT Thurles";

    // URL to get contacts JSON
    //private static String url = "https://pathsearcher.azurewebsites.net/ActionJsonOrg";
    private static String url = "http://10.0.2.2:8080/PathFinder/ActionJsonOrg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_org);


        new GetOrgDetails().execute();
    }
    private class GetOrgDetails extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(GetOrgActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray PathFinderMap = jsonObj.getJSONArray("org_details");

                    db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
                    db.execSQL("DROP TABLE IF EXISTS org_details");
                    db.execSQL("DROP TABLE IF EXISTS map_details");

                    db.execSQL("CREATE TABLE IF NOT EXISTS " +
                            "org_details(" +
                            "organisation_name varchar ," +
                            "organisation_address varchar ," +
                            "organisation_email varchar," +
                            "organisation_mobile varchar," +
                            "organisation_building_name varchar);");

                    db.execSQL("CREATE TABLE IF NOT EXISTS " +
                            "map_details(" +
                            "map_id int ," +
                            "org_name varchar," +
                            "org_building varchar," +
                            "map_name varchar," +
                            "map_comments varchar," +
                            "map_image longblob);");

                    for (int i = 0; i < PathFinderMap.length(); i++) {

                        JSONObject c = PathFinderMap.getJSONObject(i);

                        String organisation_name = c.getString("organisation_name");
                        String organisation_address = c.getString("organisation_address");
                        String organisation_email = c.getString("organisation_email");
                        String organisation_mobile = c.getString("organisation_mobile");
                        String organisation_building_name = c.getString("organisation_building_name");

                        db.execSQL("INSERT INTO org_details VALUES('"
                                + organisation_name + "','"
                                + organisation_address + "','"
                                + organisation_email + "','"
                                + organisation_mobile + "','"
                                + organisation_building_name + "');");

                        if (c != null) {
                            JSONArray PathFinderMap2 = c.getJSONArray("map_details");
                            if(PathFinderMap2 != null) {
                                for (int j = 0; j < PathFinderMap2.length(); j++)
                                {
                                    JSONObject cc = PathFinderMap2.getJSONObject(j);
                                    int map_id = cc.getInt("map_id");
                                    String org_name = cc.getString("org_name");
                                    String org_building = cc.getString("org_building");
                                    String map_name = cc.getString("map_name");
                                    String map_comments = cc.getString("map_comments");

                                    byte[] decodedString = Base64.decode(cc.getString("map_image"), Base64.DEFAULT);
                                    Bitmap map_image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                                    //byte [] barr = Base64.getDecoder().decode(cc.getString("map_image"));
                                    //String map_image = barr;

                                    db.execSQL("INSERT INTO map_details VALUES('"
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
                txtView.setText("The Organisations have been updated");
            }
            else if (db == null)
            {
                txtView.setText("There is a connection error");
            }
        }
    }
}
