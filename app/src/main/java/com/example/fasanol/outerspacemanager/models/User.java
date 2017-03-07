package com.example.fasanol.outerspacemanager.models;

import android.text.Editable;
import android.util.Log;

/**
 * Created by mac1 on 07/03/2017.
 */

public class User {
    private String username;
    private String password;
    private String token;
    private int expiration;

    public User(Editable id, Editable pass){
        this.username = id.toString();
        this.password = pass.toString();
    }

    public String getToken(){
        return token;
    }

    public String getUsername(){
        return username;
    }

}


/*

{"users":{"Hdjd":{"points":0,"username":"Hdjd"},"alan":{"points":1040,"username":"alan"},"alansearch":{"points":630,"username":"alansearch"}}}

*/