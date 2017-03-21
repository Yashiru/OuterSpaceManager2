package com.example.fasanol.outerspacemanager.models;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;

import com.example.fasanol.outerspacemanager.interfaces.ReportInterface;
import com.example.fasanol.outerspacemanager.models.HttpResponses.ReportsReponse;
import com.google.gson.Gson;

import org.joda.time.DateTime;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mac1 on 20/03/2017.
 */

public class RowAttackList {
    private long initialTimeStamp;
    private long endTimeStamp;
    private String jsonFleet;
    private String username;
    private String user;

    public RowAttackList(long initialTimeStamp, final long endTimeStamp, String jsonFleet, Context context) {
        this.initialTimeStamp = initialTimeStamp;
        this.endTimeStamp = endTimeStamp;
        this.jsonFleet = jsonFleet;
    }

    public long getInitialTimeStamp() {
        return initialTimeStamp;
    }

    public long getEndTimeStamp() {
        return endTimeStamp;
    }

    public String getJsonFleet() {
        return jsonFleet;
    }

    public String getFleetString(){
        Gson json = new Gson();
        ShipListToAttack ship;
        String retour = "Flotte envoyée chez "+this.user+"\n\n";
        shipFactory fa = new shipFactory();
        String endDate = "";

        ship = json.fromJson(this.jsonFleet, ShipListToAttack.class);

        for(MinimalShip s : ship.getShips())
        {
            retour += s.getAmount() + " " + fa.shipsNames[s.getShipId()] + "\n";
        }
        DateTime date = new DateTime(this.endTimeStamp);
        String day = date.dayOfMonth().getAsText();
        String month = date.monthOfYear().get()+"";
        String hours = date.getHourOfDay()+"";
        String minutes = date.getMinuteOfHour()+"";
        String secondes = date.getSecondOfMinute()+"";
        String whiteSpace = "                                       ";
        if(day.length() < 2){ day = "0"+day; }
        if(month.length() < 2){ month = "0"+month; }
        if(hours.length() < 2){ hours = "0"+hours; }
        if(minutes.length() < 2){ minutes = "0"+minutes; }
        if(secondes.length() < 2){ secondes = "0"+secondes; }

        retour += "\nAvancement:\n"+this.getProgress()+"%"+whiteSpace + "Terminée le " + day + " / " + month + " à " + hours + ":" + minutes + ":" + secondes;

        return retour;
    }

    public long getProgress(){
        long progress;
        long diff = this.endTimeStamp - this.initialTimeStamp;
        long now = System.currentTimeMillis();
        long past = now - this.initialTimeStamp;

        progress = (long)((float) past / diff * 100);

        if(progress > 100)
        {
            progress = 100;
        }

        return progress;
    }

    public void setInitialTimeStamp(long initialTimeStamp) {
        this.initialTimeStamp = initialTimeStamp;
    }

    public void setJsonFleet(String jsonFleet) {
        this.jsonFleet = jsonFleet;
    }

    public void setEndTimeStamp(long end)
    {
        this.endTimeStamp = end;
    }

    public RowAttackList() {

    }

    public void setUser(String user) {
        this.user = user;
    }
}
