package com.example.fasanol.outerspacemanager.interfaces;

import com.example.fasanol.outerspacemanager.models.HttpResponses.BuildingResponse;
import com.example.fasanol.outerspacemanager.models.HttpResponses.MinimalShip;
import com.example.fasanol.outerspacemanager.models.HttpResponses.UserResponse;
import com.example.fasanol.outerspacemanager.models.HttpResponses.httpSimpleResponse;

import java.util.ArrayList;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface UserInterface {

    @GET("api/v1/buildings/list")
    Call<BuildingResponse> getBuildings(@Header("x-access-token") String token);

    @GET("api/v1/buildings/list")
    Call<BuildingResponse> addBuilding(@Header("x-access-token") String token, @Body int buildingId);

    @POST("api/v1/buildings/create/{buildingId}")
    Call<httpSimpleResponse> createBuilding(@Header("x-access-token") String token, @Path("buildingId") int buildingId);

    @GET("api/v1/users/0/11")
    Call<UserResponse> getAllUsers(@Header("x-access-token") String token);

    @POST("api/fleet/attack/{userName}")
    Call<httpSimpleResponse> attackUser(@Header("x-access-token") String token, @Path("userName") String username, @Body ArrayList<MinimalShip> ships);

}
