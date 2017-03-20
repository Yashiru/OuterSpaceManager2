package com.example.fasanol.outerspacemanager.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mac1 on 20/03/2017.
 */

public class SelectedShips implements Serializable {
    private int shipId;
    private int amount;

    public SelectedShips(int amount, int shipId) {
        this.amount = amount;
        this.shipId = shipId;
    }

    public int getShipId() {
        return shipId;
    }

    public int getAmount() {
        return amount;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
