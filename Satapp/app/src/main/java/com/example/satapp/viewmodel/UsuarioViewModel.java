package com.example.satapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.satapp.models.User;
import com.example.satapp.repository.UsuariosRepository;

import java.util.List;

public class UsuarioViewModel extends AndroidViewModel {

    MutableLiveData<List<User>> usuarios;
    UsuariosRepository usuariosRepository;
    MutableLiveData<User> user;


    public UsuarioViewModel(@NonNull Application application){
        super(application);
        usuariosRepository = new UsuariosRepository();

       }


    public MutableLiveData<List<User>> getUsuariosValidados(){
        usuarios = usuariosRepository.getUsuariosValidados();
        return  usuarios;
       }

    public MutableLiveData<List<User>> getUsuariosNoValidados(){
        usuarios = usuariosRepository.getUsuariosNoValidados();
        return  usuarios;
    }

    public MutableLiveData<User> validarUsuario(String id){
        user = usuariosRepository.validarUsuario(id);
        return user;
    }

    public void borrarUsuario(String id){
        usuariosRepository.borrarUsuario(id);
    }
}
