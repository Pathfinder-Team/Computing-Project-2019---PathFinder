import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.pathfinder.GetMapActivity;
import com.example.pathfinder.HttpHandler;
import com.example.pathfinder.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/*
package com.example.pathfinder;


import android.app.ProgressDialog;
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

    public class SpecialObject
    {
        String specialValue;
        int specialValueInt;

        public SpecialObject()
        {

        }
        public SpecialObject(String value)
        {
            this.specialValue = value;
        }

        public SpecialObject(int value)
        {
            this.specialValueInt = value;
        }

    }

    private String TAG = GetMapActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get contacts JSON
    private static String url = "https://pathsearcher.azurewebsites.net/ActionJson";

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
/*
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

        Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("map_points");

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    int current_point_id = c.getInt("current_point_id");
                    String point_name = c.getString("point_name");
                    int maps_map_id = c.getInt("maps_map_id");
                    int point_from_id = c.getInt("point_from_id");
                    int point_to_id = c.getInt("point_to_id");
                    int point_weight = c.getInt("point_weight");
                    String point_direction = c.getString("point_direction");

                    // tmp hash map for single contact
                    GetMapActivity.SpecialObject ob = new GetMapActivity.SpecialObject();

                    HashMap<String, String> contact = new HashMap<>();


                        /*
                        contact.put("current_point_id", new SpecialObject(current_point_id));
                        contact.put("maps_map_id", new SpecialObject(maps_map_id));
                        contact.put("point_from_id", new SpecialObject(point_from_id));
                        contact.put("point_to_id", new SpecialObject(point_to_id));
                        contact.put("point_weight", new SpecialObject(point_weight));
                        contact.put("point_name", new SpecialObject(point_name));
                        contact.put("point_direction", new SpecialObject(point_direction));

                         */
/*
                    contact.put("point_weight", Integer.toString(point_weight));
                    contact.put("point_name",point_name);
                    contact.put("point_direction", point_direction);


                    // adding contact to contact list
                    contactList.add(contact);
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
            pDialog.dismiss();
       Updating parsed JSON data into ListView
*/
/*
        ListAdapter adapter = new SimpleAdapter(
                GetMapActivity.this, contactList,
                R.layout.list_item, new String[]{"point_name", "point_directions","point_weight"}, new int[]{R.id.point_name,R.id.point_direction,R.id.point_weight});

        lv.setAdapter(adapter);
    }

}
}
*/