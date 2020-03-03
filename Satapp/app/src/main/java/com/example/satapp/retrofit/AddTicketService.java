package com.example.satapp.retrofit;

import com.example.satapp.models.AddTicketResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AddTicketService {

    @Multipart
    @POST("ticket")
    Call<AddTicketResponse> addTicket(
            @Part List<MultipartBody.Part> avatar,
            @Part("titulo") RequestBody titulo,
            @Part("descripcion") RequestBody descripcion,
            @Part("access_token") RequestBody access_token
    );
}

