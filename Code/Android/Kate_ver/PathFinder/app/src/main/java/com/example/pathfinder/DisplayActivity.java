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
        db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
        try {
            setup.setUpMap(current_selected,selected_destination,db);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("current_selected: "+current_selected);
        System.out.println("selected_destination:"+selected_destination);

        Button btn_scan = (Button) findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(this);
    }
    protected void onStart()
    {
        super.onStart();
        TextView mes1 = (TextView)findViewById(R.id.display_current);
        TextView mes2 =  (TextView)findViewById(R.id.display_next);
        mes1.setText(current_selected);
        mes2.setText(selected_destination);
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
