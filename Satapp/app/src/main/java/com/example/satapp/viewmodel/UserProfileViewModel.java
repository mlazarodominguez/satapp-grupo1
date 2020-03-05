package com.example.satapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.satapp.models.User;
import com.example.satapp.repository.UsuariosRepository;

public class UserProfileViewModel extends AndroidViewModel {

    private UsuariosRepository usuariosRepository;
    public MutableLiveData<User> usuario;

    public UserProfileViewModel(@NonNull Application application) {
        super(application);
        usuariosRepository = new UsuariosRepository();

    }

    public MutableLiveData<User> getCurrentUser (String token){
        usuario = usuariosRepository.getCurrentUser(token);
        return usuario;
    }
}
