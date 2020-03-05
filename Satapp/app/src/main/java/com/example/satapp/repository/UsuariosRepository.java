package com.example.satapp.repository;

import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.satapp.R;
import com.example.satapp.common.MyApp;
import com.example.satapp.models.User;
import com.example.satapp.models.UtilToken;
import com.example.satapp.retrofit.IUsuarioService;
import com.example.satapp.retrofit.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuariosRepository {
    IUsuarioService service;
    ServiceGenerator serviceGenerator;

    MutableLiveData<List<Object>> seriesPopulares;

    public UsuariosRepository(){
        service = serviceGenerator.createService(IUsuarioService.class);
        seriesPopulares = null;
    }

    public MutableLiveData<List<Object>> getSeriesPopulares(){
        final MutableLiveData<List<Object>> data = new MutableLiveData<>();

        /*Call<Object> call = service.getClass();
        call.enqueue(new Callback<PopularSeries>() {
            @Override
            public void onResponse(Call<PopularSeries> call, Response<PopularSeries> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body().getResults());
                } else {
                    Toast.makeText(MyApp.getContext(), "Error on the response from the Api", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PopularSeries> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error in the connection", Toast.LENGTH_SHORT).show();
            }
        });*/
        return data;
    }


    public MutableLiveData<User>getCurrentUser(String token) {
        final MutableLiveData<User> userProfile = new MutableLiveData<>();
        Call<User> userCallMe = service.profile(token);
        userCallMe.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    userProfile.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error in the connection", Toast.LENGTH_SHORT).show();
            }
        });
        return userProfile;
    }

}
