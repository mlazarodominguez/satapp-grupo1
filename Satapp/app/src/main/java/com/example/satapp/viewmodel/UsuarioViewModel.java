package com.example.satapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.satapp.models.Name;
import com.example.satapp.models.User;
import com.example.satapp.repository.UsuariosRepository;

import java.util.List;

import okhttp3.RequestBody;

public class UsuarioViewModel extends AndroidViewModel {


    MutableLiveData<List<User>> usuarios;
    UsuariosRepository usuariosRepository;
    MutableLiveData<User> user;
    MutableLiveData<String> idSeleccionada;
    public MutableLiveData<String> token;


    public UsuarioViewModel(@NonNull Application application){
        super(application);
        usuariosRepository = new UsuariosRepository();
        this.idSeleccionada = new MutableLiveData<>();
        this.idSeleccionada.setValue(null);

       }


    public MutableLiveData<List<User>> getUsuariosValidados(){
        usuarios = usuariosRepository.getUsuariosValidados();
        return  usuarios;
       }

    public void  setUsuariosValidados(List<User> usuarios){
        this.usuarios.setValue(usuarios);
    }

    public MutableLiveData<List<User>> getUsuariosNoValidados(){
        usuarios = usuariosRepository.getUsuariosNoValidados();
        return  usuarios;
    }

    public MutableLiveData<User> validarUsuario(String id){
        user = usuariosRepository.validarUsuario(id);
        return user;
    }
    public MutableLiveData<User> getUser(String id){
        user = usuariosRepository.getUser(id);
        return user;
    }

    public MutableLiveData<User> getCurrentUser(String token){
        user = usuariosRepository.getCurrentUser(token);
        return  user;
    }
    public MutableLiveData<User> upgradeTecnico(String id){
        user = usuariosRepository.upgradeTecnico(id);
        return user;
    }

    public MutableLiveData<User> upgradeProfile(String id, Name name){
        user = usuariosRepository.updateUsuario(id, name);
        return  user;
    }

    public void borrarUsuario(String id){
        usuariosRepository.borrarUsuario(id);
    }
    public void borrarFoto(String id){
        usuariosRepository.borrarImagen(id);
    }

    public void setUsuarioId(String usuarioId) {
        this.idSeleccionada.setValue(usuarioId);
    }

    public MutableLiveData<String> getUsuarioId() {
        return idSeleccionada;
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

