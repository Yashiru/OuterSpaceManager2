package com.example.fasanol.outerspacemanager.models.HttpResponses;

import com.example.fasanol.outerspacemanager.batimentActivity;
import com.example.fasanol.outerspacemanager.models.Building;
import com.example.fasanol.outerspacemanager.models.Ship;

import java.util.ArrayList;

/**
 * Created by mac1 on 14/03/2017.
 */

public class ShipResponse {
    private int size;
    private ArrayList<Ship> ships;

    public ArrayList<String> getShipInfo(){
        ArrayList<String> infos = new ArrayList<String>();
        for (Ship x : this.ships){
            infos.add(x.getName()+"\nGaz: "+x.getGasCost()+" L\n Minerals:"+x.getMineralCost()+" Kg");
        }

        return infos;
    }

    public ArrayList<String> getShipNames(){
        ArrayList<String> infos = new ArrayList<String>();
        for (Ship x : this.ships){
            infos.add(x.getName());
        }

        return infos;
    }
}
