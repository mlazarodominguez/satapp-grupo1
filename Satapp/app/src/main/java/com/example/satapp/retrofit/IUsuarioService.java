package com.example.satapp.retrofit;

import com.example.satapp.models.LoginReponse;
import com.example.satapp.models.User;
import com.example.satapp.models.UserLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IUsuarioService {

    @POST("auth")
    Call<LoginReponse> login(@Header("Authorization")String authHeader, @Query("access_token")String masterKey);

    @GET("users/me")
    Call<User> profile(@Query("access_token")String token);

}