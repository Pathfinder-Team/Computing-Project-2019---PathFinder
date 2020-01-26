package com.example.pathfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener {


    String current_selected = "";
    String selected_destination = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        current_selected = extras.getString("current_selected");
        selected_destination = extras.getString("selected_destination");

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
