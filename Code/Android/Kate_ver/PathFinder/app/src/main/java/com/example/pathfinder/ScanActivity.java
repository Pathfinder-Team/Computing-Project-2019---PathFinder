package com.example.pathfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    ZXingScannerView ScannerView;
    Bundle ActExtra;
    String ActivitySelector = "";
    public static String SpecialMessage = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_scan);

        Intent intent = getIntent();
        ActExtra = intent.getExtras();

        ActivitySelector = ActExtra.getString("ActivityName");

        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);
    }

    @Override
    public void handleResult(Result result)
    {
        if(ActivitySelector.equals("PathFinder"))
        {
            /*
            System.out.println("Scan Result 2: " + result);
            SpecialMessage = result.getText().toString();

            PathFinder.resultTextView1.setText(result.getText());
            //PathFinder.result = result;

             */
            Intent intent = new Intent(this, PathFinder.class);
            Bundle extras1 = new Bundle();
            SpecialMessage = result.getText().toString();
            extras1.putString("ResultLocation",SpecialMessage);
            intent.putExtras(extras1);
            startActivity(intent);
        }
        else if(ActivitySelector.equals("MenuActivity"))
        {
            System.out.println("Scan Result 1 : " + result);
            //MenuActivity.resultTextView.setText(result.getText());
        }

        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        ScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}
