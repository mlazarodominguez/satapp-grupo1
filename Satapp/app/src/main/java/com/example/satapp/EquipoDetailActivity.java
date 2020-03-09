package com.example.satapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.satapp.common.Constantes;
import com.example.satapp.common.MyApp;
import com.example.satapp.models.Equipo;
import com.example.satapp.models.UtilToken;
import com.example.satapp.viewmodel.EquipoEditViewModel;
import com.example.satapp.viewmodel.EquipoViewModel;
import com.example.satapp.viewmodel.TicketDetailViewModel;

public class EquipoDetailActivity extends AppCompatActivity {

    EquipoViewModel equipoViewModel;
    TextView tvUbicacion, tvTipo, tvTitulo, tvTickets;
    ImageView ivFoto;
    Bundle bundle;
    String id, jwt;
    EquipoEditViewModel equipoEditViewModel;
    EquipoViewModel equipoViewModelImagen;
    TicketDetailViewModel ticketDetailViewModel;

    Bundle extras;
    Equipo e;
    UtilToken utilToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_detail);

        bundle = getIntent().getExtras();
        id = bundle.getString(Constantes.EXTRA_ID_INVENTARIABLE);

        jwt = UtilToken.getToken(this);
        retrofitInit();
        findViews();
        events();
    }

    private void retrofitInit() {
        equipoViewModel = new ViewModelProvider(this).get(EquipoViewModel.class);
        equipoViewModelImagen = new ViewModelProvider(this).get(EquipoViewModel.class);
        extras = getIntent().getExtras();
    }

    private void findViews() {
        tvUbicacion = findViewById(R.id.textViewUbicacionEquipoDetail);
        tvTickets = findViewById(R.id.textViewTicketsDetail);
        tvTipo = findViewById(R.id.textViewTipoEquipoDetail);
        tvTitulo = findViewById(R.id.textViewTituloEquipoDetail);
        ivFoto = findViewById(R.id.imageViewEquipoDetail);
    }

    private void events() {

        equipoEditViewModel.getEquipo(id, jwt).observe(this, new Observer<Equipo>() {
            @Override
            public void onChanged(final Equipo equipo) {
                tvUbicacion.setText(equipo.getUbicacion());
                tvTipo.setText(equipo.getTipo());
                tvTitulo.setText(equipo.getNombre());


                equipoViewModelImagen.getImagenEquipo(equipo.getId(),(utilToken.getToken(MyApp.getContext()))).observe(EquipoDetailActivity.this, new Observer<Bitmap>() {
                    @Override
                    public void onChanged(Bitmap bitmap) {
                        Glide.with(EquipoDetailActivity.this)
                                .load(bitmap)
                                .centerCrop()
                                .into(ivFoto);
                    }
                });


                tvTickets.setText("Ver Tickets");

                tvTickets.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(EquipoDetailActivity.this, TicketsEquipoActivity.class);
                        i.putExtra(Constantes.EXTRA_ID_INVENTARIABLE, id);
                        startActivity(i);
                        finish();
                    }
                });



            }
        });
    }



}
