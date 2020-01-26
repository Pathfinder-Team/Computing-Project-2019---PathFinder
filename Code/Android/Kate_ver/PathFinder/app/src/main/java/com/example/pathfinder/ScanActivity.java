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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        //setContentView(R.layout.activity_scan);

        Intent intent = getIntent();
        ActExtra = intent.getExtras();

        ActivitySelector = ActExtra.getString("ActivityName");
        setContentView(ScannerView);

    }

    @Override
    public void handleResult(Result result)
    {
        if(ActivitySelector.equals("MenuActivity"))
        {
            System.out.println("Scan Result 1 : " + result);
            MenuActivity.resultTextView.setText(result.getText());
        }
        else if(ActivitySelector.equals("PathFinder"))
        {
            System.out.println("Scan Result 2: " + result);
            PathFinder.resultTextView.setText(result.getText());
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
