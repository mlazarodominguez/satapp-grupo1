package com.example.satapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.satapp.models.Equipo;
import com.example.satapp.repository.EquipoRepository;

public class EquipoEditViewModel extends AndroidViewModel {
    EquipoRepository equipoRepository;
    MutableLiveData<Equipo> equipo;

    public EquipoEditViewModel(@NonNull Application application) {
        super(application);
        equipoRepository = new EquipoRepository();
    }

    public MutableLiveData<Equipo> getEquipo(String id, String token) {
        equipo = equipoRepository.getInventariable(id, token);
        return equipo;
    }
    public void updateEquipo(String id,String token, Equipo equipo){
        equipoRepository.getInventaribleToEdit(id,token,equipo);
    }
}
