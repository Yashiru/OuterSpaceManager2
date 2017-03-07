package com.example.fasanol.outerspacemanager.models;

import android.text.Editable;

/**
 * Created by mac1 on 07/03/2017.
 */

public class UserConnected {
    private double gas;
    private int gasModifier;
    private double minerals;
    private int mineralsModifier;
    private int points;
    private String username;

    public UserConnected(Editable username){
        this.username = username.toString();
    }

    public String getUsername(){
        return this.username;
    }

    public String getPoints(){
        return String.valueOf(this.points);
    }
}

