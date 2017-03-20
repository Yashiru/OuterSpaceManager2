package com.example.fasanol.outerspacemanager.models;

import com.google.gson.Gson;

/**
 * Created by mac1 on 20/03/2017.
 */

public class RowAttackList {
    private long initialTimeStamp;
    private long EndTimeStamp;
    private String jsonFleet;

    public RowAttackList(int initialTimeStamp, long date) {
        this.initialTimeStamp = initialTimeStamp;
    }

    public long getInitialTimeStamp() {
        return initialTimeStamp;
    }

    public String getJsonFleet() {
        return jsonFleet;
    }

    public void setInitialTimeStamp(int initialTimeStamp) {
        this.initialTimeStamp = initialTimeStamp;
    }

    public void setJsonFleet(String jsonFleet) {
        this.jsonFleet = jsonFleet;
    }

    public RowAttackList() {

    }
}
