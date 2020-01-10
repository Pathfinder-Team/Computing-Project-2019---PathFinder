package com.example.pathfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class PathFinder extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ArrayList<String> pointNames = new ArrayList<>();

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_finder);


        db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getPointName());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), "Selected User: "+getPointName().get(position) ,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
    }

    public ArrayList<String> getPointName()
    {
        // Cursor c = getReadableDatabase().rawQuery("SELECT * FROM friendinfotable",null);

        Cursor c = db.rawQuery("select * from map_points",null);

        while(c.moveToNext())
        {
            System.out.println("Special: "+c.getString(1));
            pointNames.add(c.getString(1));
        }
        return pointNames;
    }
}
