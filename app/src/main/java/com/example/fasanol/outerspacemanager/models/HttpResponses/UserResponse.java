package com.example.fasanol.outerspacemanager.models.HttpResponses;

import com.example.fasanol.outerspacemanager.models.Building;
import com.example.fasanol.outerspacemanager.models.SimpleUser;

import java.util.ArrayList;

/**
 * Created by mac1 on 14/03/2017.
 */

public class UserResponse {
    private ArrayList<SimpleUser> users;

    public ArrayList<String> getUsersInfos(){
        ArrayList<String> infos = new ArrayList<String>();
        for (SimpleUser x : this.users){
            infos.add(x.getUsername()+"\n"+x.getPoints()+" points");
        }

        return infos;
    }
}
