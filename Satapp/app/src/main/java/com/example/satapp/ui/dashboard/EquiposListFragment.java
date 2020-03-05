package com.example.satapp.ui.dashboard;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.satapp.R;
import com.example.satapp.models.Equipo;
import com.example.satapp.models.UtilToken;
import com.example.satapp.viewmodel.EquipoViewModel;

import java.util.List;


public class EquiposListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private MyEquiposListRecyclerViewAdapter adapter;
    private List<Equipo> equipoList;
    private Context context;
    private RecyclerView recyclerView;
    EquipoViewModel equipoViewModel;
    UtilToken utilToken;

    public EquiposListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EquiposListFragment newInstance(int columnCount) {
        EquiposListFragment fragment = new EquiposListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // ATENCIÓN ESTE FRAGMENT TIENE UN MENÚ ADICIONAL DE OPCIONES
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.nuevo_equipo, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nuevoEquipo:
                DialogFragment dialog = new NuevoEquipoDialogFragment();
                dialog.show(getFragmentManager(),"NuevoEquipoDialogFragment");

                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        equipoViewModel = new ViewModelProvider(getActivity()).get(EquipoViewModel.class);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equiposlist_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }
        adapter = new MyEquiposListRecyclerViewAdapter(equipoList,context,R.layout.fragment_equiposlist,equipoViewModel);
        recyclerView.setAdapter(adapter);
        return view;
    }




    @Override
    public void onResume() {
        super.onResume();
        loadEquipoData();
    }

    private void loadEquipoData(){

        equipoViewModel.getEquipos(utilToken.getToken(context)).observe(getActivity(), new Observer<List<Equipo>>(){

            @Override
            public void onChanged(List<Equipo> equipos) {
                equipoList = equipos;
                adapter.setData(equipoList);
            }

        });
    }
}
