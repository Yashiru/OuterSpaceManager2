package com.example.fasanol.outerspacemanager.models;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * Created by mac1 on 20/03/2017.
 */

public class ApplicationDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "OSMLocalDB";
    public static final String ATTACK_TABLE_NAME = "Attack";
    public static final String KEY_INITTIMESTAMP = "timeStamp";
    public static final String KEY_ENDTIMESTAMP = "endtimeStamp";
    public static final String KEY_JSONFLEET = "jsonFleet";
    private static final String ATTACK_TABLE_CREATE = "CREATE TABLE " + ATTACK_TABLE_NAME + " (" + KEY_JSONFLEET + " TEXT, " + KEY_ENDTIMESTAMP + " LONG, " + KEY_INITTIMESTAMP + " LONG);";


    public ApplicationDB(Context context) {
        super(context, Environment.getExternalStorageDirectory()+"/"+DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ATTACK_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ATTACK_TABLE_CREATE);
        onCreate(db);
    }
}
