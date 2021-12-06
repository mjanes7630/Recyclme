package com.example.recyclme_v3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity {


    //member variables
    IntentIntegrator intentIntegrator;
    public static final String EXTRA_LONG = "com.example.recyclme_v3.EXTRA_LONG";
    public long scanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        //initialize intent integrator
        intentIntegrator = new IntentIntegrator(ScanActivity.this);
        //Set Prompt Text
        intentIntegrator.setPrompt("For Flash Use Volume Up");
        //Set Beep
        intentIntegrator.setBeepEnabled(true);
        //lock orientation
        intentIntegrator.setOrientationLocked(true);
        //set capture activity
        intentIntegrator.setCaptureActivity(Capture.class);
        //initialize scan
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //Initialze intent result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //check condition
        if(intentResult.getContents() != null){
            scanResult = Long.parseLong(intentResult.getContents());
            openResultsActivity();
        } else{
            Toast.makeText(getApplicationContext(), "OOps...You did not scan anything", Toast.LENGTH_SHORT).show();
        }
    }

    public void openResultsActivity(){
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra(EXTRA_LONG, scanResult);
        startActivity(intent);
    }

}