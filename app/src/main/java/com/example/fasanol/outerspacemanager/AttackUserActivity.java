package com.example.fasanol.outerspacemanager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fasanol.outerspacemanager.interfaces.ShipInterface;
import com.example.fasanol.outerspacemanager.interfaces.UserInterface;
import com.example.fasanol.outerspacemanager.models.FleetShip;
import com.example.fasanol.outerspacemanager.models.HttpResponses.AttackResponse;
import com.example.fasanol.outerspacemanager.models.MinimalShip;
import com.example.fasanol.outerspacemanager.models.RowAttackListDataSource;
import com.example.fasanol.outerspacemanager.models.ShipListToAttack;
import com.example.fasanol.outerspacemanager.models.HttpResponses.fleetResponse;
import com.example.fasanol.outerspacemanager.models.SelectedShips;
import com.google.gson.Gson;

import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AttackUserActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView myFleet;
    private ListView selectedFleet;
    private Button attack;

    private Retrofit ret = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private String token;
    private String userNameToAttack;
    private SharedPreferences settings;
    private ProgressDialog mProgressDialog;

    private ArrayList<String> fleetInfos;
    private ArrayList<String> fleetNames;
    private ArrayList<FleetShip> defaultFleet;
    private ArrayList<SelectedShips> selectedShips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack_user);

        mProgressDialog = ProgressDialog.show(this, "",
                "Loading fleet", true);
        this.settings = getSharedPreferences("apiSettings", 0);
        this.token = settings.getString("token", "");
        this.userNameToAttack = settings.getString("selectedUserToAttack", "");

        this.myFleet = (ListView) findViewById(R.id.myFleet);
        this.selectedFleet = (ListView) findViewById(R.id.selectedFleet);
        this.attack = (Button) findViewById(R.id.attack);

        this.attack.setText("Attaquer "+this.userNameToAttack);
        this.attack.setOnClickListener(this);

        this.myFleet.setOnItemClickListener(this);
        this.selectedFleet.setOnItemClickListener(this);


        ShipInterface service = ret.create(ShipInterface.class);
        Call<fleetResponse> request = service.getFleetList(token);
        request.enqueue(new Callback<fleetResponse>() {
            @Override
            public void onResponse(Call<fleetResponse> call, Response<fleetResponse> response) {
                mProgressDialog.dismiss();
                if(response.isSuccessful())
                {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AttackUserActivity.this,
                            android.R.layout.simple_list_item_1, response.body().getFleetInfos());
                    myFleet.setAdapter(adapter);


                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AttackUserActivity.this,
                            android.R.layout.simple_list_item_1, response.body().gatSelectedShipDefaultString());
                    selectedFleet.setAdapter(adapter2);

                    fleetInfos = response.body().getFleetInfos();
                    selectedShips = response.body().gatSelectedShipDefault();
                    defaultFleet = response.body().getShips();
                    fleetNames = response.body().getFleetNames();


                }

            }

            @Override
            public void onFailure(Call<fleetResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("fleets", fleetInfos);
        outState.putStringArrayList("fleetsNames", fleetNames);
        outState.putSerializable("fleets", defaultFleet);
        outState.putSerializable("fleets", selectedShips);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.myFleet)
        {
            ArrayList<String> newList = new ArrayList<String>();
            int i = 0;
            if(selectedShips.get(position).getAmount() < defaultFleet.get(position).getAmount())
                selectedShips.set(position, new SelectedShips(selectedShips.get(position).getAmount() + 1, selectedShips.get(position).getShipId()));

                for(SelectedShips x : selectedShips)
                {
                    newList.add(x.getAmount() + " " + fleetNames.get(i));
                    i++;
                }

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AttackUserActivity.this,
                        android.R.layout.simple_list_item_1, newList);
                selectedFleet.setAdapter(adapter2);
        }

        if(parent.getId() == R.id.selectedFleet){
            ArrayList<String> newList = new ArrayList<String>();
            int i = 0;
            if(selectedShips.get(position).getAmount() > 0)
            {
                selectedShips.set(position, new SelectedShips(selectedShips.get(position).getAmount() - 1, selectedShips.get(position).getShipId()));

                for(SelectedShips x : selectedShips)
                {
                    newList.add(x.getAmount() + " " + fleetNames.get(i));
                    i++;
                }

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AttackUserActivity.this,
                        android.R.layout.simple_list_item_1, newList);
                selectedFleet.setAdapter(adapter2);
            }
        }
    }

    @Override
    public void onClick(View v) {
        ArrayList<MinimalShip> ships = new ArrayList<MinimalShip>();

        mProgressDialog = ProgressDialog.show(this, "",
                "Lancement de l'attaque", true);


        for(SelectedShips ship : selectedShips)
        {
            ships.add(new MinimalShip(ship.getShipId(), ship.getAmount()));
        }

        final ShipListToAttack shipsList = new ShipListToAttack(ships);


        UserInterface service = ret.create(UserInterface.class);
        Call<AttackResponse> request = service.attackUser(token, this.userNameToAttack, shipsList);
        request.enqueue(new Callback<AttackResponse>() {
            @Override
            public void onResponse(Call<AttackResponse> call, Response<AttackResponse> response) {
                mProgressDialog.dismiss();
                if(response.isSuccessful())
                {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(AttackUserActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        } else {
                            ActivityCompat.requestPermissions(AttackUserActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                        }
                    }else {

                        long timestamp = response.body().getAttackTime();
                        DateTime date = new DateTime(timestamp);

                        RowAttackListDataSource dataSource = new RowAttackListDataSource(getApplicationContext());
                        dataSource.open();

                        Gson json = new Gson();
                        long timestampnow = System.currentTimeMillis();

                        dataSource.createAttack(json.toJson(shipsList), timestampnow, timestamp, userNameToAttack);

                        dataSource.close();

                        Context context = getApplicationContext();
                        CharSequence text = "Votre attaque sera fini le " + date.dayOfMonth().getAsText() + " / " + date.monthOfYear().getAsText() + " à " + date.hourOfDay().getAsText() + ":" + date.minuteOfHour().getAsText() + ":" + date.secondOfMinute().getAsText();
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    }
                    finish();
                }

            }

            @Override
            public void onFailure(Call<AttackResponse> call, Throwable t) {

            }
        });
    }
}
