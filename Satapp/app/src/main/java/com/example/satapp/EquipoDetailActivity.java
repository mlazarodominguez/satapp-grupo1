package com.example.satapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.satapp.common.Constantes;
import com.example.satapp.models.Equipo;
import com.example.satapp.models.UtilToken;
import com.example.satapp.retrofit.IEquipoService;
import com.example.satapp.retrofit.ServiceGenerator;
import com.example.satapp.viewmodel.EquipoEditViewModel;
import com.example.satapp.viewmodel.EquipoViewModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquipoDetailActivity extends AppCompatActivity {

    EquipoEditViewModel equipoViewModel;
    TextView tvUbicacion, tvTipo, tvTitulo, tvTickets;
    ImageView ivFoto;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_detail);

        retrofitInit();
        findViews();
        events();
    }

    private void retrofitInit() {
        equipoViewModel = new ViewModelProvider(this).get(EquipoEditViewModel.class);
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
        equipoViewModel.getEquipo(extras.getString(Constantes.EXTRA_ID_INVENTARIABLE), UtilToken.getToken(this)).observe(this, new Observer<Equipo>() {
            @Override
            public void onChanged(final Equipo equipo) {
                tvUbicacion.setText(equipo.getUbicacion());
                tvTipo.setText(equipo.getTipo());
                tvTitulo.setText(equipo.getNombre());

                IEquipoService service = ServiceGenerator.createService(IEquipoService.class);

                Call<ResponseBody> imagenEquipo = service.getImagenEquipo(equipo.getId(),UtilToken.getToken(EquipoDetailActivity.this));

                imagenEquipo.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Bitmap fotoBitMap = BitmapFactory.decodeStream(response.body().byteStream());

                        Glide.with(EquipoDetailActivity.this)
                                .load(fotoBitMap)
                                .centerCrop()
                                .into(ivFoto);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Upload error", t.getMessage());
                    }

                });

                tvTickets.setText("Ver Tickets");

                tvTickets.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(EquipoDetailActivity.this, TicketsEquipoActivity.class);
                        i.putExtra(Constantes.EXTRA_ID_INVENTARIABLE, equipo.getId());
                        startActivity(i);
                        finish();
                    }
                });
            }
        });
    }
}
