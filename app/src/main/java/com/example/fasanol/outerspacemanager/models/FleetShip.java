package com.example.fasanol.outerspacemanager.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mac1 on 14/03/2017.
 */

public class FleetShip implements Serializable{
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setGasCost(int gasCost) {
        this.gasCost = gasCost;
    }

    public void setMineralCost(int mineralCost) {
        this.mineralCost = mineralCost;
    }

    public void setMaxAttack(int maxAttack) {
        this.maxAttack = maxAttack;
    }

    public void setMinAttack(int minAttack) {
        this.minAttack = minAttack;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public void setSpatioportLevelNeeded(int spatioportLevelNeeded) {
        this.spatioportLevelNeeded = spatioportLevelNeeded;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setTimeToBuild(int timeToBuild) {
        this.timeToBuild = timeToBuild;
    }

    private int amount;
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

    public int getAmount() {
        return this.amount;
    }

    public int getTimeToBuild() {
        return timeToBuild;
    }

    public int getShipId() {
        return shipId;
    }

    public String getName() {
        return this.name;
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

}
