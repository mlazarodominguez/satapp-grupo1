package com.example.satapp.ui.notifications;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        /*        if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }*/
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
