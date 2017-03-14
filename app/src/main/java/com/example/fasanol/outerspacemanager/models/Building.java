package com.example.fasanol.outerspacemanager.models;

/**
 * Created by mac1 on 07/03/2017.
 */

public class Building {
    private int amountOfEffectByLevel;
    private int amountOfEffectLevel0;
    private Boolean building;
    private String effect;
    private int gasCostByLevel;
    private int gasCostLevel0;


    private int level;
    private int mineralCostByLevel;
    private int mineralCostLevel0;
    private String name;
    private int timeToBuildByLevel;
    private int timeToBuildLevel0;

    public String getName(){
        return this.name;
    }

    public int getGasCost(){
        return this.gasCostByLevel;
    }

    public int getMineralCost(){
        return this.mineralCostByLevel;
    }

    public int getAmountOfEffectByLevel(){
        return this.amountOfEffectByLevel;
    }

    public int getLevel() { return level; }

    public int getGasCostToUp(){
        return this.gasCostLevel0 + this.gasCostByLevel * this.level;
    }

    public int getMineralCostToUp(){
        return this.mineralCostLevel0 + this.mineralCostByLevel * this.level;
    }

    public Boolean isBuilding(){
        return this.building;
    }
}
