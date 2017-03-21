package com.example.fasanol.outerspacemanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fasanol.outerspacemanager.interfaces.LoginInterface;
import com.example.fasanol.outerspacemanager.models.UserConnected;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences settings;
    private String token;

    private TextView username;
    private TextView score;
    private TextView mineral;
    private TextView gas;
    private Button vueGenerale;
    private Button batiment;
    private Button flotte;
    private Button recherche;
    private Button chantierSpatial;
    private Button galaxie;
    private Button attackInProgress;
    private Button signOut;
    private Button attack;

    private Retrofit ret = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private ProgressDialog mProgressDialog;


    @Override
    protected void onResume() {
        super.onResume();
        if(token != "" && token != null)
        {
            LoginInterface service = ret.create(LoginInterface.class);
            Call<UserConnected> request = service.getUser(token);
            request.enqueue(new Callback<UserConnected>() {
                @Override
                public void onResponse(Call<UserConnected> call, Response<UserConnected> response) {
                    username.setText(response.body().getUsername());
                    score.setText(response.body().getPoints()+ " pts");
                    gas.setText(response.body().getGas()+" Litres de gaz");
                    mineral.setText(response.body().getMinerals()+ " Kg de mineraux");
                }

                @Override
                public void onFailure(Call<UserConnected> call, Throwable t) {

                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (TextView) findViewById(R.id.username);
        score = (TextView) findViewById(R.id.score);
        gas = (TextView) findViewById(R.id.gas);
        mineral = (TextView) findViewById(R.id.mineral);

        batiment = (Button) findViewById(R.id.ba);
        flotte = (Button) findViewById(R.id.fl);
        recherche = (Button) findViewById(R.id.re);
        chantierSpatial = (Button) findViewById(R.id.cs);
        attackInProgress = (Button) findViewById(R.id.attackInProgress);
        signOut = (Button) findViewById(R.id.signOut);
        attack = (Button) findViewById(R.id.attack);
        galaxie = (Button) findViewById(R.id.galaxy);

        batiment.setOnClickListener(this);
        chantierSpatial.setOnClickListener(this);
        flotte.setOnClickListener(this);
        recherche.setOnClickListener(this);
        galaxie.setOnClickListener(this);
        signOut.setOnClickListener(this);
        attack.setOnClickListener(this);
        attackInProgress.setOnClickListener(this);
        galaxie.setOnClickListener(this);


        this.settings = getSharedPreferences("apiSettings", 0);
        this.token = settings.getString("token", "");

        if(token != "" && token != null)
        {
            mProgressDialog = ProgressDialog.show(this, "",
                    "Loading user datas", true);
            LoginInterface service = ret.create(LoginInterface.class);
            Call<UserConnected> request = service.getUser(token);
            request.enqueue(new Callback<UserConnected>() {
                @Override
                public void onResponse(Call<UserConnected> call, Response<UserConnected> response) {
                    username.setText(response.body().getUsername());
                    score.setText(response.body().getPoints()+ " pts");
                    gas.setText(response.body().getGas()+" Litres de gaz");
                    mineral.setText(response.body().getMinerals()+ " Kg de mineraux");
                    mProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<UserConnected> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.signOut:
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("token");
                editor.commit();
                finish();
                break;

            case R.id.ba:
                Intent ba = new Intent(getApplicationContext(), batimentActivity.class);
                startActivity(ba);
                break;

            case R.id.cs:
                Intent cs = new Intent(getApplicationContext(), ChantierActivity.class);
                startActivity(cs);
                break;

            case R.id.fl:
                Intent fl = new Intent(getApplicationContext(), FleetActivity.class);
                startActivity(fl);
                break;

            case R.id.re:
                Intent re = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(re);
                break;

            case R.id.attack:
                Intent at = new Intent(getApplicationContext(), AttackActivity.class);
                startActivity(at);
                break;

            case R.id.attackInProgress:
                Intent ac = new Intent(getApplicationContext(), AttackInProgressActivity.class);
                startActivity(ac);
                break;

            case R.id.galaxy:
                Intent ga = new Intent(getApplicationContext(), GalaxyActivity.class);
                startActivity(ga);
                break;
        }
    }
}
