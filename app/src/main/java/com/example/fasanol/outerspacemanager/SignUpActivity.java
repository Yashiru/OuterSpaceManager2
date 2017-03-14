package com.example.fasanol.outerspacemanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fasanol.outerspacemanager.interfaces.LoginInterface;
import com.example.fasanol.outerspacemanager.interfaces.SignUpInterface;
import com.example.fasanol.outerspacemanager.models.User;
import com.example.fasanol.outerspacemanager.models.UserConnected;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private String token;
    private EditText identifiantText;
    private EditText passwordText;
    private Button connectButton;
    private Button createButton;
    private SharedPreferences settings;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        passwordText = (EditText) findViewById(R.id.password);
        identifiantText = (EditText) findViewById(R.id.identifiant);
        connectButton = (Button) findViewById(R.id.connect);
        createButton = (Button) findViewById(R.id.createUser);

        createButton.setOnClickListener(this);
        connectButton.setOnClickListener(this);

        this.settings = getSharedPreferences("apiSettings", 0);
        this.token = settings.getString("token", "");

        if(token != "" && token != null)
        {
            mProgressDialog = ProgressDialog.show(this, "",
                "Loggin in", true);
            Retrofit ret = new Retrofit.Builder()
                    .baseUrl("https://outer-space-manager.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            LoginInterface service = ret.create(LoginInterface.class);
            Call<UserConnected> request = service.getUser(token);
            request.enqueue(new Callback<UserConnected>() {
                @Override
                public void onResponse(Call<UserConnected> call, Response<UserConnected> response) {
                    Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                    mProgressDialog.dismiss();
                    startActivity(myIntent);
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
            case R.id.connect:

                Retrofit ret = new Retrofit.Builder()
                    .baseUrl("https://outer-space-manager.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
                SignUpInterface service = ret.create(SignUpInterface.class);
                Call<User> request = service.connectUser(new User(this.identifiantText.getText(), this.passwordText.getText()));
                request.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.code() == 200){
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("token", response.body().getToken());
                            Log.d("connexion", response.body().getToken().toString());
                            editor.commit();
                            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(myIntent);
                        }
                        else{

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

                break;

            case R.id.createUser:
                Retrofit ret2 = new Retrofit.Builder()
                        .baseUrl("https://outer-space-manager.herokuapp.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                SignUpInterface service2 = ret2.create(SignUpInterface.class);
                Call<User> request2 = service2.creatUser(new User(this.identifiantText.getText(), this.passwordText.getText()));
                request2.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.code() == 200){
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("token", response.body().getToken());
                            editor.commit();
                            Log.d("Token", response.body().getToken());
                            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(myIntent);
                        }
                        else{
                            Log.d("Error", "User deja exitant");

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

                break;
        }
    }
}
