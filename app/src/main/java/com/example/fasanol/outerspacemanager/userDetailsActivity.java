package com.example.fasanol.outerspacemanager;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fasanol.outerspacemanager.interfaces.ReportInterface;
import com.example.fasanol.outerspacemanager.interfaces.UserInterface;
import com.example.fasanol.outerspacemanager.models.FleetShip;
import com.example.fasanol.outerspacemanager.models.HttpResponses.ReportsReponse;
import com.example.fasanol.outerspacemanager.models.HttpResponses.UserResponse;
import com.example.fasanol.outerspacemanager.models.Report;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class userDetailsActivity extends AppCompatActivity {
    private Retrofit ret = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private String token;
    private SharedPreferences settings;
    private ProgressDialog mProgressDialog;
    private ArrayList<Report> reports;
    private String userNameSelected;

    private TextView title;
    private ListView reportsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        mProgressDialog = ProgressDialog.show(this, "",
                "Loading reports", true);
        this.settings = getSharedPreferences("apiSettings", 0);
        this.token = settings.getString("token", "");
        this.userNameSelected = settings.getString("selectedUserToViewReports", "");

        this.title = (TextView) findViewById(R.id.title);
        this.reportsView = (ListView) findViewById(R.id.reports);

        this.title.setText("Rapports des batailles chez "+this.userNameSelected);

        ReportInterface service = ret.create(ReportInterface.class);
        Call<ReportsReponse> request = service.getReports(token, 0, 15);
        request.enqueue(new Callback<ReportsReponse>() {
            @Override
            public void onResponse(Call<ReportsReponse> call, Response<ReportsReponse> response) {
                mProgressDialog.dismiss();
                if(response.isSuccessful())
                {
                    reports = response.body().getReportByUsername(userNameSelected);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(userDetailsActivity.this,
                            android.R.layout.simple_list_item_1, response.body().getReportsInfos(reports));
                    reportsView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<ReportsReponse> call, Throwable t) {

            }
        });
    }
}
