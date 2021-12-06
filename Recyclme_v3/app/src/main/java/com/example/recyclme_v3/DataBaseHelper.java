package com.example.recyclme_v3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "barcode.db";
    public static final String DATABASE_PATH = "/data/data/com.example.recyclme_v3/databases/";
    private static final int DATABASE_VERSION = 1;

    //Product
    private static final String COLUMN_PRODUCT_ID = "ID";
    private static final String COLUMN_PRODUCT_NAME = "PRODUCT_NAME";
    private static final String COLUMN_PRODUCT_WEIGHT = "WEIGHT";
    private static final String COLUMN_PRODUCT_RECYCLE_INSTRUCTIONS = "RECYCLE_INSTRUCTIONS";
    private static final String PRODUCT_TABLE = "PRODUCT_TABLE";

    //User
    private static final String COLUMN_AMOUNT_RECYCLED = "AMOUNT_RECYCLED";
    private static final String COLUMN_WEIGHT_RECYCLED = "WEIGHT_RECYCLED";
    private static final String USER_TABLE = "USER";

    //context
    private Context context;
    private SQLiteDatabase db;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //not creating database, function is un-needed, but required
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    //yeah no idea how this shit works
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //opens readable database
    public void openDatabase(){
        String dbPath = context.getDatabasePath(DATABASE_NAME).getPath();
        if(db != null && db.isOpen()){
            return;
        }
        db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    //opens writable database
    public void openWriteableDatabase(){
        String dbPath = context.getDatabasePath(DATABASE_NAME).getPath();
        if(db!= null && db.isOpen()){
            return;
        }
        db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    //closes the database
    public void closeDatabase(){
        if(db != null){
            db.close();
        }
        return;
    }

    //retrieves product info form database, put its in a PorductDAO
    //@Params prodcut barcode
    public ProductDAO getProduct(long barCode){
        openDatabase();
        //build empty productDAO
        ProductDAO product = new ProductDAO(0, null, 0, 0);
        //query statement
        String queryString = "SELECT * FROM " + PRODUCT_TABLE + " WHERE ID = " + barCode;
        Cursor cursor = this.db.rawQuery(queryString, null);

        //iterate through the cursor an assigns value to ProductDAO
        if(cursor.moveToFirst()){
            do{
                long productID = cursor.getLong(0);
                String productName = cursor.getString(1);
                float weight = cursor.getFloat(2);
                int recycle_instructions = cursor.getInt(3);

                product.setId(productID);
                product.setProductName(productName);
                product.setWeight(weight);
                product.setRecycle_instructions(recycle_instructions);
            }while(cursor.moveToNext());
        }else{
            //failure to fetch from database
        }
        cursor.close();
        closeDatabase();
        return product;
    }

    public ProductDAO getProduct(String p){
        openDatabase();
        //build empty productDAO
        ProductDAO product = new ProductDAO(0, null, 0, 0);
        //query statement
        String queryString = "SELECT * FROM " + PRODUCT_TABLE + " WHERE PRODUCT_NAME = \"" + p  + "\" ";
        Cursor cursor = this.db.rawQuery(queryString, null);

        //iterate through the cursor an assigns value to ProductDAO
        if(cursor.moveToFirst()){
            do{
                long productID = cursor.getLong(0);
                String productName = cursor.getString(1);
                float weight = cursor.getFloat(2);
                int recycle_instructions = cursor.getInt(3);

                product.setId(productID);
                product.setProductName(productName);
                product.setWeight(weight);
                product.setRecycle_instructions(recycle_instructions);
            }while(cursor.moveToNext());
        }else{
            //failure to fetch from database
        }
        cursor.close();
        closeDatabase();
        return product;
    }

    //unused method
/*
    public List<ProductDAO> viewAllProducts(){
        openDatabase();
        List<ProductDAO> returnList = new ArrayList<>();
        //get data
        String queryString = "SELECT * FROM " + PRODUCT_TABLE;
        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = this.db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                long productId = cursor.getLong(0);
                String productName = cursor.getString(1);
                int weight = cursor.getInt(2);
                int recycle_instructions = cursor.getInt(3);


                ProductDAO newProduct = new ProductDAO(productId, productName, weight, recycle_instructions);
                returnList.add(newProduct);
            }while(cursor.moveToNext());
        }else{
            //failure
        }
        cursor.close();
        closeDatabase();
        return returnList;
    }
*/

    //gets a list of all products in the database
    public List<String> viewAllProductsNames(){
        openDatabase();
        List<String> returnList = new ArrayList<>();
        //get data
        String queryString = "SELECT PRODUCT_NAME FROM " + PRODUCT_TABLE;
        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = this.db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String productName = cursor.getString(0);

                returnList.add(productName);
            }while(cursor.moveToNext());
        }else{
            //failure
        }
        cursor.close();
        closeDatabase();
        return returnList;
    }

    //returns user data from the database
    public UserDAO getUserInfo(){
        openDatabase();
        //emtpy user dao
        UserDAO userDAO;
        //sql querry statment
        String queryString = "SELECT * FROM " + USER_TABLE;
        Cursor cursor = this.db.rawQuery(queryString, null);
        //goes through the cursor and assings values from the cursor to the user
        if(cursor.moveToFirst()){
            do{
                int timesRecycled = cursor.getInt(0);
                float weightRecycled = cursor.getFloat(1);
                userDAO = new UserDAO(timesRecycled, weightRecycled);
            }while(cursor.moveToNext());
        }else{
            userDAO = new UserDAO(0, 0);
        }
        cursor.close();
        closeDatabase();
        return userDAO;
    }

    public void incrementAwardData(float weight){
        openWriteableDatabase();

        //String queryString = "UPDATE " + USER_TABLE + " SET " + COLUMN_AMOUNT_RECYCLED + " = " + COLUMN_AMOUNT_RECYCLED + " 1";
        //update number of times recycled
        String queryString = "UPDATE USER SET AMOUNT_RECYCLED = AMOUNT_RECYCLED + 1";
        db.execSQL(queryString);


        //update amount of wieght
        queryString = ("UPDATE " + USER_TABLE + " SET " + COLUMN_WEIGHT_RECYCLED + " = " + COLUMN_WEIGHT_RECYCLED + " + " + Float.toString(weight));
        db.execSQL(queryString);

        closeDatabase();

    }

}
