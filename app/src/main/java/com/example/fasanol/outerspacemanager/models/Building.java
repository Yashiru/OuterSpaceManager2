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
}
