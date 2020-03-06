package com.example.satapp;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.satapp.common.MyApp;
import com.example.satapp.models.User;
import com.example.satapp.models.UtilToken;
import com.example.satapp.retrofit.IUsuarioService;
import com.example.satapp.retrofit.ServiceGenerator;
import com.example.satapp.viewmodel.UsuarioViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyListaUsuarioRecyclerViewAdapter extends RecyclerView.Adapter<MyListaUsuarioRecyclerViewAdapter.ViewHolder> {

    private List<User> mValues;
    UsuarioViewModel usuarioViewModel;
    Context context;
    IUsuarioService service;
    ServiceGenerator serviceGenerator;

    public MyListaUsuarioRecyclerViewAdapter(Context ctx,List<User> items,UsuarioViewModel usuarioViewModel) {
        mValues = items;
        this.context = ctx;
        this.usuarioViewModel= usuarioViewModel;
        service = serviceGenerator.createService(IUsuarioService.class);
    }

   @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_lista_usuario, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues!=null){
        holder.mItem= mValues.get(position);
        holder.tvNombreLista.setText(holder.mItem.getEmail());
        holder.tvEmail.setText(holder.mItem.getName());
        holder.tvRol.setText(holder.mItem.getRole().toUpperCase());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != usuarioViewModel){
                    usuarioViewModel.setUsuarioId(holder.mItem.getId());
                }
            }
        });
        if(holder.mItem.getPicture()!=null&& holder.mItem!=null){
            Call<ResponseBody> call = service.getAvatarUser(holder.mItem.getId(),UtilToken.getToken(context));
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Bitmap fotoBitMpa = BitmapFactory.decodeStream(response.body().byteStream());
                    Log.e("bitmap",fotoBitMpa.toString());
                   Glide.with(context)
                            .load(fotoBitMpa)
                           .circleCrop()
                            .into(holder.ivAvatarLista);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }else{
            Glide.with(context)
                    .load("https://d500.epimg.net/cincodias/imagenes/2016/07/04/lifestyle/1467646262_522853_1467646344_noticia_normal.jpg")
                    .circleCrop()
                    .into(holder.ivAvatarLista);
        }


        }
        if(holder.mItem.getRole().equalsIgnoreCase("Admin")){
            holder.btnEliminarUsuario.setVisibility(View.INVISIBLE);
        }
        holder.btnEliminarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                alertDialogBuilder.setTitle("ELIMINAR");
                alertDialogBuilder
                        .setMessage("¿Quieres eliminar este usuario?")
                        .setCancelable(false)
                        .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                holder.btnValidar.setVisibility(View.INVISIBLE);
                                usuarioViewModel.borrarUsuario(holder.mItem.getId());

                                mValues.remove(holder.mItem);
                                notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();




            }
        });
        if(holder.mItem.getValidated()==false){
            holder.btnValidar.setVisibility(View.VISIBLE);
            holder.btnValidar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);
                        alertDialogBuilder.setTitle("VALIDAR");
                    alertDialogBuilder
                            .setMessage("Validar a este usuario")
                            .setCancelable(false)
                            .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    holder.btnValidar.setVisibility(View.INVISIBLE);
                                    usuarioViewModel.validarUsuario(holder.mItem.getId());

                                        mValues.remove(holder.mItem);
                                        notifyDataSetChanged();

                                }
                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                }


            });

        }


    }

    public void setData(List<User> list){
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
        if(mValues != null){
            return mValues.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvNombreLista;
        public final ImageView ivAvatarLista;
        public final Button btnValidar;
        public final TextView tvEmail;
        public final Button btnEliminarUsuario;
        public final TextView tvRol;
        public User mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvNombreLista = (TextView) view.findViewById(R.id.ivNombreLista);
            ivAvatarLista = (ImageView) view.findViewById(R.id.ivAvatarLista);
            btnValidar = (Button) view.findViewById(R.id.btnValidar);
            tvEmail = (TextView) view.findViewById(R.id.tvEmailLista);
            tvRol = (TextView) view.findViewById(R.id.tvRol);
            btnEliminarUsuario = (Button) view.findViewById(R.id.btnEliminarUser);
        }

    }
}
