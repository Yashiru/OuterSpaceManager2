package com.example.fasanol.outerspacemanager;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.fasanol.outerspacemanager.interfaces.UserInterface;
import com.example.fasanol.outerspacemanager.models.BuildingsAdapter;
import com.example.fasanol.outerspacemanager.models.User;
import com.example.fasanol.outerspacemanager.models.HttpResponses.UserResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.fasanol.outerspacemanager.R.id.recyclerView;

public class GalaxyActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Retrofit ret = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private ProgressDialog mProgressDialog;
    private ListView listUser;
    private ArrayList<User> users;
    private SharedPreferences settings;
    private String token;
    private ArrayList<String> listUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galaxy);
        mProgressDialog = ProgressDialog.show(this, "",
                "Loading users", true);

        this.settings = getSharedPreferences("apiSettings", 0);
        this.token = settings.getString("token", "");
        this.listUser = (ListView) findViewById(R.id.listUser);
        this.listUser.setOnItemClickListener(this);

        UserInterface service = ret.create(UserInterface.class);
        Call<UserResponse> request = service.getAllUsers(token);
        request.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                mProgressDialog.dismiss();
                if(response.isSuccessful())
                {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(GalaxyActivity.this,
                            android.R.layout.simple_list_item_1, response.body().getUsersInfos());
                    listUser.setAdapter(adapter);

                    listUserName = response.body().getUserNames();

                }


            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

                mProgressDialog.dismiss();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
