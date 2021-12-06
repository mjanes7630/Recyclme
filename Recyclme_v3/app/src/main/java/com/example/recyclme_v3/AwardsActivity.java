package com.example.recyclme_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class AwardsActivity extends AppCompatActivity {

    //members
    private DataBaseHelper dbHelper;
    TextView amountView;
    TextView weightView;
    ImageView brnzMedal;
    ImageView slvrMedal;
    ImageView gldMedal;
    ImageView brnzRib;
    ImageView slvrRib;
    ImageView gldRib;
    ImageView brnzMedalGrey;
    ImageView slvrMedalGrey;
    ImageView gldMedalGrey;
    ImageView brnzRibGrey;
    ImageView slvrRibGrey;
    ImageView gldRibGrey;
    private int amountRecycled;
    private float weightRecycled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set view and home button, initializing Databasehelper
        setContentView(R.layout.activity_awards);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHelper = new DataBaseHelper(this);

        //connecting xml fields
        amountView = findViewById(R.id.tv_timesrecycled);
        weightView = findViewById(R.id.tv_weightrecycled);
        brnzMedal = findViewById(R.id.iv_bronzeMed);
        slvrMedal = findViewById(R.id.iv_silverMed);
        gldMedal = findViewById(R.id.iv_goldMed);
        brnzRib = findViewById(R.id.iv_bronzeRib);
        slvrRib = findViewById(R.id.iv_silverRib);
        gldRib = findViewById(R.id.iv_goldRib);
        brnzMedalGrey = findViewById(R.id.iv_bronzeMed_greyscale);
        slvrMedalGrey = findViewById(R.id.iv_silverMed_greyscale);
        gldMedalGrey = findViewById(R.id.iv_goldMed_greyscale);
        brnzRibGrey = findViewById(R.id.iv_bronzeRib_greyscale);
        slvrRibGrey = findViewById(R.id.iv_silverRib_greyscale);
        gldRibGrey = findViewById(R.id.iv_goldRib_greyscale);

        //set global variables, and uses those to set views
        amountRecycled = dbHelper.getUserInfo().getTimesRecycled();
        weightRecycled = dbHelper.getUserInfo().getWeightRecycled();
        amountView.setText("" + amountRecycled + " Times!");
        weightView.setText("" + weightRecycled + " Pounds!");
        setAwards(amountRecycled, weightRecycled);

    }

    private void setAwards(int amount, float weight){
        //amount medal
        if(amount >= 5){
            this.brnzMedal.setVisibility(View.VISIBLE);
            this.brnzMedalGrey.setVisibility(View.INVISIBLE);
        }
        if(amount >= 10){
            this.slvrMedal.setVisibility(View.VISIBLE);
            this.slvrMedalGrey.setVisibility(View.INVISIBLE);
        }
        if(amount >= 25){
            this.gldMedal.setVisibility(View.VISIBLE);
            this.gldMedalGrey.setVisibility(View.INVISIBLE);
        }

        //weight medal
        if(weight >= 1.0){
            this.brnzRib.setVisibility(View.VISIBLE);
            this.brnzRibGrey.setVisibility(View.INVISIBLE);
        }
        if(weight >= 2.0){
            this.slvrRib.setVisibility(View.VISIBLE);
            this.slvrRibGrey.setVisibility(View.INVISIBLE);
        }
        if(weight >= 5.0){
            this.gldRib.setVisibility(View.VISIBLE);
            this.gldRibGrey.setVisibility(View.INVISIBLE);
        }
    }
}