package com.example.fasanol.outerspacemanager.models;

import com.example.fasanol.outerspacemanager.models.SelectedShips;

import java.util.ArrayList;

/**
 * Created by mac1 on 20/03/2017.
 */

public class FleetAfterBattle {
    private int capacity;
    private int survivingShips;
    private ArrayList<FleetShip> fleet;

    public ArrayList<FleetShip> getFleet() {
        return fleet;
    }
}
