package com.example.fasanol.outerspacemanager.models;

/**
 * Created by mac1 on 14/03/2017.
 */

public class MinimalShip {
    private int shipId;
    private int amount;

    public MinimalShip(int shipeId, int amount) {
        this.shipId = shipeId;
        this.amount = amount;
    }

    public void setShipeId(int shipeId) {

        this.shipId = shipeId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
