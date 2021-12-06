package com.example.recyclme_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class LookUpActivity extends AppCompatActivity {

    //member variables
    public static final String EXTRA_LONG = "com.example.recyclme_v3.EXTRA_LONG";
    public long clickResult;
    ListView listView;
    EditText search;

    ArrayAdapter arrayAdapter;
    DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set view and home button, initializing Databasehelper
        setContentView(R.layout.activity_look_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHelper = new DataBaseHelper(LookUpActivity.this);

        //connecting xml fields
        search = findViewById(R.id.tv_search);
        listView = findViewById(R.id.lv_products);

        //builds product list from database, and sets onclick listener
        ShowProductsOnListView(dbHelper);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String productName = parent.getItemAtPosition(position).toString();
                ProductDAO productDAO = dbHelper.getProduct(productName);
                clickResult = productDAO.getId();
                openResultsActivity();
            }
        });

        //Search functionality
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (LookUpActivity.this).arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private void ShowProductsOnListView(DataBaseHelper dataBaseHelper2){
        this.arrayAdapter = new ArrayAdapter<String>(LookUpActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper2.viewAllProductsNames());
        this.listView.setAdapter(arrayAdapter);
    }

    public void openResultsActivity(){
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra(EXTRA_LONG, clickResult);
        startActivity(intent);
    }
}