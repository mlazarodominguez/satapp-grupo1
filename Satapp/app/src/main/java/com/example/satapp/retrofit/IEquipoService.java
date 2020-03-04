package com.example.satapp.retrofit;

import com.example.satapp.models.Equipo;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface IEquipoService {

    @GET("inventariable")
    Call<List<Equipo>> getEquipos(@Query("access_token") String token);

    @GET("inventariable/img/{id}")
    Call<ResponseBody> getImagenEquipo (@Path("id") String id, @Query("access_token") String token);

    @Multipart
    @POST("inventariable")
    Call<Equipo> nuevoEquipo(@Part MultipartBody.Part imagen,
                             @Part("nombre") RequestBody nombre,
                             @Part("tipo") RequestBody tipo,
                             @Part("descripcion") RequestBody descripcion,
                             @Part("ubicacion") RequestBody ubicacion,
                             @Query("access_token") String token);

    @GET("inventariable/{id}")
    Call<Equipo> getEquipoDetalles(@Path("id")String id);

    @GET("inventariable/tipos")
    Call<List<String>> getAllTipos(@Query("access_token") String token);
}
