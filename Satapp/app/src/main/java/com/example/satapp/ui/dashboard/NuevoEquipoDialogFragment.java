package com.example.satapp.ui.dashboard;



import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.satapp.R;
import com.example.satapp.models.Equipo;
import com.example.satapp.models.UtilToken;
import com.example.satapp.retrofit.IEquipoService;
import com.example.satapp.retrofit.ServiceGenerator;
import com.example.satapp.viewmodel.EquipoViewModel;


import org.joda.time.LocalDate;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    String tipoSeleccionado;
    View v;
    public EditText ubicacion, descripcion, nombre;
    public LocalDate now;
    UtilToken utilToken;
    Spinner spinner;
    ArrayList<String> arrayTipos= new ArrayList<String>();
    ArrayAdapter<String> adapterTipos ;
    EquipoViewModel viewModel;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Configura el dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Configuración del diálogo

        builder.setTitle("Nuevo equipo");


        builder.setCancelable(true);

        v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_nuevo_equipo_dialog, null);
        builder.setView(v);

        ubicacion= v.findViewById(R.id.editTextUbicacionEquipo);
        descripcion = v.findViewById(R.id.editTextDescripcionEquipo);
        nombre= v.findViewById(R.id.editTextNombreEquipo);
        imageView= v.findViewById(R.id.imageViewNuevoEquipo);
        spinner = v.findViewById(R.id.spinner);

        viewModel= new ViewModelProvider(getActivity()).get(EquipoViewModel.class);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });



        loadTipos();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tipoSeleccionado = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        builder.setPositiveButton(R.string.button_guardar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (uriSelected != null) {


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

                        RequestBody name = RequestBody.create(MultipartBody.FORM, nombre.getText().toString());
                        RequestBody type = RequestBody.create(MultipartBody.FORM, tipoSeleccionado);
                        RequestBody description = RequestBody.create(MultipartBody.FORM, descripcion.getText().toString());
                        RequestBody ubication = RequestBody.create(MultipartBody.FORM, ubicacion.getText().toString());


                        Call<Equipo> callNuevoEquipo = service.nuevoEquipo( body, name, type, description, ubication,utilToken.getToken(getContext()));

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

    public void performFileSearch() {


        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);


        intent.addCategory(Intent.CATEGORY_OPENABLE);


        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }


    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {


        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                uriSelected= uri;
                Log.i("Filechooser URI", "Uri: " + uri.toString());
            }
        }
    }

    public void loadTipos() {
        viewModel.getAllTipos(utilToken.getToken(getContext())).observe(getActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> tipos) {

                for (int i = 0; i < tipos.size(); i++) {
                    arrayTipos.add(tipos.get(i));
                }

                adapterTipos = new ArrayAdapter<String>(NuevoEquipoDialogFragment.this.getActivity(),
                        android.R.layout.simple_spinner_item, arrayTipos);
                adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapterTipos);
            }
        });
    }

}
