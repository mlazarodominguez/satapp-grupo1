package com.example.satapp.repository;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.satapp.common.MyApp;
import com.example.satapp.models.Equipo;
import com.example.satapp.retrofit.IEquipoService;
import com.example.satapp.retrofit.ServiceGenerator;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EquipoRepository {
    IEquipoService service;
    ServiceGenerator serviceGenerator;

    MutableLiveData<List<Equipo>> equipos;

    public EquipoRepository(){
        service = serviceGenerator.createService(IEquipoService.class);
        equipos = null;
    }

    public MutableLiveData<List<Equipo>> getEquipos(String token){
        final MutableLiveData<List<Equipo>> data = new MutableLiveData<>();
        Call<List<Equipo>> call = service.getEquipos(token);
        call.enqueue(new Callback<List<Equipo>>() {
                         @Override
                         public void onResponse(Call<List<Equipo>> call, Response<List<Equipo>> response) {
                             if (response.isSuccessful()) {
                                 data.setValue(response.body());
                             } else {
                                 Toast.makeText(MyApp.getContext(), "Error on the response from the Api", Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onFailure(Call<List<Equipo>> call, Throwable t) {
                             Toast.makeText(MyApp.getContext(), "Error in the connection", Toast.LENGTH_SHORT).show();
                         }
                     }
        );
        return data;
    }


    public MutableLiveData<Equipo>getInventariable(String id, String token) {
        final MutableLiveData<Equipo> inventariableEdit = new MutableLiveData<>();
        Call<Equipo> editInventariableCall = service.getEquipoDetalles(id,token);
        editInventariableCall.enqueue(new Callback<Equipo>() {
            @Override
            public void onResponse(Call<Equipo> call, Response<Equipo> response) {
                if(response.isSuccessful()){
                    inventariableEdit.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<Equipo> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error in the connection", Toast.LENGTH_SHORT).show();
            }
        });
        return inventariableEdit;
    }

    public void getInventaribleToEdit(String id, String token,Equipo equipo) {
        final MutableLiveData<Equipo> inventariableEdit = new MutableLiveData<>();
        Call<Equipo> inventariableCall = service.editInventariable(id,token,equipo);
        inventariableCall.enqueue(new Callback<Equipo>() {
            @Override
            public void onResponse(Call<Equipo> call, Response<Equipo> response) {
                if(response.isSuccessful()){
                    inventariableEdit.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<Equipo> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error in the connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editInventariableImg(String id, String token, MultipartBody.Part imagen) {
        final MutableLiveData<Equipo> inventariableEdit = new MutableLiveData<>();
        Call<Equipo> inventariableCall = service.editImg(id,token,imagen);
        inventariableCall.enqueue(new Callback<Equipo>() {
            @Override
            public void onResponse(Call<Equipo> call, Response<Equipo> response) {
                if(response.isSuccessful()){
                    inventariableEdit.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<Equipo> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error in the connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public MutableLiveData<Bitmap> getImagenEquipo(String id, String token) {
        final MutableLiveData<Bitmap> imagenBitMap = new MutableLiveData<>();
        Call<ResponseBody> imagenEquipo = service.getImagenEquipo(id, token);
        imagenEquipo.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    Bitmap fotoBitMap = BitmapFactory.decodeStream(response.body().byteStream());
                    imagenBitMap.setValue(fotoBitMap);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error in the connection", Toast.LENGTH_SHORT).show();
            }
        });
        return imagenBitMap;
    }

}
