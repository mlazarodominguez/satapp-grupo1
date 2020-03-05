package com.example.satapp.repository;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.satapp.common.MyApp;
import com.example.satapp.models.AddTicketResponse;
import com.example.satapp.models.UtilToken;
import com.example.satapp.retrofit.AddTicketService;
import com.example.satapp.retrofit.ServiceGenerator;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTicketRepository {

    AddTicketService service;
    ServiceGenerator serviceGenerator;

    public AddTicketRepository() {
        service = serviceGenerator.createService(AddTicketService.class);
    }

    public MutableLiveData<AddTicketResponse> addTicket(List<MultipartBody.Part> avatar,
                                                        RequestBody titulo,
                                                        RequestBody descripcion) {
        final MutableLiveData<AddTicketResponse> data = new MutableLiveData<>();

        Call<AddTicketResponse> call = service.addTicket(UtilToken.getToken(MyApp.getContext()), avatar, titulo, descripcion);
        call.enqueue(new Callback<AddTicketResponse>() {
            @Override
            public void onResponse(Call<AddTicketResponse> call, Response<AddTicketResponse> response) {
                if (response.isSuccessful())
                    if (response.body() == null)
                        Toast.makeText(MyApp.getContext(), "Es nulo", Toast.LENGTH_SHORT).show();
                    else
                        data.setValue(response.body());
                else
                    Toast.makeText(MyApp.getContext(), "Error on the response from the Api", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AddTicketResponse> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error in the connection", Toast.LENGTH_SHORT).show();
            }
        });
        return data;
    }

}
