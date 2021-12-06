package com.example.recyclme_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    //member variables
    Button scanButton;
    Button lookupButton;
    Button awardsButton;
    DataBaseHelper dbHelper;

    ArrayAdapter productArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set content view and initialize databasehelper
        setContentView(R.layout.activity_main);
        dbHelper = new DataBaseHelper(MainActivity.this);

        //scan button initialzie and functionality
        scanButton = findViewById(R.id.btn_scan);
        scanButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openScanActivity();
            }
        });

        //look up button initialzie and functionality
        lookupButton = findViewById(R.id.btn_lookup);
        lookupButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openLookUpActivity();
            }
        });

        //Awards button initialzie and functionality
        awardsButton = findViewById(R.id.btn_awards);
        awardsButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                openAwardsActivity();
            }
        });

        //Check for database
        File db = getApplicationContext().getDatabasePath(DataBaseHelper.DATABASE_NAME);
        if (!db.exists()) {
            dbHelper.getReadableDatabase();
            if (copyDatabase(this)) {
                //toast for error checking - success
                Toast.makeText(this, "Copy Database Success", Toast.LENGTH_SHORT).show();
            } else {
                //toast for error checking - failure
                Toast.makeText(this, "Copy Data Error", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }



    public void openScanActivity(){
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
    }

    public void openLookUpActivity(){
        Intent intent = new Intent(this, LookUpActivity.class);
        startActivity(intent);
    }

    public void openAwardsActivity(){
        Intent intent = new Intent(this, AwardsActivity.class);
        startActivity(intent);
    }

    //copy's the database from the assets folder to the data/data folder
    private boolean copyDatabase(Context context){
        try{
            //location of the database in the assets folder
            InputStream inputStream = context.getAssets().open(DataBaseHelper.DATABASE_NAME);
            //location for the database to be copied to
            String outFileName = DataBaseHelper.DATABASE_PATH + DataBaseHelper.DATABASE_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[]buff = new byte[1024];
            int len = 0;
            while((len = inputStream.read(buff)) > 0){
                outputStream.write(buff, 0, len);
            }
            outputStream.flush();
            outputStream.close();
            Log.v("ResultsActivity", "Data Loaded Successfully");
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}