package com.example.fasanol.outerspacemanager.interfaces;

import com.example.fasanol.outerspacemanager.models.FleetShip;
import com.example.fasanol.outerspacemanager.models.HttpResponses.BuildingResponse;
import com.example.fasanol.outerspacemanager.models.HttpResponses.ShipResponse;
import com.example.fasanol.outerspacemanager.models.HttpResponses.UserResponse;
import com.example.fasanol.outerspacemanager.models.HttpResponses.fleetResponse;
import com.example.fasanol.outerspacemanager.models.HttpResponses.httpSimpleResponse;
import com.example.fasanol.outerspacemanager.models.shipQueryObject;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ShipInterface {

    @GET("api/v1/ships")
    Call<ShipResponse> getShipList(@Header("x-access-token") String token);

    @POST("api/v1/ships/create/{shipId}")
    Call<httpSimpleResponse> createShip(@Header("x-access-token") String token, @Path("shipId") int shipId, @Body shipQueryObject amount);

    @GET("api/v1/fleet/list")
    Call<fleetResponse> getFleetList(@Header("x-access-token") String token);
}
