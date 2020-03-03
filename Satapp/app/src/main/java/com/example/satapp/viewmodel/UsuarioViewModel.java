package com.example.satapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.satapp.models.User;
import com.example.satapp.repository.UsuariosRepository;

import java.util.List;

public class UsuarioViewModel extends AndroidViewModel {

    private UsuariosRepository usuariosRepository;
    public MutableLiveData<User> usuario;
    public MutableLiveData<String> token;

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        usuariosRepository = new UsuariosRepository();
    }

    //Set Token
    public void setToken(String tokenA) {
        this.token.setValue(tokenA);
    }

    //Get Token
    public MutableLiveData<String> getToken() {
        return token;
    }
}