package com.example.satapp.ui.notifications;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.satapp.R;
import com.example.satapp.viewmodel.UbicacionViewModel;

import java.util.ArrayList;
import java.util.List;

public class UbicacionFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private UbicacionViewModel ubicacionViewModel;
    MyUbicacionRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    private List<String> ubicacionesList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UbicacionFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static UbicacionFragment newInstance(int columnCount) {
        UbicacionFragment fragment = new UbicacionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        ubicacionViewModel = new ViewModelProvider(getActivity()).get(UbicacionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ubicacion_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            ubicacionesList = new ArrayList<>();

            adapter = new MyUbicacionRecyclerViewAdapter(
                    getActivity(), ubicacionesList, ubicacionViewModel
            );
            recyclerView.setAdapter(adapter);

            loadUbicaciones();
        }
        return view;
    }

    private void loadUbicaciones() {
        if (ubicacionViewModel.getListUbicaciones() == null)
            Toast.makeText(getActivity(), "No hay ubicaciones creados", Toast.LENGTH_SHORT).show();
        else {
            ubicacionViewModel.getListUbicaciones().observe(getActivity(), new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> strings) {
                    ubicacionesList = strings;
                    adapter.setData(ubicacionesList);
                }
            });
        }
    }

}
