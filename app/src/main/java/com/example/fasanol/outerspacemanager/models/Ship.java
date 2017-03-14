package com.example.fasanol.outerspacemanager.models;

/**
 * Created by mac1 on 14/03/2017.
 */


public class Ship {
    private int shipId;
    private String name;
    private int life;
    private int gasCost;
    private int mineralCost;
    private int maxAttack;
    private int minAttack;
    private int shield;
    private int spatioportLevelNeeded;
    private int speed;
    private int timeToBuild;

    public int getShipId() {
        return shipId;
    }

    public String getName() {
        return name;
    }

    public int getLife() {
        return life;
    }

    public int getGasCost() {
        return gasCost;
    }

    public int getMineralCost() {
        return mineralCost;
    }

    public int getMaxAttack() {
        return maxAttack;
    }

    public int getMinAttack() {
        return minAttack;
    }

    public int getShield() {
        return shield;
    }

    public int getSpatioportLevelNeeded() {
        return spatioportLevelNeeded;
    }

    public int getSpeed() {
        return speed;
    }

    public int getTimeToBuild() {
        return timeToBuild;
    }
}