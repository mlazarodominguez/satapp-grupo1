package com.example.satapp.ui.dashboard.ticketsEquipo;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        public TicketsResponse mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
        }

    }
}
