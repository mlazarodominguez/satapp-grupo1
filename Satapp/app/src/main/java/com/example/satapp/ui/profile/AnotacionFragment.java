package com.example.satapp.ui.profile;

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
import com.example.satapp.models.UtilToken;
import com.example.satapp.viewmodel.AnotacionViewModel;

import java.util.ArrayList;
import java.util.List;

public class AnotacionFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private AnotacionViewModel anotacionViewModel;
    MyAnotacionRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    private List<String> anotacionesList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AnotacionFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AnotacionFragment newInstance(int columnCount) {
        AnotacionFragment fragment = new AnotacionFragment();
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
        anotacionViewModel = new ViewModelProvider(getActivity()).get(AnotacionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anotacion_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            anotacionesList = new ArrayList<>();

            adapter = new MyAnotacionRecyclerViewAdapter(
                    getActivity(), anotacionesList, anotacionViewModel
            );
            recyclerView.setAdapter(adapter);

            loadAnotaciones();
        }
        return view;
    }

    private void loadAnotaciones() {
        if (anotacionViewModel.getListAnotaciones(UtilToken.getToken(getActivity())) == null)
            Toast.makeText(getActivity(), "No hay anotaciones creados", Toast.LENGTH_SHORT).show();
        else {
            anotacionViewModel.getListAnotaciones(UtilToken.getToken(getActivity())).observe(getActivity(), new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> strings) {
                    anotacionesList = strings;
                    adapter.setData(anotacionesList);
                }
            });
        }
    }

}
