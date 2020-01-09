package com.example.pathfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class PathFinder extends AppCompatActivity {
    ArrayList<String> pointNames;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_finder);

        db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getPointName());

        Spinner mSpinner = findViewById(R.id.spinner);
        mSpinner.setAdapter(adapter);
    }

    public ArrayList<String> getPointName()
    {
        // Cursor c = getReadableDatabase().rawQuery("SELECT * FROM friendinfotable",null);

        Cursor c = db.rawQuery("select * from map_points",null);

        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext())
        {
            System.out.println("Special: "+c.getString(1));
            pointNames.add(c.getString(6));
        }


        return pointNames;
    }
}
