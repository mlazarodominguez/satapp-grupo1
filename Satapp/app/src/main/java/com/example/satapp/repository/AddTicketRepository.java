package com.example.satapp.repository;

import com.example.satapp.retrofit.AddTicketService;
import com.example.satapp.retrofit.ServiceGenerator;

public class AddTicketRepository {

    AddTicketService service;
    ServiceGenerator serviceGenerator;

    public AddTicketRepository() {
        service = serviceGenerator.createService(AddTicketService.class);
    }

    public void addTicket() {

    }

}
