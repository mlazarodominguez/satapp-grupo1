package com.example.satapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.satapp.models.TicketResponse;
import com.example.satapp.models.UtilToken;
import com.example.satapp.viewmodel.TicketDetailViewModel;

public class TicketDetailActivity extends AppCompatActivity {

    TicketDetailViewModel ticketDetailViewModel;
    TextView tvDescripcion, tvTitulo;
    ImageView ivFoto;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        retrofitInit();
        findViews();
        events();
    }

    private void retrofitInit() {
        ticketDetailViewModel = new ViewModelProvider(this).get(TicketDetailViewModel.class);
        extras = getIntent().getExtras();
    }

    private void findViews() {
        tvDescripcion = findViewById(R.id.textViewDescripcionDetail);
        tvTitulo = findViewById(R.id.textViewTituloDetail);
        ivFoto = findViewById(R.id.imageViewFotoTicketDetail);
    }

    private void events() {
        ticketDetailViewModel.getTicketDetail(extras.getString("idTicketDetail"), UtilToken.getToken(this)).observe(this, new Observer<TicketResponse>() {
            @Override
            public void onChanged(final TicketResponse ticketResponse) {
                tvDescripcion.setText(ticketResponse.getDescripcion());
                tvTitulo.setText(ticketResponse.getTitulo());

                //if (ticketResponse.getFotos().isEmpty())
                Glide.with(TicketDetailActivity.this)
                        .load(ticketResponse.getFotos().get(0))
                        .centerCrop()
                        .into(ivFoto);
            }
        });
    }
}
