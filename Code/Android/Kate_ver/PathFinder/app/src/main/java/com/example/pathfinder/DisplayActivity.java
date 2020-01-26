package com.example.pathfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener {


    String current_selected = "";
    String selected_destination = "";
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        current_selected = extras.getString("current_selected");
        selected_destination = extras.getString("selected_destination");

        Setup setup = new Setup();

        try {
            db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
            setup.setUpMap(current_selected,selected_destination,db);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println("current_selected: "+current_selected);
        //System.out.println("selected_destination:"+selected_destination);

        Button btn_scan = (Button) findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(this);
    }
    protected void onStart()
    {
        super.onStart();
        TextView mes1 = (TextView)findViewById(R.id.display_current);
        TextView mes2 =  (TextView)findViewById(R.id.display_next);
        TextView mes3 =  (TextView)findViewById(R.id.display_path_information);
        mes1.setText(current_selected);
        mes2.setText(selected_destination);
        ArrayList<String > myArray = new ArrayList<>();
        myArray.add("Straight");
        myArray.add("turn left");
        myArray.add("turn right");
        myArray.add("upstairs");
        myArray.add("downstairs");

        for(int i = 0; i < myArray.size(); i++)
        {
            mes3.append(myArray.get(i));
            mes3.append("\n");
        }
    }

    public void onClick(View view)
    {
        Intent intent;
        switch(view.getId()) {
            case R.id.btn_scan:
                intent = new Intent(this, ScanActivity.class);
                startActivity(intent);
                break;
        }
    }
}
