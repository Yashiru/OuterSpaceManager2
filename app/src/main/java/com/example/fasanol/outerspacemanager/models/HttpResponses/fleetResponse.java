package com.example.fasanol.outerspacemanager.models.HttpResponses;

import android.util.Log;

import com.example.fasanol.outerspacemanager.models.Building;
import com.example.fasanol.outerspacemanager.models.FleetShip;
import com.example.fasanol.outerspacemanager.models.SelectedShips;

import java.util.ArrayList;

/**
 * Created by mac1 on 14/03/2017.
 */

public class fleetResponse {
    private int size;
    private ArrayList<FleetShip> ships;

    public ArrayList<FleetShip> getShips() {
        return ships;
    }

    public ArrayList<String> getFleetInfos(){
        ArrayList<String> infos = new ArrayList<String>();
        for (FleetShip x : this.ships){
            infos.add(x.getAmount()+" "+x.getName());
        }

        return infos;
    }

    public ArrayList<String> getFleetNames(){
        ArrayList<String> infos = new ArrayList<String>();
        for (FleetShip x : this.ships){
            infos.add(x.getName());
        }

        return infos;
    }


    public ArrayList<SelectedShips> gatSelectedShipDefault(){
        ArrayList<SelectedShips> infos = new ArrayList<SelectedShips>();
        for (FleetShip x : this.ships){
            SelectedShips ship = new SelectedShips(0, x.getShipId());
            infos.add(ship);
        }

        return infos;
    }

    public ArrayList<String> gatSelectedShipDefaultString(){
        ArrayList<String> infos = new ArrayList<String>();
        for (FleetShip x : this.ships){
            infos.add(0 + " " + x.getName());
        }

        return infos;
    }
}
