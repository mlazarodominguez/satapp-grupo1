package com.example.satapp.ui.dashboard;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.satapp.R;
import com.example.satapp.models.Equipo;


import java.util.List;


public class MyEquiposListRecyclerViewAdapter extends RecyclerView.Adapter<MyEquiposListRecyclerViewAdapter.ViewHolder> {

    private List<Equipo> mValues;
    private Context ctx;
    private int layout;
    private final EquipoViewModel equipoViewModel;

    public MyEquiposListRecyclerViewAdapter(List<Equipo> items, Context ctx, int layout, EquipoViewModel equipoViewModel) {
        mValues = items;
        this.ctx =ctx;
        this.layout = layout;
        this.equipoViewModel = equipoViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_equiposlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.titulo.setText(holder.mItem.getNombre());

        Glide.with(ctx)
                .load(holder.mItem.getImagen())
                .centerCrop()
                .into(holder.imagenEquipo);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setData(List<Equipo> equipoList){
        this.mValues = equipoList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mValues != null)
            return this.mValues.size();
        else return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imagenEquipo;
        public final TextView titulo;
        public Equipo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imagenEquipo = (ImageView) view.findViewById(R.id.imageViewEquipo);
            titulo = (TextView) view.findViewById(R.id.textViewTituloEquipo);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titulo.getText() + "'";
        }
    }
}
