package com.example.fasanol.outerspacemanager.models;

import java.util.ArrayList;

/**
 * Created by mac1 on 20/03/2017.
 */

public class Report {
    private ArrayList<SelectedShips> attackerFleet;
    private FleetAfterBattle attackerFleetAfterBattle;
    private long date;
    private long dateInv;
    private ArrayList<FleetShip> defenderFleet;
    private FleetAfterBattle defenderFleetAfterBattle;
    private String from;
    private int gasWon;
    private int mineralsWon;
    private String to;
    private String type;

    public ArrayList<SelectedShips> getAttackerFleet() {
        return attackerFleet;
    }

    public long getDate() {
        return date;
    }

    public long getDateInv() {
        return dateInv;
    }

    public ArrayList<FleetShip> getDefenderFleet() {
        return defenderFleet;
    }

    public FleetAfterBattle getDefenderFleetAfterBattle() {
        return defenderFleetAfterBattle;
    }

    public String getFrom() {
        return from;
    }

    public int getGasWon() {
        return gasWon;
    }

    public int getMineralsWon() {
        return mineralsWon;
    }

    public String getTo() {
        return to;
    }

    public String getType() {
        return type;
    }

}
