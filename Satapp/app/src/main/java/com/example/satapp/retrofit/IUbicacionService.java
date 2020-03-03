package com.example.satapp.retrofit;

import com.example.satapp.models.TicketsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IUbicacionService {

    @GET("inventariable/ubicaciones")
    Call<List<String>> getUbicaciones(@Query("access_token") String access_token);

    @GET("/ticket/inventariable/{id}")
    Call<List<TicketsResponse>> getListTicketsEquipo(@Query("access_token") String access_token, @Path("id") String id);
}
