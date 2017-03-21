package com.example.fasanol.outerspacemanager.models.HttpResponses;

import android.content.Context;
import android.widget.Toast;

import com.example.fasanol.outerspacemanager.models.MinimalShip;
import com.example.fasanol.outerspacemanager.models.Report;
import com.example.fasanol.outerspacemanager.models.RowAttackList;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by mac1 on 20/03/2017.
 */

public class ReportsReponse {
    private ArrayList<Report> reports;

    public ArrayList<Report> getReportByUsername(String username){
        ArrayList<Report> res = new ArrayList<Report>();
        for(Report report : this.reports){
            if(report.getTo().equals(username)){
                res.add(report);
            }
        }
        return res;
    }

    public ArrayList<String> getReportsInfos(ArrayList<Report> reports){
        ArrayList<String> res = new ArrayList<String>();
        for(Report report : reports){
            long timestamp = report.getDate();
            DateTime date = new DateTime(timestamp);

            String info = " le " + date.dayOfMonth().getAsText() + " / "+date.monthOfYear().getAsText()+" à "+date.hourOfDay().getAsText()+":"+date.minuteOfHour().getAsText()+":"+date.secondOfMinute().getAsText() + "\n\n" +
                    "Gas: " + report.getGasWon() + "\n"+
                    "Minerals: " + report.getMineralsWon() + "\n\n" +
                    "Perte :\n"+report.getLosts();


            res.add(info);
        }
        return res;
    }

    public ArrayList<RowAttackList> getProgressAttack(){
        ArrayList<RowAttackList> retour = new ArrayList<RowAttackList>();

        for(Report report : this.reports){
            //retour.add(new RowAttackList());
        }

        return retour;
    }

    public String getUsernameByAttack(long timestamp){
        String username = "";

        for(Report r : this.reports){
            if(r.getDate() == timestamp)
            {
                username = r.getTo();
                break;
            }
        }

        return username;
    }
}
