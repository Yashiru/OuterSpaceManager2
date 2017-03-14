package com.example.fasanol.outerspacemanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fasanol.outerspacemanager.interfaces.UserInterface;
import com.example.fasanol.outerspacemanager.models.HttpResponses.BuildingResponse;
import com.example.fasanol.outerspacemanager.models.BuildingsAdapter;
import com.example.fasanol.outerspacemanager.models.HttpResponses.httpSimpleResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class batimentActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private SharedPreferences settings;
    private String token;
    private ListView listBatiment;
    private Button addButton;
    private ArrayList<String> buildings;
    private Retrofit ret = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private ProgressDialog mProgressDialog;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mProgressDialog = ProgressDialog.show(this, "",
                "Loading buildings", true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batiment);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.settings = getSharedPreferences("apiSettings", 0);
        this.token = settings.getString("token", "");

        UserInterface service = ret.create(UserInterface.class);
        Call<BuildingResponse> request = service.getBuildings(token);
        request.enqueue(new Callback<BuildingResponse>() {
            @Override
            public void onResponse(Call<BuildingResponse> call, Response<BuildingResponse> response) {
                mProgressDialog.dismiss();
                BuildingsAdapter adapter = new BuildingsAdapter(response.body().getBuildings(), batimentActivity.this);
                buildings = response.body().getBuildingsName();
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<BuildingResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id){

        new AlertDialog.Builder(this)
                .setTitle("Construire un nouveau bâtiment ")
                .setMessage("Êtes-vous sure de vouloire construire un(e) "+buildings.get(position)+"?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        UserInterface service = ret.create(UserInterface.class);
                        Call<httpSimpleResponse> request = service.createBuilding(token, position);
                        request.enqueue(new Callback<httpSimpleResponse>() {
                            @Override
                            public void onResponse(Call<httpSimpleResponse> call, Response<httpSimpleResponse> response) {
                                Context context = getApplicationContext();
                                CharSequence text = buildings.get(position)+" crée(e)";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }

                            @Override
                            public void onFailure(Call<httpSimpleResponse> call, Throwable t) {

                            }
                        });
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_input_add)
                .show();
    }

    /**
     * Created by mac1 on 14/03/2017.
     */

}
