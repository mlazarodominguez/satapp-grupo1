package com.example.satapp.ui.dashboard;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.satapp.MainActivity;
import com.example.satapp.R;
import com.example.satapp.common.Constantes;
import com.example.satapp.models.Equipo;
import com.example.satapp.models.UtilToken;
import com.example.satapp.viewmodel.EquipoEditViewModel;
import com.example.satapp.viewmodel.EquipoViewModel;
import com.google.android.material.textfield.TextInputLayout;

import okhttp3.ResponseBody;

public class EditInventariableFragment extends Fragment {

    public TextInputLayout tilCod, tilTipo, tilUbi, tilNombre, tilDescription;
    public EquipoEditViewModel equipoEditViewModel;
    public EquipoViewModel equipoViewModel;
    public Bundle bundle;
    public String jwt, idUse;
    public ProgressBar progressBar;
    public Button btnSend;
    public AppCompatEditText appCompatEditTextCod, appCompatEditTextTipo,appCompatEditTextUbi;
    public EditText etNombre, etDescription, etUbi;
    public Spinner miSpinner;
    public ImageView ivFoto;
    public Button btnCambiarImagen;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_inventariable, container, false);
        appCompatEditTextCod = view.findViewById(R.id.hintTextCod);
        appCompatEditTextTipo = view.findViewById(R.id.hintTextTipo);
        appCompatEditTextUbi = view.findViewById(R.id.editTextUbi);

        tilCod = view.findViewById(R.id.textInputLayoutCod);
        tilTipo = view.findViewById(R.id.textInputLayoutTipo);
        tilUbi = view.findViewById(R.id.textInputLayoutUbi);

        btnCambiarImagen = view.findViewById(R.id.buttonEditarFotoEquipo);
        miSpinner = view.findViewById(R.id.miSpinner);
        ivFoto = view.findViewById(R.id.imageViewFotoEquipo);

        progressBar = view.findViewById(R.id.progressBarLoadEditEquipo);
        btnSend = view.findViewById(R.id.buttonSendEditEquipo);
        etNombre = view.findViewById(R.id.editTextNombre);
        etDescription = view.findViewById(R.id.editTextDescription);

        progressBar.setVisibility(View.VISIBLE);
        etNombre.setVisibility(View.GONE);
        etDescription.setVisibility(View.GONE);
        appCompatEditTextCod.setVisibility(View.GONE);
        appCompatEditTextTipo.setVisibility(View.GONE);
        btnSend.setVisibility(View.GONE);
        appCompatEditTextUbi.setVisibility(View.GONE);
        ivFoto.setVisibility(View.GONE);
        btnCambiarImagen.setVisibility(View.GONE);

        loadData();

        return view;
    }

    public void loadData(){

        equipoEditViewModel.getEquipo(idUse,jwt).observe(getActivity(),new Observer<Equipo>() {
            @Override
            public void onChanged(final Equipo equipo) {

                progressBar.setVisibility(View.GONE);
                etDescription.setVisibility(View.VISIBLE);
                appCompatEditTextCod.setVisibility(View.VISIBLE);
                appCompatEditTextTipo.setVisibility(View.VISIBLE);
                btnSend.setVisibility(View.VISIBLE);
                etNombre.setVisibility(View.VISIBLE);
                appCompatEditTextUbi.setVisibility(View.VISIBLE);
                appCompatEditTextCod.setText(equipo.getCodigo());
                appCompatEditTextTipo.setText(equipo.getTipo());
                appCompatEditTextUbi.setText(equipo.getUbicacion());
                appCompatEditTextCod.setEnabled(false);
                appCompatEditTextTipo.setEnabled(false);
                appCompatEditTextUbi.setEnabled(false);

                etNombre.setText(equipo.getNombre());
                etDescription.setText(equipo.getDescripcion());

                equipoViewModel.getImagenEquipo(idUse,jwt).observe(getActivity(), new Observer<Bitmap>() {
                    @Override
                    public void onChanged(Bitmap bitmap) {
                        Glide.with(getActivity())
                                .load(bitmap)
                                .centerCrop()
                                .into(ivFoto);
                        ivFoto.setVisibility(View.VISIBLE);
                        btnCambiarImagen.setVisibility(View.VISIBLE);
                    }
                });

                /*ArrayAdapter listaUbicaciones = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line,ds);
                miSpinner.setAdapter(listaTemporadas);
                miSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            }

                        }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/




                btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Equipo equipoEditado = new Equipo(etNombre.getText().toString(),
                                etDescription.getText().toString(),
                                appCompatEditTextUbi.getText().toString());

                        equipoEditViewModel.updateEquipo(idUse,jwt,equipoEditado);
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                });
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
