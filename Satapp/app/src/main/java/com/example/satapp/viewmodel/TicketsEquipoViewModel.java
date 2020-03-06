package com.example.satapp.viewmodel;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.satapp.models.TicketsResponse;
import com.example.satapp.repository.UbicacionRepository;

import java.util.List;

public class TicketsEquipoViewModel extends AndroidViewModel {
    private MutableLiveData<List<TicketsResponse>> listTicketsEquipo;
    private UbicacionRepository ubicacionRepository;
    private MutableLiveData<Bitmap> bitmapImg;

    public TicketsEquipoViewModel(@NonNull Application application) {
        super(application);
        ubicacionRepository = new UbicacionRepository();
    }

    public MutableLiveData<List<TicketsResponse>> getListTicketsEquipo(String id) {
        listTicketsEquipo = ubicacionRepository.getTicketsEquipoList(id);
        return listTicketsEquipo;
    }
    public MutableLiveData<Bitmap> getImagenTicket(String id, String img , String token) {
        bitmapImg = ubicacionRepository.getImagenTicket(id,img,token);
        return bitmapImg;
    }

}
