package com.example.fasanol.outerspacemanager;

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
    private Button vueGenerale;
    private Button batiment;
    private Button flotte;
    private Button recherche;
    private Button chantierSpatial;
    private Button galaxie;
    private Button signOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (TextView) findViewById(R.id.username);
        score = (TextView) findViewById(R.id.score);

        vueGenerale = (Button) findViewById(R.id.vg);
        batiment = (Button) findViewById(R.id.ba);
        flotte = (Button) findViewById(R.id.fl);
        recherche = (Button) findViewById(R.id.re);
        chantierSpatial = (Button) findViewById(R.id.cs);
        galaxie = (Button) findViewById(R.id.ga);
        signOut = (Button) findViewById(R.id.signOut);

        batiment.setOnClickListener(this);
        signOut.setOnClickListener(this);


        this.settings = getSharedPreferences("apiSettings", 0);
        this.token = settings.getString("token", "");

        Retrofit ret = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if(token != "" && token != null)
        {
            LoginInterface service = ret.create(LoginInterface.class);
            Call<UserConnected> request = service.getUser(token);
            request.enqueue(new Callback<UserConnected>() {
                @Override
                public void onResponse(Call<UserConnected> call, Response<UserConnected> response) {
                    username.setText(response.body().getUsername());
                    score.setText("Score: "+response.body().getPoints());
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
                Intent myIntent = new Intent(getApplicationContext(), batimentActivity.class);
                startActivity(myIntent);
                break;
        }
    }
}
