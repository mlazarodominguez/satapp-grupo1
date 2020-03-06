package com.example.satapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.satapp.models.TicketResponse;
import com.example.satapp.repository.UbicacionRepository;

public class TicketDetailViewModel extends AndroidViewModel {
    UbicacionRepository ticketDetailRepo;
    MutableLiveData<TicketResponse> ticketDetail;

    public TicketDetailViewModel(@NonNull Application application) {
        super(application);
        ticketDetailRepo = new UbicacionRepository();
    }

    public MutableLiveData<TicketResponse> getTicketDetail(String id, String token) {
        ticketDetail = ticketDetailRepo.getTicketDetail(id, token);
        return ticketDetail;
    }
}
