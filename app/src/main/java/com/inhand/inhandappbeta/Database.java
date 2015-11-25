package com.inhand.inhandappbeta;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Patrick on 11/25/15.
 */
public class Database extends SQLiteOpenHelper {


    /************************* NOT SCALABLE***************************/

    public class MyDBHandler {

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "products.db";
        public static final String TABLE_PRODUCTS = "products";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_PRODUCTNAME = "productname";


        public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            //Create query
            String query = "CREATE TABLE " + TABLE_PRODUCTS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY " +
                    " AUTOINCREMENT, " + COLUMN_PRODUCTNAME + " TEXT" + ");";

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
        public void addProduct (Products product){

            ContentValues values = new ContentValues();
            values.put(COLUMN_PRODUCTNAME, product.get_productname());
            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_PRODUCTS, null, values);
            db.close();
        }

        //Delete product from the database
        public void deleteProduct (String productName){
            SQLiteDatabase db = getWritableDatabase();   //gets reference to that database
            db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + " = \""
                    + productName + "\";");
        }

        //Print db as a string
        public List<Products> databaseToString() {
            List<Products> products = new ArrayList<Products>();
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";

            //Cursor points to a location in your results
            Cursor c = db.rawQuery(query, null);

            //Move to the first row in results
            c.moveToFirst();

            while(!c.isAfterLast()){
                Products product = cursorToProduct(c);

                products.add(product);

                c.moveToNext();
            }
            db.close();
            return products;
        }

        private Products cursorToProduct(Cursor c){
            Products product = new Products();
            product.set_id(c.getInt(0));
            product.set_productname((c.getString(1)));

            return product;
        }


    }



}
