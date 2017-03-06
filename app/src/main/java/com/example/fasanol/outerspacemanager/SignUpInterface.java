package com.example.fasanol.outerspacemanager;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Path;

/*


{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE0ODk0MjAyMDY3OTIsImlhdCI6MTQ4ODgxNTQwNn0.vanjYaAid1vp7WMOHsFNWnj80L-KWjMJAjaF8rDfaBc",
  "expires": 1489420206792
}


*/

public class SignUpInterface {
    @GET("https://outer-space-manager.herokuapp.com/api/v1")
    Call<User> listRepos(@Path("user") String user) {
        return null;
    }
}