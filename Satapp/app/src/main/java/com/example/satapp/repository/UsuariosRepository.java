package com.example.satapp.repository;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;


import com.example.satapp.common.Constantes;

import com.example.satapp.R;

import com.example.satapp.common.MyApp;
import com.example.satapp.models.Name;
import com.example.satapp.models.User;
import com.example.satapp.models.UtilToken;
import com.example.satapp.retrofit.IUsuarioService;
import com.example.satapp.retrofit.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class UsuariosRepository {

    IUsuarioService service;
    ServiceGenerator serviceGenerator;


    MutableLiveData<List<User>> usuarios;
    List<User> listaAux = new ArrayList<>();

    public UsuariosRepository(){
        service = serviceGenerator.createService(IUsuarioService.class);
        usuarios = null;
    }

    public MutableLiveData<List<User>> getUsuariosValidados(){
        final MutableLiveData<List<User>> data = new MutableLiveData<>();

        Call<List<User>> call = service.allUsersValidated(UtilToken.getToken(MyApp.getContext()));
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

    public MutableLiveData<User> upgradeTecnico(String id) {
        final MutableLiveData<User> data = new MutableLiveData<>();
        Call<User> call = service.upgradeTecnico(id, UtilToken.getToken(MyApp.getContext()));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    return data;
    }

    public MutableLiveData<User> updateUsuario(String id, Name name){
        final MutableLiveData<User> data = new MutableLiveData<>();
        Call<User> call = service.updateProfile(id, UtilToken.getToken(MyApp.getContext()),name);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

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
                } else if(response.code()==404) {
                    Toast.makeText(MyApp.getContext(), "No hay usuarios pendientes de validación", Toast.LENGTH_SHORT).show();
                }else{
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

    public MutableLiveData<User> validarUsuario(String id) {
        final MutableLiveData<User> data = new MutableLiveData<>();

        Call<User> call = service.validarUsuario(id,UtilToken.getToken(MyApp.getContext()));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        return data;
    }

    public void borrarImagen(String id){
        Call<ResponseBody> call = service.borrarFoto(id,UtilToken.getToken(MyApp.getContext()));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(MyApp.getContext(),"Imagen Borrada" ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void borrarUsuario(String id){

        Call<ResponseBody> call = service.borrarUsuario(id,UtilToken.getToken(MyApp.getContext()));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(MyApp.getContext(),"Usuario Borrado",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    public MutableLiveData<User> getUser(String id) {
        final MutableLiveData<User> data = new MutableLiveData<>();
        Call<User> userCallMe = service.perfilUsuario(id,UtilToken.getToken(MyApp.getContext()));
        userCallMe.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    data.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error in the connection", Toast.LENGTH_SHORT).show();
            }
        });
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
