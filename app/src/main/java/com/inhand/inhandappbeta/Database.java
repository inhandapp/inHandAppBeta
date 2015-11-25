package com.inhand.inhandappbeta;

import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserSearches.db";
    public static final String TABLE_PRODUCTS = "Search";
    public static final String COLUMN_SearchString = "User String";


    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create query
        String query = "CREATE TABLE " + TABLE_PRODUCTS + " (" +  " INTEGER PRIMARY KEY " +
                " AUTOINCREMENT, " + COLUMN_SearchString + " TEXT" + ");";

        //Execute query
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Delete table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);

        //Create table
        onCreate(db);
    }

    //Add new row to database
    public void addProduct (UserQuery UserQuery){

        ContentValues values = new ContentValues();
        values.put(COLUMN_SearchString, UserQuery.get_searchString());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    //Delete product from the database
    public void deleteProduct (String productName){
        SQLiteDatabase db = getWritableDatabase();   //gets reference to that database
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_SearchString + " = \""
                + productName + "\";");
    }

    //Print db as a string
    public List<UserQuery> databaseToString() {
        List<UserQuery> UserQuery = new ArrayList<UserQuery>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);

        //Move to the first row in results
        c.moveToFirst();

        while(!c.isAfterLast()){
            UserQuery UserQueryCursor = cursorToUserQuery(c);

            UserQuery.add(UserQueryCursor);

            c.moveToNext();
        }
        db.close();
        return UserQuery;
    }

    private UserQuery cursorToUserQuery(Cursor c){
        UserQuery UserQuery = new UserQuery();
        UserQuery.set_searchString((c.getString(1)));

        return UserQuery;
    }


}