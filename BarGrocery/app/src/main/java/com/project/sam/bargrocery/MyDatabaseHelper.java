package com.project.sam.bargrocery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Samantha Hamor on 3/15/2015.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_ITEMS = "items";
    public static final String COL_ID = BaseColumns._ID;
    public static final String COL_BRAND = "brand";
    public static final String COL_PRODUCT = "product";
    public static final String COL_PRICE = "price";
    private static final String DATABASE_NAME = "my_app.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_ITEMS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_BRAND + " TEXT,"
                + COL_PRODUCT + " TEXT NOT NULL,"
                + COL_PRICE + " INTEGER"
                + ");");

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS + ";");

        onCreate(db);

    }

    public long insert(String tableName, ContentValues values) throws NotValidException {

        validate(values);

        return getWritableDatabase().insert(tableName, null, values);

    }

    public int update(String tableName, long id, ContentValues values) throws NotValidException {

        validate(values);

        String selection = COL_ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        return getWritableDatabase().update(tableName, values, selection, selectionArgs);

    }

    public int delete(String tableName, long id) {

        String selection = COL_ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        return getWritableDatabase().delete(tableName, selection, selectionArgs);

    }

    protected void validate(ContentValues values) throws NotValidException {

        if (!values.containsKey(COL_BRAND) || values.getAsString(COL_BRAND) == null || values.getAsString(COL_BRAND).isEmpty()) {

            throw new NotValidException("brand must be set");

        }

    }

    public static class NotValidException extends Throwable {

        public NotValidException(String msg) {

            super(msg);

        }

    }

    public Cursor query(String tableName, String orderedBy) {

        String[] projection = {COL_ID, COL_BRAND, COL_PRODUCT, COL_PRICE};

        return getReadableDatabase().query(tableName, projection, null, null, null, null, orderedBy);

    }

}
