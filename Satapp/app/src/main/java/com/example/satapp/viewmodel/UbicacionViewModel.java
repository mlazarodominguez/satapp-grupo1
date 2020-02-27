package com.example.satapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.satapp.repository.UbicacionRepository;

import java.util.List;

public class UbicacionViewModel extends AndroidViewModel {
    private MutableLiveData<List<String>> listUbicaciones;
    private UbicacionRepository ubicacionRepository;

    public UbicacionViewModel(@NonNull Application application) {
        super(application);
        ubicacionRepository = new UbicacionRepository();
        listUbicaciones = ubicacionRepository.getUbicacionesList();
    }

    public MutableLiveData<List<String>> getListUbicaciones() {
        return listUbicaciones;
    }
}
