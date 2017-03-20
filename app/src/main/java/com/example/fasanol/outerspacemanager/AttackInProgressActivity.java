package com.example.fasanol.outerspacemanager;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fasanol.outerspacemanager.interfaces.ReportInterface;
import com.example.fasanol.outerspacemanager.interfaces.UserInterface;
import com.example.fasanol.outerspacemanager.models.FleetShip;
import com.example.fasanol.outerspacemanager.models.HttpResponses.ReportsReponse;
import com.example.fasanol.outerspacemanager.models.HttpResponses.UserResponse;
import com.example.fasanol.outerspacemanager.models.RowAttackList;
import com.example.fasanol.outerspacemanager.models.RowAttackListDataSource;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AttackInProgressActivity extends AppCompatActivity {
    private ListView myAttacks;

    private Retrofit ret = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private String token;
    private SharedPreferences settings;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack_in_progress);
        mProgressDialog = ProgressDialog.show(this, "",
                "Loading attacks in progress", true);
        this.settings = getSharedPreferences("apiSettings", 0);
        this.token = settings.getString("token", "");

        this.myAttacks = (ListView) findViewById(R.id.attackList);


        RowAttackListDataSource dataSource = new RowAttackListDataSource(getApplicationContext());
        dataSource.open();

        ArrayList<RowAttackList> list = dataSource.getAllAttacks();

        ArrayAdapter<RowAttackList> adapter = new ArrayAdapter<RowAttackList>(AttackInProgressActivity.this,
                android.R.layout.simple_list_item_1, list);
        myAttacks.setAdapter(adapter);

        dataSource.close();
    }
}
