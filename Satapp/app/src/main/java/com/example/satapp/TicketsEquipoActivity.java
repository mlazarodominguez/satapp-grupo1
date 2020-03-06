package com.example.satapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.satapp.common.Constantes;
import com.example.satapp.viewmodel.EquipoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TicketsEquipoActivity extends AppCompatActivity {

    EquipoViewModel equipoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_equipo);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: cambiar clase que direcciona cuando se cree y funciona bien la peticion de lista de tickets
                Intent intent = new Intent(TicketsEquipoActivity.this, AddTicketActivity.class);
                startActivity(intent);
            }
        });

        equipoViewModel = new ViewModelProvider(this)
                .get(EquipoViewModel.class);

        equipoViewModel.getEquipo3().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String equipoId) {
                if(equipoId != null) {
                    Intent i = new Intent(TicketsEquipoActivity.this, AddTicketActivity.class);
                    i.putExtra(Constantes.EXTRA_ID_INVENTARIABLE, equipoId);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}
