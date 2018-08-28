package com.example.daniyal.govava.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AST on 8/28/2018.
 */

public class ContactsDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contacts.db";
    public static final String TABLE_NAME = "contacts";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IMG = "img";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SERVICE_NAME = "serviceName";
    public static final String COLUMN_MOBILE_NO = "mobileNo";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_PRICE + " TEXT,"
                    + COLUMN_IMG + " TEXT," + COLUMN_SERVICE_NAME + " TEXT," + COLUMN_MOBILE_NO + " TEXT)";

    public ContactsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertContactGift(String name, String price, String image, String serviceName, String mobileNo) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_PRICE, price);
        contentValues.put(COLUMN_IMG, image);
        contentValues.put(COLUMN_SERVICE_NAME, serviceName);
        contentValues.put(COLUMN_MOBILE_NO, mobileNo);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    public Cursor getContactGifts(String mobileNo) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "+COLUMN_MOBILE_NO+" = "+mobileNo;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        return cursor;
    }

    public boolean deleteContactGifts(String name, String price, String image, String serviceName, int mobileNo) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        return sqLiteDatabase.delete(TABLE_NAME, COLUMN_NAME + "=? AND " +
                        COLUMN_PRICE + "=? AND " + COLUMN_IMG + "=? AND " + COLUMN_SERVICE_NAME + "=? AND " + COLUMN_MOBILE_NO + "=?"
                , new String[]{name, price, image, serviceName, String.valueOf(mobileNo)}) > 0;

    }


}
