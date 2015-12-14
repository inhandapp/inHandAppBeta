package com.inhand.inhandappbeta;


/**
 * Created by ACH on 12/10/2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;



public class InHandDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "inhand.db";
    public static final String TABLE_eBayItemS = "eBayItems";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "eBayItemTitle";
    public static final String COLUMN_PRICE = "eBayItemPrice";
    public static final String COLUMN_URL = "eBayItemURL";

    public SQLiteDatabase db;

    public InHandDBHandler(Context context, String name,
                           SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create query
        String query = "CREATE TABLE " + TABLE_eBayItemS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +  COLUMN_PRICE + " TEXT " +
                COLUMN_URL + " TEXT " + ");";
        // execute SQL
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_eBayItemS);
        onCreate(db);
    }

    // Add new row to the database
    public void addeBayItems(eBayItem eBayItem) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, eBayItem.getTitle());
        values.put(COLUMN_PRICE, eBayItem.getPrice());
        values.put(COLUMN_URL, eBayItem.getLink());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_eBayItemS, null, values);
        db.close();
    }

    // Delete a product from the database
    public void deleteeBayItems (String eBayItemTitle) {
        SQLiteDatabase db = getWritableDatabase(); // get reference to that database
        db.execSQL("DELETE FROM " + TABLE_eBayItemS + " WHERE " +
                COLUMN_TITLE + "=\"" + eBayItemTitle + "\";");
    }

    // Print out the database as a string
    public List<eBayItem> databaseToString() {
        List<eBayItem> eBayItems = new ArrayList<eBayItem>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_eBayItemS + " WHERE 1";

        // cursor point to a location in your results
        Cursor c = db.rawQuery(query, null);
        // move to the first row in your results
        c.moveToFirst();

        while(!c.isAfterLast()) {
            eBayItem eBayItem = cursorToeBayItem(c);
            eBayItems.add(eBayItem);
            c.moveToNext();
        }

        db.close();
        return eBayItems;
    }

    private eBayItem cursorToeBayItem(Cursor c) {
        eBayItem eBayItem = new eBayItem();
        eBayItem.set_id(c.getInt(0));
        eBayItem.setTitle(c.getString(1));
        eBayItem.setPrice(c.getString(2));
        eBayItem.setLink(c.getString(3));
        return eBayItem;
    }



}
