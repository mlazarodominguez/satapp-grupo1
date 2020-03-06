package com.example.satapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.satapp.repository.AnotacionRepository;

import java.util.List;

public class AnotacionViewModel extends AndroidViewModel {
    private MutableLiveData<List<String>> listAnotaciones;
    private AnotacionRepository anotacionRepository;
    private MutableLiveData<String> anotacion;

    public AnotacionViewModel(@NonNull Application application) {
        super(application);
        anotacionRepository = new AnotacionRepository();
        listAnotaciones = new MutableLiveData<>();
    }

    public MutableLiveData<List<String>> getListAnotaciones(String token) {
        listAnotaciones = anotacionRepository.getAnotaciones(token);
        return listAnotaciones;
    }

    public MutableLiveData<String> getAnotacion(String id, String token) {
        anotacion = anotacionRepository.getAnotacionDetail(id, token);
        return anotacion;
    }
}
