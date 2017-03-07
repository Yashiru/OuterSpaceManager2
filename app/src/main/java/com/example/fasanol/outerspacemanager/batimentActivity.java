package com.example.fasanol.outerspacemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.fasanol.outerspacemanager.interfaces.UserInterface;
import com.example.fasanol.outerspacemanager.models.Building;
import com.example.fasanol.outerspacemanager.models.BuildingResponse;
import com.example.fasanol.outerspacemanager.models.UserConnected;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class batimentActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences settings;
    private String token;
    private ListView listBatiment;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batiment);
        this.addButton = (Button) findViewById(R.id.addButton);

        this.listBatiment = (ListView) findViewById(R.id.listBatiment);
        this.settings = getSharedPreferences("apiSettings", 0);
        this.token = settings.getString("token", "");
        this.addButton.setOnClickListener(this);

        Retrofit ret = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserInterface service = ret.create(UserInterface.class);
        Call<BuildingResponse> request = service.getBuildings(token);
        request.enqueue(new Callback<BuildingResponse>() {
            @Override
            public void onResponse(Call<BuildingResponse> call, Response<BuildingResponse> response) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(batimentActivity.this,
                        android.R.layout.simple_list_item_1, response.body().getBuildingsName());

                listBatiment.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<BuildingResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.addButton:
                Intent myIntent = new Intent(getApplicationContext(), batimentAddActivity.class);
                startActivity(myIntent);
                break;
        }
    }


}
