package com.example.satapp.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IAnotacionService {

    @GET("/anotaciones/ticket")
    Call<List<String>> getAnotaciones(@Query("access_token") String access_token);

    @GET("/anotaciones/{id}")
    Call<String> getAnotacion(@Path("id") String id, @Query("access_token") String token);
}
