package com.example.fasanol.outerspacemanager.interfaces;

import com.example.fasanol.outerspacemanager.models.User;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.Call;

public interface SignUpInterface {

    @POST("api/v1/auth/create")
    Call<User> creatUser(@Body User user);

    @POST("api/v1/auth/login")
    Call<User> connectUser(@Body User user);


}