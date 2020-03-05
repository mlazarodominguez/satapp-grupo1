package com.example.satapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.satapp.models.AddTicketResponse;
import com.example.satapp.repository.AddTicketRepository;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddTicketViewModel extends AndroidViewModel {
    AddTicketRepository addTicketRepository;

    public AddTicketViewModel(@NonNull Application application) {
        super(application);
        addTicketRepository = new AddTicketRepository();
    }

    public MutableLiveData<AddTicketResponse> addTicket(RequestBody titulo,
                                                        RequestBody descripcion,
                                                        RequestBody inventariable,
                                                        List<MultipartBody.Part> fotos) {
        return addTicketRepository.addTicket(titulo, descripcion, inventariable, fotos);
    }
}
