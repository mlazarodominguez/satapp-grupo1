package com.example.satapp.repository;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.satapp.common.MyApp;
import com.example.satapp.models.TicketResponse;
import com.example.satapp.retrofit.IAnotacionService;
import com.example.satapp.retrofit.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnotacionRepository {
    IAnotacionService service;
    ServiceGenerator serviceGenerator;
    MutableLiveData<List<String>> anotaciones;

    public AnotacionRepository() {
        service = serviceGenerator.createService(IAnotacionService.class);
        anotaciones = null;
    }

    public MutableLiveData<List<String>> getAnotaciones(String token){
        final MutableLiveData<List<String>> data = new MutableLiveData<>();
        Call<List<String>> call = service.getAnotaciones(token);
        call.enqueue(new Callback<List<String>>() {
                         @Override
                         public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                             if (response.isSuccessful()) {
                                 data.setValue(response.body());
                             } else {
                                 Toast.makeText(MyApp.getContext(), "Error on the response from the Api", Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onFailure(Call<List<String>> call, Throwable t) {
                             Toast.makeText(MyApp.getContext(), "Error in the connection", Toast.LENGTH_SHORT).show();
                         }
                     }
        );
        return data;
    }

    public MutableLiveData<String>getAnotacionDetail(String id, String token) {
        final MutableLiveData<String> anotacionDetail = new MutableLiveData<>();

        Call<String> call = service.getAnotacion(id,token);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    anotacionDetail.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error in the connection", Toast.LENGTH_SHORT).show();
            }
        });
        return anotacionDetail;
    }
}
