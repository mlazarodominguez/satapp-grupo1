package com.example.satapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.satapp.models.TicketsResponse;
import com.example.satapp.repository.UbicacionRepository;

import java.util.List;

public class TicketsEquipoViewModel extends AndroidViewModel {
    private MutableLiveData<List<TicketsResponse>> listTicketsEquipo;
    private UbicacionRepository ubicacionRepository;

    public TicketsEquipoViewModel(@NonNull Application application) {
        super(application);
        ubicacionRepository = new UbicacionRepository();
        listTicketsEquipo = ubicacionRepository.getTicketsEquipoList();
    }

    public MutableLiveData<List<TicketsResponse>> getListTicketsEquipo() {
        return listTicketsEquipo;
    }
}
