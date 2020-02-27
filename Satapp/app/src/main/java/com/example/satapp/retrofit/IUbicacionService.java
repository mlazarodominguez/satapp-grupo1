package com.example.satapp.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IUbicacionService {

    @GET("inventariable/ubicaciones")
    Call<List<String>> getUbicaciones(@Query("access_token") String access_token);
}
