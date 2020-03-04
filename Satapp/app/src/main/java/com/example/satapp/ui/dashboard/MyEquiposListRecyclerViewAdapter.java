package com.example.satapp.ui.dashboard;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.satapp.R;
import com.example.satapp.models.Equipo;
import com.example.satapp.models.UtilToken;
import com.example.satapp.retrofit.IEquipoService;
import com.example.satapp.retrofit.ServiceGenerator;
import com.example.satapp.viewmodel.EquipoViewModel;


import java.io.File;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyEquiposListRecyclerViewAdapter extends RecyclerView.Adapter<MyEquiposListRecyclerViewAdapter.ViewHolder> {

    private List<Equipo> mValues;
    private Context ctx;
    private int layout;
    private final EquipoViewModel equipoViewModel;
    UtilToken utilToken;

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

        IEquipoService service = ServiceGenerator.createService(IEquipoService.class);


        Call<ResponseBody> imagenEquipo = service.getImagenEquipo(holder.mItem.getId(),(utilToken.getToken(ctx)));

        imagenEquipo.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.i("imagen","" + response.body().byteStream());
                Bitmap fotoBitMap = BitmapFactory.decodeStream(response.body().byteStream());


                Glide.with(ctx)
                        .load(fotoBitMap)
                        .centerCrop()
                        .into(holder.imagenEquipo);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error", t.getMessage());

            }

        });

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
