package com.example.fasanol.outerspacemanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fasanol.outerspacemanager.interfaces.ShipInterface;
import com.example.fasanol.outerspacemanager.interfaces.UserInterface;
import com.example.fasanol.outerspacemanager.models.FleetShip;
import com.example.fasanol.outerspacemanager.models.HttpResponses.UserResponse;
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
    private ArrayList<String> userNames;
    private Retrofit ret = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private ArrayList<FleetShip> fleet;
    private ArrayList<FleetShip> selectedFleet = new ArrayList<FleetShip>();
    private ArrayList<String> fleetInfos;
    private String token;
    private SharedPreferences settings;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack);
        mProgressDialog = ProgressDialog.show(this, "",
                "Loading fleet", true);
        this.settings = getSharedPreferences("apiSettings", 0);
        this.token = settings.getString("token", "");

        this.myFleet = (ListView) findViewById(R.id.myFleet);
        this.myFleet.setOnItemClickListener(this);

        UserInterface service = ret.create(UserInterface.class);
        Call<UserResponse> request = service.getAllUsers(token);
        request.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                mProgressDialog.dismiss();
                if(response.isSuccessful())
                {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AttackActivity.this,
                            android.R.layout.simple_list_item_1, response.body().getUsersInfos());
                    myFleet.setAdapter(adapter);

                    userNames = response.body().getUserNames();
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("selectedUserToAttack", userNames.get(position));
        editor.commit();

        Intent myIntent = new Intent(getApplicationContext(), AttackUserActivity.class);
        startActivity(myIntent);
    }
}
