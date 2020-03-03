package com.example.satapp.retrofit;

import com.example.satapp.models.Equipo;
import com.example.satapp.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface IEquipoService {

    @GET("inventariable")
    Call<List<Equipo>> getEquipos(@Query("access_token") String token);

    @GET("inventariable/{id}")
    Call<Equipo> getEquipoDetalles(@Path("id")String id);

    @PUT("inventariable/{id}")
    Call<Equipo> editInventariable(@Query("access_token")String token, @Path("id") String id);
}
