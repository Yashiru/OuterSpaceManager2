package com.example.fasanol.outerspacemanager.interfaces;

import com.example.fasanol.outerspacemanager.models.BuildingResponse;
import com.example.fasanol.outerspacemanager.models.User;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.GET;
import retrofit2.Call;


public interface UserInterface {

    @GET("api/v1/buildings/list")
    Call<BuildingResponse> getBuildings(@Header("x-access-token") String token);

    @GET("api/v1/buildings/list")
    Call<BuildingResponse> addBuilding(@Header("x-access-token") String token, @Body int buildingId);

}
