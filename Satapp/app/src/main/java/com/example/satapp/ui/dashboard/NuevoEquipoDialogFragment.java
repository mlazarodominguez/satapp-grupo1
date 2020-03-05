package com.example.satapp.ui.dashboard;



import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.satapp.R;
import com.example.satapp.models.Equipo;
import com.example.satapp.models.UtilToken;
import com.example.satapp.retrofit.IEquipoService;
import com.example.satapp.retrofit.ServiceGenerator;


import org.joda.time.LocalDate;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NuevoEquipoDialogFragment extends DialogFragment {

    private static final int READ_REQUEST_CODE = 42;
    ImageView imageView;
    Uri uriSelected;
    View v;
    public EditText codigo, ubicacion, descripcion, nombre, tipo;
    public LocalDate now;
    UtilToken utilToken;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Configura el dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Configuración del diálogo

        builder.setTitle("Nuevo equipo");


        builder.setCancelable(true);

        v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_nuevo_equipo_dialog, null);
        builder.setView(v);

        codigo= v.findViewById(R.id.editTextCodigoEquipo);
        ubicacion= v.findViewById(R.id.editTextUbicacionEquipo);
        descripcion = v.findViewById(R.id.editTextDescripcionEquipo);
        nombre= v.findViewById(R.id.editTextNombreEquipo);
        tipo = v.findViewById(R.id.editTextTipoEquipo);


        builder.setPositiveButton(R.string.button_guardar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (uriSelected == null) {


                    IEquipoService service = ServiceGenerator.createService(IEquipoService.class);

                    try {
                        InputStream inputStream = getContext().getContentResolver().openInputStream(uriSelected);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                        int cantBytes;
                        byte[] buffer = new byte[1024 * 4];

                        while ((cantBytes = bufferedInputStream.read(buffer, 0, 1024 * 4)) != -1) {
                            baos.write(buffer, 0, cantBytes);
                        }

                        RequestBody requestFile = RequestBody.create(MediaType.parse(getContext().getContentResolver().getType(uriSelected)), baos.toByteArray());

                        MultipartBody.Part body = MultipartBody.Part.createFormData("imagen", "imagen", requestFile);

                        RequestBody code = RequestBody.create(MultipartBody.FORM, codigo.toString());
                        RequestBody name = RequestBody.create(MultipartBody.FORM, nombre.toString());
                        RequestBody type = RequestBody.create(MultipartBody.FORM, tipo.toString());
                        RequestBody description = RequestBody.create(MultipartBody.FORM, descripcion.toString());


                        Call<Equipo> callNuevoEquipo = service.nuevoEquipo(utilToken.getToken(getContext()), body, code, name, type, description);

                        callNuevoEquipo.enqueue(new Callback<Equipo>() {
                            @Override
                            public void onResponse(Call<Equipo> call, Response<Equipo> response) {
                                if (response.isSuccessful()) {
                                    Log.d("Uploaded", "Éxito");
                                    Log.d("Uploaded", response.body().toString());
                                } else {
                                    Log.e("Upload error", response.errorBody().toString());
                                }
                                Log.d("REPSONSE EQUIPO", ""+response);
                            }

                            @Override
                            public void onFailure(Call<Equipo> call, Throwable t) {
                                Log.e("Upload error", t.getMessage());
                            }
                        });


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.button_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });


        // Create the AlertDialog object and return it

        return builder.create();
    }

}
