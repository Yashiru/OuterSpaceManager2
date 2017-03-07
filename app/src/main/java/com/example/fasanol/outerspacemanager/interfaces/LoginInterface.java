package com.example.fasanol.outerspacemanager.interfaces;

import com.example.fasanol.outerspacemanager.models.UserConnected;

import retrofit2.http.Header;
import retrofit2.http.GET;
import retrofit2.Call;
/**
 * Created by mac1 on 07/03/2017.
 */

public interface LoginInterface {
    @GET("api/v1/users/get")
    Call<UserConnected> getUser(@Header("x-access-token") String token);
}