package com.example.satapp.repository;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.satapp.common.Constantes;
import com.example.satapp.common.MyApp;
import com.example.satapp.models.User;
import com.example.satapp.models.UtilToken;
import com.example.satapp.retrofit.IUsuarioService;
import com.example.satapp.retrofit.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuariosRepository {
    IUsuarioService service;
    ServiceGenerator serviceGenerator;

    MutableLiveData<List<Object>> seriesPopulares;
    List<User> listaAux = new ArrayList<>();

    public UsuariosRepository(){
        service = serviceGenerator.createService(IUsuarioService.class);
        seriesPopulares = null;
    }

    public MutableLiveData<List<User>> getUsuariosValidados(){
        final MutableLiveData<List<User>> data = new MutableLiveData<>();

        Call<List<User>> call = service.allUsersValidated(UtilToken.getToken(MyApp.getContext()));
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    listaAux.addAll(getUsuariosNoValidados().getValue());
                    response.body().removeAll(listaAux);
                    data.setValue(response.body());
                } else {
                    Toast.makeText(MyApp.getContext(), "Error on the response from the Api", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error in the connection", Toast.LENGTH_SHORT).show();
            }
        });
        return data;
    }
    public MutableLiveData<List<User>> getUsuariosNoValidados(){
        final MutableLiveData<List<User>> data = new MutableLiveData<>();

        Call<List<User>> call = service.allUsersNonValidate(UtilToken.getToken(MyApp.getContext()));
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    Toast.makeText(MyApp.getContext(), "Error on the response from the Api", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error in the connection", Toast.LENGTH_SHORT).show();
            }
        });
        return data;
    }
}
