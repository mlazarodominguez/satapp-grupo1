package com.example.satapp.viewmodel;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.satapp.models.Equipo;
import com.example.satapp.repository.EquipoRepository;

import java.util.List;

import okhttp3.ResponseBody;

public class EquipoViewModel extends AndroidViewModel {

    private MutableLiveData<List<Equipo>> equipos;
    private MutableLiveData<Bitmap> bitmapImg;
    private MutableLiveData<List<String>> tipos;
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

    public MutableLiveData<Bitmap> getImagenEquipo(String id, String token) {
        bitmapImg = equipoRepository.getImagenEquipo(id, token);
        return bitmapImg;
    }

    public MutableLiveData<List<String>> getAllTipos(String token){
        tipos = equipoRepository.getAllTipos(token);
        return tipos;
    }

    public MutableLiveData<Equipo> deleteEquipo(String id, String token){
        equipo = equipoRepository.deleteEquipo(id,token);
        return equipo;
    }

    public void setEquipo(String id) {
        this.idEquipoSeleccionado.setValue(id);
    }


    public MutableLiveData<String> getEquipo() {
        return idEquipoSeleccionado;
    }

}