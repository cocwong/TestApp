package com.test.testapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLite extends SQLiteOpenHelper {
    private static final int VERSION = 2;
    private static final String NAME = "stub.db";

    private final String TABLE_NAME = "per";

    public MySQLite(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (_id integer PRIMARY KEY AUTOINCREMENT,name varchar(10))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 2) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD age integer");
        }
        if (newVersion == 3) {
//            db.execSQL("ALTER TABLE " + TABLE_NAME + " DROP COLUMN name");
        }
    }
}
