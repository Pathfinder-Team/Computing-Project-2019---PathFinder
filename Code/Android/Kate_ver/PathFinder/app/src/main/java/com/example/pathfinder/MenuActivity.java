package com.example.pathfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    public static TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btn_org = (Button) findViewById(R.id.btn_org);
        btn_org.setOnClickListener(this);

        Button btn_scan = (Button) findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(this);

        Button btn_contact = (Button) findViewById(R.id.btn_contact);
        btn_contact.setOnClickListener(this);

        resultTextView = (TextView) findViewById(R.id.result_scan);

        Button btn_map = (Button) findViewById(R.id.btn_map);
        btn_map.setOnClickListener(this);

        Button btn_path_finder = (Button) findViewById(R.id.btn_path_finder);
        btn_path_finder.setOnClickListener(this);

    }


    public void onClick(View view)
    {
        Intent intent;
        switch(view.getId()) {
            case R.id.btn_org:
                intent = new Intent(this, OrgActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_scan:
                intent = new Intent(this, ScanActivity.class);
                Bundle extras1 = new Bundle();
                extras1.putString("ActivityName","MenuActivity");
                intent.putExtras(extras1);
                startActivity(intent);
                break;
            case R.id.btn_contact:
                intent = new Intent(this, ContactActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_map:
                intent = new Intent(this, GetMapActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_path_finder:
                intent = new Intent(this, PathFinder.class);
                startActivity(intent);
                break;
        }
    }
}
