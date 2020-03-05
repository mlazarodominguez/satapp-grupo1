package com.example.satapp.ui.notifications;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.satapp.R;
import com.example.satapp.viewmodel.UbicacionViewModel;

import java.util.ArrayList;
import java.util.List;


public class MyUbicacionRecyclerViewAdapter extends RecyclerView.Adapter<MyUbicacionRecyclerViewAdapter.ViewHolder> {

    private List<String> mValues;
    UbicacionViewModel ubicacionViewModel;
    Context context;

    public MyUbicacionRecyclerViewAdapter(Context ctx, List<String> ubicaciones, UbicacionViewModel ubicacionViewModel) {
        this.context = ctx;
        mValues = ubicaciones;
        this.ubicacionViewModel = ubicacionViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ubicacion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvUbicacion.setText(holder.mItem);

        holder.tvUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Aquí va el filtrao de los equipos de las aulas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setData(List<String> list){
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
        public final TextView tvUbicacion;
        public String mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvUbicacion = view.findViewById(R.id.textViewUbicacion);
        }

    }
}
