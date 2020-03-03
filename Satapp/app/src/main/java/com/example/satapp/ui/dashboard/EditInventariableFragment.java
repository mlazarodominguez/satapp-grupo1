package com.example.satapp.ui.dashboard;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.satapp.R;
import com.example.satapp.viewmodel.EquipoViewModel;
import com.google.android.gms.plus.PlusOneButton;

public class EditInventariableFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    TextView tvNombre, tvDescripcion;
    EquipoViewModel equipoViewModel;


    public EditInventariableFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        equipoViewModel = new ViewModelProvider(getActivity()).get(EquipoViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_inventariable, container, false);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
