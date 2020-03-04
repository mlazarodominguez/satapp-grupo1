package com.example.satapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.satapp.models.Equipo;
import com.example.satapp.repository.EquipoRepository;

import java.util.List;

public class EquipoViewModel extends AndroidViewModel {

    private MutableLiveData<List<Equipo>> equipos;
    private MutableLiveData<Equipo> equipo;
    EquipoRepository equipoRepository;
    MutableLiveData<String> idEquipoSeleccionado;

    public EquipoViewModel(@NonNull Application application) {
        super(application);
        equipoRepository = new EquipoRepository();
        this.idEquipoSeleccionado = new MutableLiveData<>();
        this.idEquipoSeleccionado.setValue(null);
    }

    public MutableLiveData<List<Equipo>> getEquipos(String token) {
        equipos = equipoRepository.getEquipos(token);
        return equipos;
    }

    public void setEquipo(String id) {
        this.idEquipoSeleccionado.setValue(id);
    }

    public MutableLiveData<String> getEquipo() {
        return idEquipoSeleccionado;
    }
}