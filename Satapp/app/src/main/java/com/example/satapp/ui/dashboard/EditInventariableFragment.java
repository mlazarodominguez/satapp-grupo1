package com.example.satapp.ui.dashboard;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.satapp.MainActivity;
import com.example.satapp.R;
import com.example.satapp.common.Constantes;
import com.example.satapp.models.Equipo;
import com.example.satapp.models.UtilToken;
import com.example.satapp.retrofit.IEquipoService;
import com.example.satapp.retrofit.ServiceGenerator;
import com.example.satapp.viewmodel.EquipoEditViewModel;
import com.example.satapp.viewmodel.EquipoViewModel;
import com.example.satapp.viewmodel.UbicacionViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditInventariableFragment extends Fragment {

    public TextInputLayout tilCod, tilTipo, tilUbi, tilNombre, tilDescription;
    public EquipoEditViewModel equipoEditViewModel;
    public EquipoViewModel equipoViewModel;
    public UbicacionViewModel ubicacionViewModel;
    public Bundle bundle;
    public String jwt, idUse;
    public ProgressBar progressBarData;
    public Button btnSend;
    public AppCompatEditText appCompatEditTextCod, appCompatEditTextTipo;
    public EditText etNombre, etDescription, etUbi;
    public Spinner miSpinner;
    public ImageView ivFoto;
    public Button btnCambiarImagen;
    public String ubiacion;
    Uri uriSelected;
    MultipartBody.Part body;
    private static final int READ_REQUEST_CODE = 23;


    public EditInventariableFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getActivity().getIntent().getExtras();
        jwt = UtilToken.getToken(getContext());
        idUse = bundle.getString(Constantes.EXTRA_ID_INVENTARIABLE);
        equipoViewModel = new ViewModelProvider(getActivity()).get(EquipoViewModel.class);
        equipoEditViewModel = new ViewModelProvider(getActivity()).get(EquipoEditViewModel.class);
        ubicacionViewModel = new ViewModelProvider(getActivity()).get(UbicacionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_inventariable, container, false);
        appCompatEditTextCod = view.findViewById(R.id.hintTextCod);
        appCompatEditTextTipo = view.findViewById(R.id.hintTextTipo);

        tilCod = view.findViewById(R.id.textInputLayoutCod);
        tilTipo = view.findViewById(R.id.textInputLayoutTipo);
        tilUbi = view.findViewById(R.id.textInputLayoutUbi);

        btnCambiarImagen = view.findViewById(R.id.buttonEditarFotoEquipo);
        miSpinner = view.findViewById(R.id.miSpinner);
        ivFoto = view.findViewById(R.id.imageViewFotoEquipo);

        progressBarData = view.findViewById(R.id.progressBarLoadEditEquipo);
        btnSend = view.findViewById(R.id.buttonSendEditEquipo);
        etNombre = view.findViewById(R.id.editTextNombre);
        etDescription = view.findViewById(R.id.editTextDescription);

        progressBarData.setVisibility(View.VISIBLE);
        etNombre.setVisibility(View.GONE);
        etDescription.setVisibility(View.GONE);
        appCompatEditTextCod.setVisibility(View.GONE);
        appCompatEditTextTipo.setVisibility(View.GONE);
        btnSend.setVisibility(View.GONE);
        ivFoto.setVisibility(View.GONE);
        btnCambiarImagen.setVisibility(View.GONE);
        miSpinner.setVisibility(View.GONE);
        loadData();
        return view;
    }

    public void loadData(){

        equipoEditViewModel.getEquipo(idUse,jwt).observe(getActivity(),new Observer<Equipo>() {
            @Override
            public void onChanged(final Equipo equipo) {


                appCompatEditTextCod.setText(equipo.getCodigo());
                appCompatEditTextTipo.setText(equipo.getTipo());
                appCompatEditTextCod.setEnabled(false);
                appCompatEditTextTipo.setEnabled(false);
                etNombre.setText(equipo.getNombre());
                etDescription.setText(equipo.getDescripcion());

                equipoViewModel.getImagenEquipo(idUse, jwt).observe(getActivity(), new Observer<Bitmap>() {
                    @Override
                    public void onChanged(Bitmap bitmap) {
                        Glide.with(getActivity())
                                .load(bitmap)
                                .centerCrop()
                                .into(ivFoto);
                        ivFoto.setVisibility(View.VISIBLE);
                        btnCambiarImagen.setVisibility(View.VISIBLE);
                        etDescription.setVisibility(View.VISIBLE);
                        appCompatEditTextCod.setVisibility(View.VISIBLE);
                        appCompatEditTextTipo.setVisibility(View.VISIBLE);
                        btnSend.setVisibility(View.VISIBLE);
                        etNombre.setVisibility(View.VISIBLE);
                        miSpinner.setVisibility(View.VISIBLE);
                        progressBarData.setVisibility(View.GONE);
                        btnCambiarImagen.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                performFileSearch();
                            }
                        });
                    }
                });

                    ubicacionViewModel.getListUbicaciones().observe(getActivity(), new Observer<List<String>>() {
                        @Override
                        public void onChanged(List<String> strings) {
                            ArrayAdapter listaUbicaciones = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, strings);
                            miSpinner.setAdapter(listaUbicaciones);
                            miSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    final String ubi = miSpinner.getAdapter().getItem(position).toString();
                                    btnSend.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Equipo equipoEditado = new Equipo(etNombre.getText().toString(),
                                                    etDescription.getText().toString(),
                                                    ubi);
                                            equipoEditViewModel.updateEquipo(idUse, jwt, equipoEditado);
                                            imgTrans();
                                            Intent i = new Intent(getActivity(), MainActivity.class);
                                            startActivity(i);
                                            getActivity().finish();
                                        }
                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    });
            }
        });

    }


    public void imgTrans(){
        if (uriSelected != null) {
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
                body = MultipartBody.Part.createFormData("imagen", "imagen", requestFile);
                equipoEditViewModel.editImgEquipo(idUse,jwt,body);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    @Override
    public void onResume() {
        super.onResume();
    }

}
