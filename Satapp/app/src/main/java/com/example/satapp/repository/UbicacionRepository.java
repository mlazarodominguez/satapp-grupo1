package com.example.satapp.repository;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.satapp.common.MyApp;
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

    public UbicacionRepository() {
        service = serviceGenerator.createService(IUbicacionService.class);
        ubicacionesList = null;
    }

    public MutableLiveData<List<String>> getUbicacionesList() {
        final MutableLiveData<List<String>> data = new MutableLiveData<>();

        Call<List<String>> call = service.getUbicaciones("");
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful())
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
}
