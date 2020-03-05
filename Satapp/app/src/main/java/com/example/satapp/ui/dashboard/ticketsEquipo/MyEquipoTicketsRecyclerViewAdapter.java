package com.example.satapp.ui.dashboard.ticketsEquipo;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.satapp.R;
import com.example.satapp.models.TicketsResponse;
import com.example.satapp.viewmodel.TicketsEquipoViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyEquipoTicketsRecyclerViewAdapter extends RecyclerView.Adapter<MyEquipoTicketsRecyclerViewAdapter.ViewHolder> {

    private List<TicketsResponse> mValues;
    TicketsEquipoViewModel ticketsEquipoViewModel;
    Context context;

    public MyEquipoTicketsRecyclerViewAdapter(Context ctx, List<TicketsResponse> ticketsResponses, TicketsEquipoViewModel ticketsEquipoViewModel) {
        this.context = ctx;
        mValues = ticketsResponses;
        this.ticketsEquipoViewModel = ticketsEquipoViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_equipotickets, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        // TODO: Una vez averiguado el json que devuelve la peticion añadir los atributos necesarios para mostrar
        holder.tvTitulo.setText(holder.mItem.getTitulo());
        holder.tvCreadoPor.setText("Creado por: " + holder.mItem.getCreadoPor().getName());
        holder.tvInventariable.setText("Equipo: " + holder.mItem.getInventariable());
        holder.tvEstado.setText("Estado: " + holder.mItem.getEstado());
        holder.tvFechaCreacion.setText("Creado: " + holder.mItem.getFechaCreacion());
        holder.tvDescripcion.setText("Descripción: " + holder.mItem.getDescripcion());

        if (holder.mItem.getFotos().isEmpty())
            Glide.with(context).load("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/600px-No_image_available.svg.png").into(holder.ivFoto);
        else
            Glide.with(context).load(holder.mItem.getFotos()).into(holder.ivFoto);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setData(List<TicketsResponse> list){
        if(this.mValues != null) {
            this.mValues.clear();
        } else {
            this.mValues =  new ArrayList<>();
        }
        this.mValues.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView ivFoto;
        public final TextView tvTitulo;
        public final TextView tvCreadoPor;
        public final TextView tvInventariable;
        public final TextView tvEstado;
        public final TextView tvFechaCreacion;
        public final TextView tvDescripcion;
        public TicketsResponse mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivFoto = view.findViewById(R.id.imageViewFotoTicket);
            tvTitulo = view.findViewById(R.id.textViewTitulo);
            tvCreadoPor = view.findViewById(R.id.textViewCreadoPor);
            tvInventariable = view.findViewById(R.id.textViewInventariable);
            tvEstado = view.findViewById(R.id.textViewEstado);
            tvFechaCreacion = view.findViewById(R.id.textViewFechaCreacion);
            tvDescripcion = view.findViewById(R.id.textViewDescripcion);
        }

    }
}
