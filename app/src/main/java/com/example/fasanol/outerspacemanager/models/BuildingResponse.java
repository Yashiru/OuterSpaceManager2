package com.example.fasanol.outerspacemanager.models;

import java.util.ArrayList;

/**
 * Created by mac1 on 07/03/2017.
 */

public class BuildingResponse {
    private int size;
    private ArrayList<Building> buildings;


    public ArrayList<Building> getBuildings(){
        return this.buildings;
    }

    public ArrayList<String> getBuildingsName(){
        ArrayList<String> names = new ArrayList<String>();
        for (Building x : this.buildings){
            names.add(x.getName());
        }

        return names;
    }
}

