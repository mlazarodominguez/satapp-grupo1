package com.example.satapp.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.satapp.EditInventariableActivity;
import com.example.satapp.EquipoDetailActivity;
import com.example.satapp.MainActivity;
import com.example.satapp.R;
import com.example.satapp.common.Constantes;
import com.example.satapp.models.Equipo;
import com.example.satapp.models.UtilToken;
import com.example.satapp.viewmodel.EquipoViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyEquiposListRecyclerViewAdapter extends RecyclerView.Adapter<MyEquiposListRecyclerViewAdapter.ViewHolder> {

    private List<Equipo> mValues;
    private Context ctx;
    private int layout;
    private final EquipoViewModel equipoViewModel;
    UtilToken utilToken;
    Intent intentDetail;


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
        holder.tipo.setText(holder.mItem.getTipo());
        holder.ubicacion.setText(holder.mItem.getUbicacion());

        equipoViewModel.getImagenEquipo(holder.mItem.getId(),(utilToken.getToken(ctx))).observe((LifecycleOwner) ctx, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                Glide.with(ctx)
                        .load(bitmap)
                        .centerCrop()
                        .into(holder.imagenEquipo);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equipoViewModel != null) {
                    equipoViewModel.setEquipo(holder.mItem.getId());
                    Intent editIntent =  new Intent(ctx, EditInventariableActivity.class);
                    editIntent.putExtra(Constantes.EXTRA_ID_INVENTARIABLE, holder.mItem.getId());
                    ctx.startActivity(editIntent);
                }
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equipoViewModel != null) {
                    equipoViewModel.setEquipo(holder.mItem.getId());
                    intentDetail =  new Intent(ctx, EquipoDetailActivity.class);
                    equipoViewModel.getIdEquipo().observe((LifecycleOwner) ctx, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            intentDetail.putExtra(Constantes.EXTRA_ID_INVENTARIABLE, s);
                            ctx.startActivity(intentDetail);
                        }
                    });
                }
            }
        });

        holder.btnBorrarEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            equipoViewModel.deleteEquipo(holder.mItem.getId().toString(),utilToken.getToken(ctx));
            }
        });

    }

    public void setData(List<Equipo> equipoList){
        this.mValues = equipoList;
        notifyDataSetChanged();
    }


    public void updateList(List<Equipo> newList){
        if(mValues != null) {
            mValues.clear();
        } else {
            mValues = new ArrayList<>();
        }

        mValues.addAll(newList);
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
        public final TextView tipo;
        public final TextView ubicacion;
        public Equipo mItem;
        public ImageButton btnEdit;
        public ImageButton btnBorrarEquipo;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            imagenEquipo = (ImageView) view.findViewById(R.id.imageViewEquipo);
            titulo = (TextView) view.findViewById(R.id.textViewTituloEquipo);
            tipo= view.findViewById(R.id.textViewTipoEquipo);
            ubicacion = view.findViewById(R.id.textViewUbicacionEquipo);
            btnBorrarEquipo = view.findViewById(R.id.buttonBorrarEquipo);
            btnEdit = view.findViewById(R.id.buttonEditMomentaneo);

        }



        @Override
        public String toString() {
            return super.toString() + " '" + titulo.getText() + "'";
        }
    }
}
