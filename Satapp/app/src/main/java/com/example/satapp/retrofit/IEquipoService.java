package com.example.satapp.retrofit;

import com.example.satapp.models.Equipo;
import com.example.satapp.models.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
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
    Call<Equipo> nuevoEquipo(@Query("access_token") String token,
                             @Part MultipartBody.Part imagen,
                             @Part("codigo") RequestBody codigo,
                             @Part("nombre") RequestBody nombre,
                             @Part("tipo") RequestBody tipo,
                             @Part("descripcion") RequestBody descripcion);

    @GET("inventariable/{id}")
    Call<Equipo> getEquipoDetalles(@Path("id")String id,
                                   @Query("access_token")String token);

    @PUT("inventariable/{id}")
    Call<Equipo> editInventariable(@Path("id") String id,
                                   @Query("access_token")String token,
                                   @Body Equipo equipo);
    @Multipart
    @PUT("inventariable/{id}/img")
    Call<Equipo> editImg (@Path("id")String id,
                         @Query("access_token") String token,
                         @Part MultipartBody.Part imagen);

}
