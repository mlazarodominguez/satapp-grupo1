package com.example.satapp.repository;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.satapp.common.MyApp;
import com.example.satapp.models.Equipo;
import com.example.satapp.models.TicketResponse;
import com.example.satapp.models.TicketsResponse;
import com.example.satapp.models.UtilToken;
import com.example.satapp.retrofit.IUbicacionService;
import com.example.satapp.retrofit.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbicacionRepository {
    IUbicacionService service;
    ServiceGenerator serviceGenerator;

    MutableLiveData<List<String>> ubicacionesList;
    MutableLiveData<List<TicketsResponse>> ticketsEquipoList;

    public UbicacionRepository() {
        service = serviceGenerator.createService(IUbicacionService.class);
        ubicacionesList = null;
        ticketsEquipoList = null;
    }

    public MutableLiveData<List<String>> getUbicacionesList() {
        final MutableLiveData<List<String>> data = new MutableLiveData<>();

        Call<List<String>> call = service.getUbicaciones(UtilToken.getToken(MyApp.getContext()));
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful())
                    if (response.body() == null || response.body().isEmpty())
                        Toast.makeText(MyApp.getContext(), "Es nulo", Toast.LENGTH_SHORT).show();
                    else
                        data.setValue(response.body());
                else
                    Toast.makeText(MyApp.getContext(), "Error on the response from the Api", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error in the connection", Toast.LENGTH_SHORT).show();
            }
        });
        return data;
    }

    public MutableLiveData<List<TicketsResponse>> getTicketsEquipoList(String id) {
        final MutableLiveData<List<TicketsResponse>> data = new MutableLiveData<>();

        Call<List<TicketsResponse>> call = service.getListTicketsEquipo(id, UtilToken.getToken(MyApp.getContext()));
        call.enqueue(new Callback<List<TicketsResponse>>() {
            @Override
            public void onResponse(Call<List<TicketsResponse>> call, Response<List<TicketsResponse>> response) {
                if (response.isSuccessful())
                    if (response.body() == null || response.body().isEmpty())
                        Toast.makeText(MyApp.getContext(), "Es nulo o no hay tickets para este equipo", Toast.LENGTH_SHORT).show();
                    else
                        data.setValue(response.body());
                else
                    Toast.makeText(MyApp.getContext(), "Error on the response from the Api", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<TicketsResponse>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error in the connection", Toast.LENGTH_SHORT).show();
            }
        });
        return data;
    }


    public MutableLiveData<TicketResponse>getTicketDetail(String id, String token) {
        final MutableLiveData<TicketResponse> ticketDetail = new MutableLiveData<>();

        Call<TicketResponse> editInventariableCall = service.getTicketDetail(id,token);
        editInventariableCall.enqueue(new Callback<TicketResponse>() {
            @Override
            public void onResponse(Call<TicketResponse> call, Response<TicketResponse> response) {
                if(response.isSuccessful()){
                    ticketDetail.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<TicketResponse> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error in the connection", Toast.LENGTH_SHORT).show();
            }
        });
        return ticketDetail;
    }
}
