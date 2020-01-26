package com.example.pathfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.file.Path;
import java.util.ArrayList;

public class PathFinder extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    ArrayList<String> pointNames = null;

    SQLiteDatabase db;
    String special1 = "Empty";
    String special2 = "Empty";
    int Counter = 0;
    public static TextView resultTextView1;
    public static String result;
    Bundle ActExtra;
    String ResultLocation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_finder);

        Intent intent = getIntent();
        ActExtra = intent.getExtras();
        if(ActExtra != null)
        {
            ResultLocation = ActExtra.getString("ResultLocation");
            System.out.println("ResultLocation: " + ResultLocation);
        }
        pointNames = new ArrayList<>();
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getPointName());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getPointName());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(adapter);
        if(ResultLocation != null)
        {
            int location = getIntLocation(ResultLocation);
            spin.setSelection(location);
        }
        spin.setOnItemSelectedListener(this);
        spin2.setAdapter(adapter2);
        spin2.setOnItemSelectedListener(this);

        Button btn_find_destination = (Button) findViewById(R.id.btn_find_destination);
        btn_find_destination.setOnClickListener(this);

        Button btn_scan = (Button) findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        if(arg0.getId() == R.id.spinner)
        {
            special1 = getPointName().get(position);
        }
        if(arg0.getId() == R.id.spinner2)
        {
            special2 = getPointName().get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public ArrayList<String> getPointName()
    {
        Counter++;
        db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
        Cursor c = db.rawQuery("select * from map_points",null);
        System.out.println("Check C: "+c.getCount());
        if(c.getCount() != pointNames.size()) {
            while (c.moveToNext()) {
                //System.out.println("Special: " + c.getString(1));
                pointNames.add(c.getString(1));
            }
            System.out.println("pointNames: "+pointNames.size());
        }
        return pointNames;
    }
    public void onClick(View view)
    {
        Intent intent;
        switch(view.getId()) {
            case R.id.btn_find_destination:
                intent = new Intent(this, DisplayActivity.class);
                Bundle extras = new Bundle();
                extras.putString("current_selected",special1);
                extras.putString("selected_destination",special2);
                intent.putExtras(extras);
                startActivity(intent);
                break;
            case R.id.btn_scan:
                intent = new Intent(this, ScanActivity.class);
                Bundle ActExtra = new Bundle();
                ActExtra.putString("ActivityName","PathFinder");
                intent.putExtras(ActExtra);
                startActivity(intent);
                break;
        }
    }
    public int getIntLocation(String location)
    {
        for(int i = 0; i < pointNames.size();i++)
        {
            if(location.equals(pointNames.get(i)))
            {
                //System.out.println("##################### I: "+i);
                return i;
            }
        }
        return 0;
    }
}
