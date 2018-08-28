package com.example.daniyal.govava.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Pc on 4/19/2018.
 */

public class FavouritesDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "favourites.db";

    public static final String TABLE_NAME = "favourites";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IMG = "img";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SERVICE_NAME = "serviceName";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_PRICE + " TEXT,"
                    + COLUMN_IMG + " TEXT," + COLUMN_SERVICE_NAME + " TEXT)";


    Context context;

    public FavouritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.d("2121", "db created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);

    }

    public long insertFavourite(String name, String price, String image, String serviceName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_IMG, image);
        values.put(COLUMN_SERVICE_NAME, serviceName);

        long id = db.insert(TABLE_NAME, null, values);
        Log.d("2121", "value inserted");
        if (id != -1) {
            Toast.makeText(context, "Inserted Successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();

        }
        db.close();
        return id;
    }

    public Cursor getAllContacts() {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }


    public boolean deleteContact(String name, String price, String img_men, String serviceName) {
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_NAME,
                COLUMN_NAME + "=? AND " +
                        COLUMN_PRICE + "=? AND " +
                        COLUMN_IMG + "=? AND " +
                        COLUMN_SERVICE_NAME + "=?",
                new String[]{name, price, img_men, serviceName}) > 0;

        // database.close();
    }

}
