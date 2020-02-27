package com.example.satapp.ui.dashboard;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.satapp.common.MyApp;
import com.example.satapp.models.Equipo;
import com.example.satapp.retrofit.IEquipoService;
import com.example.satapp.retrofit.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EquipoRepository {
    IEquipoService service;
    ServiceGenerator serviceGenerator;

    MutableLiveData<List<Equipo>> equipos;

    public EquipoRepository(){
        service = serviceGenerator.createService(IEquipoService.class);
        equipos = null;
    }

    public MutableLiveData<List<Equipo>> getEquipos(){
        final MutableLiveData<List<Equipo>> data = new MutableLiveData<>();
        Call<List<Equipo>> call = service.getEquipos();
        call.enqueue(new Callback<List<Equipo>>() {
                         @Override
                         public void onResponse(Call<List<Equipo>> call, Response<List<Equipo>> response) {
                             if (response.isSuccessful()) {
                                 data.setValue(response.body());
                             } else {
                                 Toast.makeText(MyApp.getContext(), "Error on the response from the Api", Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onFailure(Call<List<Equipo>> call, Throwable t) {
                             Toast.makeText(MyApp.getContext(), "Error in the connection", Toast.LENGTH_SHORT).show();
                         }
                     }
        );
        return data;
    }



}
