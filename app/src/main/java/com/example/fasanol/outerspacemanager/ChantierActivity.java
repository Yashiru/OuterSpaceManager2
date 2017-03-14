package com.example.fasanol.outerspacemanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fasanol.outerspacemanager.interfaces.ShipInterface;
import com.example.fasanol.outerspacemanager.models.HttpResponses.ShipResponse;
import com.example.fasanol.outerspacemanager.models.HttpResponses.httpSimpleResponse;
import com.example.fasanol.outerspacemanager.models.shipQueryObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChantierActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private RecyclerView recyclerView;
    private Retrofit ret = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private ProgressDialog mProgressDialog;
    private SharedPreferences settings;
    private String token;
    private ListView shipList;
    private ArrayList<String> shipsNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chantier);
        mProgressDialog = ProgressDialog.show(this, "",
                "Loading ship list", true);
        this.settings = getSharedPreferences("apiSettings", 0);
        this.token = settings.getString("token", "");
        this.shipList = (ListView) findViewById(R.id.shipList);
        this.shipList.setOnItemClickListener(this);


        ShipInterface service = ret.create(ShipInterface.class);
        Call<ShipResponse> request = service.getShipList(token);
        request.enqueue(new Callback<ShipResponse>() {
            @Override
            public void onResponse(Call<ShipResponse> call, Response<ShipResponse> response) {
                mProgressDialog.dismiss();
                if(response.isSuccessful())
                {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChantierActivity.this,
                            android.R.layout.simple_list_item_1, response.body().getShipInfo());
                    shipList.setAdapter(adapter);
                    shipsNames = response.body().getShipNames();

                }


            }

            @Override
            public void onFailure(Call<ShipResponse> call, Throwable t) {

            }
        });

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        ShipInterface service = ret.create(ShipInterface.class);
        Call<httpSimpleResponse> request = service.createShip(token, position, new shipQueryObject(1));
        request.enqueue(new Callback<httpSimpleResponse>() {
            @Override
            public void onResponse(Call<httpSimpleResponse> call, Response<httpSimpleResponse> response) {
                if(response.isSuccessful())
                {
                    Context context = getApplicationContext();
                    CharSequence text = shipsNames.get(position)+" prèt au combat";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }


            }

            @Override
            public void onFailure(Call<httpSimpleResponse> call, Throwable t) {

            }
        });
    }
}
