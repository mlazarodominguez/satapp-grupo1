package com.example.satapp.retrofit;

import com.example.satapp.models.Equipo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface IEquipoService {

    @GET("inventariable")
    Call<List<Equipo>> getEquipos();

    @GET("inventariable/{id}")
    Call<Equipo> getEquipoDetalles(@Path("id")String id);
}
