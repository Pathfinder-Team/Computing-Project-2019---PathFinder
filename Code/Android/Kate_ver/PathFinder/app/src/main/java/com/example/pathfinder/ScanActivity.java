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
        Intent intent = getIntent();
        ActExtra = intent.getExtras();
        // getting the name of the page you just came from
        ActivitySelector = ActExtra.getString("ActivityName");
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);
    }

    @Override
    public void handleResult(Result result)
    {
        // returning the current message that you have scanned from the qr code and then passing it to the PathFinder page
        if(ActivitySelector.equals("PathFinder"))
        {
            Intent intent = new Intent(this, PathFinder.class);
            Bundle extras1 = new Bundle();
            SpecialMessage = result.getText();
            extras1.putString("ResultLocation",SpecialMessage);
            intent.putExtras(extras1);
            startActivity(intent);
        }
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // on pause stop the camera
        ScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // on resume start the camera again
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}
