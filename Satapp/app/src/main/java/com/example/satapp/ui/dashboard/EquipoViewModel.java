package com.example.satapp.ui.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.satapp.models.Equipo;

import java.util.List;

public class EquipoViewModel extends AndroidViewModel {

    private MutableLiveData<List<Equipo>> equipos;
    EquipoRepository equipoRepository;
    MutableLiveData<Integer> idEquipoSeleccionado;

    public EquipoViewModel(@NonNull Application application) {
        super(application);
        equipoRepository = new EquipoRepository();
        this.idEquipoSeleccionado = new MutableLiveData<>();
        this.idEquipoSeleccionado.setValue(null);
    }

    public MutableLiveData<List<Equipo>> getSeries() {
        equipos = equipoRepository.getEquipos();
        return equipos;
    }



}