package com.example.satapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

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

    public void addTicket(List<MultipartBody.Part> avatar,
                          RequestBody titulo,
                          RequestBody descripcion) {
        addTicketRepository.addTicket(avatar, titulo, descripcion);
    }
}
