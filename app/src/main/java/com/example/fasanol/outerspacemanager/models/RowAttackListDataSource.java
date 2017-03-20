package com.example.fasanol.outerspacemanager.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by mac1 on 20/03/2017.
 */

public class RowAttackListDataSource {
    private SQLiteDatabase database;
    private ApplicationDB dbHelper;
    private String[] allColumns = { ApplicationDB.KEY_INITTIMESTAMP, ApplicationDB.KEY_JSONFLEET, ApplicationDB.KEY_ENDTIMESTAMP };

    public RowAttackListDataSource(Context context) {
        dbHelper = new ApplicationDB(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    public RowAttackList createAttack(String jsonFleet,long initialTimeStamp, long endTimestamp) {
        ContentValues values = new ContentValues();
        values.put(ApplicationDB.KEY_JSONFLEET, jsonFleet);
        values.put(ApplicationDB.KEY_INITTIMESTAMP, initialTimeStamp);
        values.put(ApplicationDB.KEY_ENDTIMESTAMP, endTimestamp);
        database.insert(ApplicationDB.ATTACK_TABLE_NAME, null,
                values);
        Cursor cursor = database.query(ApplicationDB.ATTACK_TABLE_NAME,
                allColumns, ApplicationDB.KEY_INITTIMESTAMP + " = \"" + initialTimeStamp +"\"", null,
                null, null, null);
        cursor.moveToFirst();
        RowAttackList newRow = cursorToRowAttackList(cursor);
        cursor.close();
        return newRow;
    }

    public void deleteAttack(RowAttackList attack)
    {
        database.delete(ApplicationDB.ATTACK_TABLE_NAME, ApplicationDB.KEY_INITTIMESTAMP + " = \"" + attack.getInitialTimeStamp() + "\"", null);
    }

    public ArrayList<RowAttackList> getAllAttacks(){
        ArrayList<RowAttackList> retour = new ArrayList<RowAttackList>();
        Cursor cursor = database.query(ApplicationDB.ATTACK_TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            RowAttackList row = cursorToRowAttackList(cursor);
            retour.add(row);
            cursor.moveToNext();
        }
        cursor.close();
        return retour;
    }

    private RowAttackList cursorToRowAttackList(Cursor cursor) {
        RowAttackList row = new RowAttackList();
        row.setInitialTimeStamp(cursor.getInt(0));
        row.setJsonFleet(cursor.getString(1));
        return row;
    }


}
