package com.example.fasanol.outerspacemanager.interfaces;

import com.example.fasanol.outerspacemanager.models.HttpResponses.BuildingResponse;
import com.example.fasanol.outerspacemanager.models.HttpResponses.ReportsReponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by mac1 on 20/03/2017.
 */

public interface ReportInterface {

    @GET("/api/v1/reports/{from}/{limit}")
    Call<ReportsReponse> getReports(@Header("x-access-token") String token, @Path("from") int from, @Path("limit") int limit);

}
