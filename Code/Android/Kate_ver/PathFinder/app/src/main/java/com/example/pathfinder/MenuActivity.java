package com.example.pathfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btn_org = (Button) findViewById(R.id.btn_org);
        btn_org.setOnClickListener(this);

        Button btn_contact = (Button) findViewById(R.id.btn_contact);
        btn_contact.setOnClickListener(this);

        Button btn_path_finder = (Button) findViewById(R.id.btn_path_finder);
        btn_path_finder.setOnClickListener(this);

        Button btn_wipe_info = (Button) findViewById(R.id.btn_wipe_info);
        btn_wipe_info.setOnClickListener(this);
    }


    public void onClick(View view)
    {
        Intent intent;
        switch(view.getId()) {
            case R.id.btn_org:
                intent = new Intent(this, OrgActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_wipe_info:
                SQLiteDatabase db;
                db=openOrCreateDatabase("mapDB", Context.MODE_PRIVATE,null);
                SpecialClass wipeInfo = new SpecialClass();
                wipeInfo.Wipe(db);
                break;
            case R.id.btn_contact:
                intent = new Intent(this, ContactActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_path_finder:
                intent = new Intent(this, PathFinder.class);
                startActivity(intent);
                break;
        }
    }
}
