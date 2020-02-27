package com.example.satapp.ui.dashboard;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.satapp.R;
import com.example.satapp.models.Equipo;
import com.example.satapp.ui.dashboard.dummy.DummyContent;
import com.example.satapp.ui.dashboard.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class EquiposListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private MyEquiposListRecyclerViewAdapter adapter;
    private List<Equipo> equipoList;
    private Context context;
    private RecyclerView recyclerView;
    EquipoViewModel equipoViewModel;

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
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
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
        equipoViewModel.getSeries().observe(getActivity(), new Observer<List<Equipo>>(){

            @Override
            public void onChanged(List<Equipo> equipos) {
                equipoList = equipos;
                adapter.setData(equipoList);
            }

        });
    }
}
