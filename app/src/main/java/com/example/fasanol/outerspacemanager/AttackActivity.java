package com.example.fasanol.outerspacemanager;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fasanol.outerspacemanager.interfaces.ShipInterface;
import com.example.fasanol.outerspacemanager.models.FleetShip;
import com.example.fasanol.outerspacemanager.models.HttpResponses.fleetResponse;
import com.example.fasanol.outerspacemanager.models.Ship;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AttackActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView myFleet;
    private TextView usedShips;
    private ArrayList<Ship> ships;
    private Retrofit ret = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private ProgressDialog mProgressDialog;
    private SharedPreferences settings;
    private String token;
    private ArrayList<FleetShip> fleet;
    private ArrayList<FleetShip> selectedFleet = new ArrayList<FleetShip>();
    private ArrayList<String> fleetInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack);
        mProgressDialog = ProgressDialog.show(this, "",
                "Loading fleet", true);

        this.settings = getSharedPreferences("apiSettings", 0);
        this.token = settings.getString("token", "");

        this.myFleet = (ListView) findViewById(R.id.myFleet);
        this.usedShips = (TextView) findViewById(R.id.usedShips);
        this.myFleet.setOnItemClickListener(this);

        ShipInterface service = ret.create(ShipInterface.class);
        Call<fleetResponse> request = service.getFleetList(token);
        request.enqueue(new Callback<fleetResponse>() {
            @Override
            public void onResponse(Call<fleetResponse> call, Response<fleetResponse> response) {
                mProgressDialog.dismiss();
                if(response.isSuccessful())
                {
                    fleetInfos = response.body().getFleetInfos();
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AttackActivity.this,
                            android.R.layout.simple_list_item_1, response.body().getFleetInfos());
                    myFleet.setAdapter(adapter);
                    fleet = response.body().getShips();
                    for (FleetShip x : fleet){
                        selectedFleet.add(x);
                    }
                    for (FleetShip y : selectedFleet){
                        y.setAmount(0);
                    }
                }

            }

            @Override
            public void onFailure(Call<fleetResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = "";
        Log.d("fleet", ""+fleet.get(position).getAmount());
        Log.d("selectedfleet", ""+selectedFleet.get(position).getAmount());
        if(fleet.get(position).getAmount() > selectedFleet.get(position).getAmount())
        {
            selectedFleet.get(position).setAmount(selectedFleet.get(position).getAmount() + 1);
        }

        for (FleetShip x : this.selectedFleet){
            text += x.getAmount() + " " +x.getName()+"\n";
        }

        usedShips.setText(text);
    }
}
