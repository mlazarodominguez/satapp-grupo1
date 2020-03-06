package com.example.satapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.satapp.models.UtilToken;
import com.example.satapp.viewmodel.AnotacionViewModel;

public class AnotacionDetailActivity extends AppCompatActivity {

    AnotacionViewModel anotacionViewModel;
    TextView tv;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotacion_detail);

        retrofitInit();
        findViews();
        events();
    }

    private void retrofitInit() {
        anotacionViewModel = new ViewModelProvider(this).get(AnotacionViewModel.class);
        extras = getIntent().getExtras();
    }

    private void findViews() {
        /*
        tvUbicacion = findViewById(R.id.textViewUbicacionEquipoDetail);
        tvTickets = findViewById(R.id.textViewTicketsDetail);
        tvTipo = findViewById(R.id.textViewTipoEquipoDetail);
        tvTitulo = findViewById(R.id.textViewTituloEquipoDetail);
        ivFoto = findViewById(R.id.imageViewEquipoDetail);
         */
    }

    private void events() {
        anotacionViewModel.getAnotacion(extras.getString("idAnotacion"), UtilToken.getToken(this)).observe(this, new Observer<String>() {
            @Override
            public void onChanged(final String anotacion) {
                /*
                tvUbicacion.setText(equipo.getUbicacion());
                tvTipo.setText(equipo.getTipo());
                tvTitulo.setText(equipo.getNombre());
                 */
            }
        });
    }
}
