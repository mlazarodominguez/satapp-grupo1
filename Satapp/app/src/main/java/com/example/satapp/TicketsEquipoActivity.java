package com.example.satapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TicketsEquipoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_equipo);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: cambiar clase que direcciona cuando se cree y funciona bien la peticion de lista de tickets
                Intent intent = new Intent(TicketsEquipoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
