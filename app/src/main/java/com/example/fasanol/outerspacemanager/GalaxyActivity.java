package com.example.fasanol.outerspacemanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class GalaxyActivity extends Fragment implements AdapterView.OnItemClickListener {
    private Retrofit ret = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private ProgressDialog mProgressDialog;
    private ArrayList<User> users;
    private SharedPreferences settings;
    private String token;
    public ArrayList<String> listUserName;
    public ListView lvFragment;

    /*protected void onCreate(Bundle savedInstanceState) {

    }*/

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("selectedUserToViewReports", listUserName.get(position));
        editor.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_galaxy,container);
        lvFragment = (ListView)v.findViewById(R.id.listUser);
        lvFragment.setOnItemClickListener((GalaxyFragment)getActivity());

        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        mProgressDialog = ProgressDialog.show(getActivity(), "",
                "Loading users", true);

        this.settings = getActivity().getSharedPreferences("apiSettings", 0);
        this.token = settings.getString("token", "");

        UserInterface service = ret.create(UserInterface.class);
        Call<UserResponse> request = service.getAllUsers(token);
        request.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                mProgressDialog.dismiss();
                if(response.isSuccessful() && getActivity() != null)
                {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1, response.body().getUsersInfos());
                    lvFragment.setAdapter(adapter);

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
    public void onDestroy() {
        super.onDestroy();
        this.mProgressDialog.dismiss();
    }
}
