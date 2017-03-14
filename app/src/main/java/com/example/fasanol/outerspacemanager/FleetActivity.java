package com.example.fasanol.outerspacemanager;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.fasanol.outerspacemanager.interfaces.ShipInterface;
import com.example.fasanol.outerspacemanager.models.FleetShip;
import com.example.fasanol.outerspacemanager.models.HttpResponses.fleetResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FleetActivity extends AppCompatActivity {
    private SharedPreferences settings;
    private String token;
    private ListView listFleet;
    private Retrofit ret = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private ProgressDialog mProgressDialog;
    private ArrayList<String> fleetInfos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mProgressDialog = ProgressDialog.show(this, "",
            "Loading fleet", true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet);
        this.settings = getSharedPreferences("apiSettings", 0);
        this.token = settings.getString("token", "");

        this.listFleet = (ListView) findViewById(R.id.fleetList);

        ShipInterface service = ret.create(ShipInterface.class);
        Call<fleetResponse> request = service.getFleetList(token);
        request.enqueue(new Callback<fleetResponse>() {
            @Override
            public void onResponse(Call<fleetResponse> call, Response<fleetResponse> response) {
                mProgressDialog.dismiss();
                if(response.isSuccessful())
                {
                    fleetInfos = response.body().getFleetInfos();
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(FleetActivity.this,
                            android.R.layout.simple_list_item_1, response.body().getFleetInfos());
                    listFleet.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<fleetResponse> call, Throwable t) {

            }
        });
    }
}
