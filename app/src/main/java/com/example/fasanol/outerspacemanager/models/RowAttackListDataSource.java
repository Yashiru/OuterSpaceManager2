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
    private String[] allColumns = {  ApplicationDB.KEY_JSONFLEET, ApplicationDB.KEY_INITTIMESTAMP, ApplicationDB.KEY_ENDTIMESTAMP, ApplicationDB.KEY_ATTACKEDUSER };

    public RowAttackListDataSource(Context context) {
        dbHelper = new ApplicationDB(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    public RowAttackList createAttack(String jsonFleet,long initialTimeStamp, long endTimestamp, String attackedUser) {
        ContentValues values = new ContentValues();
        values.put(ApplicationDB.KEY_JSONFLEET, jsonFleet);
        values.put(ApplicationDB.KEY_INITTIMESTAMP, initialTimeStamp);
        values.put(ApplicationDB.KEY_ENDTIMESTAMP, endTimestamp);
        values.put(ApplicationDB.KEY_ATTACKEDUSER, attackedUser);
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
            if(row.getProgress() < 100)
            {
                retour.add(row);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return retour;
    }

    private RowAttackList cursorToRowAttackList(Cursor cursor) {
        RowAttackList row = new RowAttackList();
        row.setJsonFleet(cursor.getString(0));
        row.setInitialTimeStamp(cursor.getLong(1));
        row.setEndTimeStamp(cursor.getLong(2));
        row.setUser(cursor.getString(3));
        return row;
    }


}
