package com.example.recyclme_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ResultsActivity extends AppCompatActivity {

    private final int garbage = R.drawable.garbage;
    private final int remove_label = R.drawable.remove_label;
    private final int rinse = R.drawable.rinse;
    private final int safe = R.drawable.safe;
    private DataBaseHelper dbHelper;
    private long barcodeResult;
    private ProductDAO productDAO;
    TextView textView;
    Button btn_recycle;
    ImageView instruction1;
    ImageView instruction2;
    ImageView instruction3;
    ImageView instruction4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set content view, enable home button, gets intent, initializes databasehelper
        setContentView(R.layout.activity_results);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        dbHelper = new DataBaseHelper(ResultsActivity.this);

        //pulling values out of intent
        barcodeResult = intent.getLongExtra(ScanActivity.EXTRA_LONG, 1L);
        productDAO = dbHelper.getProduct(barcodeResult);

        //connecting xml fields
        instruction1 = findViewById(R.id.iv_instruction1);
        instruction2 = findViewById(R.id.iv_instruction2);
        instruction3 = findViewById(R.id.iv_instruction3);
        instruction4 = findViewById(R.id.iv_instruction4);
        btn_recycle = findViewById(R.id.btn_recycle);
        textView = findViewById(R.id.tv_result_prodcut_name);

        //setting views
        textView.setText(productDAO.getProductName());
        setInstructions(productDAO.getRecycle_instructions());

        //button functionality
        btn_recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.incrementAwardData(productDAO.getWeight());
                Toast.makeText(ResultsActivity.this, "Thank You For Recycling!", Toast.LENGTH_SHORT).show();
                openMainActivity();
            }
        });
    }

    //low IQ way of displaying instructions
    //dynamic list (probably recycler view) would be more functional here
    private void setInstructions(int instructions){
        switch (instructions){
            case 1:
                instruction1.setImageResource(safe);
                instruction2.setVisibility(View.INVISIBLE);
                instruction3.setVisibility(View.INVISIBLE);
                instruction4.setVisibility(View.INVISIBLE);
                break;
            case 2:
                instruction1.setImageResource(remove_label);
                instruction2.setImageResource(safe);
                instruction3.setVisibility(View.INVISIBLE);
                instruction4.setVisibility(View.INVISIBLE);
                break;
            case 3:
                instruction1.setImageResource(rinse);
                instruction2.setImageResource(safe);
                instruction3.setVisibility(View.INVISIBLE);
                instruction4.setVisibility(View.INVISIBLE);
                break;
            case 4:
                instruction1.setImageResource(garbage);
                instruction1.setImageResource(rinse);
                instruction3.setImageResource(safe);
                instruction4.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}