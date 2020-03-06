package com.example.satapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.satapp.common.Constantes;
import com.example.satapp.ui.dashboard.NuevoEquipoDialogFragment;
import com.example.satapp.ui.dashboard.ticketsEquipo.DialogFragmentAddTicket;
import com.example.satapp.viewmodel.EquipoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TicketsEquipoActivity extends AppCompatActivity {

    EquipoViewModel equipoViewModel;
    String idEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_equipo);

        equipoViewModel = new ViewModelProvider(this)
                .get(EquipoViewModel.class);

        Intent intent = this.getIntent();
        if (intent != null) {
            idEquipo = intent.getStringExtra(Constantes.EXTRA_ID_INVENTARIABLE);
        }

        equipoViewModel.setEquipo(idEquipo);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogFragmentAddTicket().show(getSupportFragmentManager(), "addTicket");
            }
        });
    }

    public String sendId() {
        return idEquipo;
    }
}
