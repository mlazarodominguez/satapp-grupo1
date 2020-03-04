package com.example.satapp.ui.dashboard;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.satapp.MainActivity;
import com.example.satapp.R;
import com.example.satapp.common.Constantes;
import com.example.satapp.models.Equipo;
import com.example.satapp.models.UtilToken;
import com.example.satapp.viewmodel.EquipoEditViewModel;
import com.example.satapp.viewmodel.EquipoViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class EditInventariableFragment extends Fragment {

    public TextInputLayout tilCod, tilTipo, tilUbi, tilNombre, tilDescription;
    public EquipoEditViewModel equipoEditViewModel;
    public Bundle bundle;
    public String jwt, idUse;
    public ProgressBar progressBar;
    public Button btnSend;
    public AppCompatEditText appCompatEditTextCod, appCompatEditTextTipo,appCompatEditTextUbi;
    public EditText etNombre, etDescription, etUbi;

    public EditInventariableFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        bundle = getActivity().getIntent().getExtras();
        jwt = UtilToken.getToken(getContext());
        idUse = bundle.getString(Constantes.EXTRA_ID_INVENTARIABLE);
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
                appCompatEditTextUbi.setText("AULA-07");
                appCompatEditTextCod.setEnabled(false);
                appCompatEditTextTipo.setEnabled(false);
                appCompatEditTextUbi.setEnabled(false);

                etNombre.setText(equipo.getNombre());
                etDescription.setText(equipo.getDescripcion());


                btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Equipo equipoo = new Equipo(etNombre.getText().toString(),etDescription.getText().toString(),appCompatEditTextUbi.getText().toString());
                        equipoEditViewModel.updateEquipo(idUse,jwt,equipoo);
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
