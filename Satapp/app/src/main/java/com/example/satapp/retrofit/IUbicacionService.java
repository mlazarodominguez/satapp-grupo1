package com.example.satapp.retrofit;

import com.example.satapp.models.TicketResponse;
import com.example.satapp.models.TicketsResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IUbicacionService {

    @GET("inventariable/ubicaciones")
    Call<List<String>> getUbicaciones(@Query("access_token") String access_token);

    @GET("/ticket/inventariable/{id}")
    Call<List<TicketsResponse>> getListTicketsEquipo(@Path("id") String id, @Query("access_token") String access_token);

    @GET("/ticket/{id}")
    Call<TicketResponse> getTicketDetail(@Path("id") String id, @Query("access_token") String access_token);

    @GET("/ticket/img/{id}/{img}")
    Call<ResponseBody> imagenTicket(@Path("id") String id, @Path("img") String img, @Query("access_token") String access_token);
}
