package com.example.satapp.ui.dashboard.ticketsEquipo;

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
import com.example.satapp.models.TicketsResponse;
import com.example.satapp.viewmodel.TicketsEquipoViewModel;

import java.util.ArrayList;
import java.util.List;

public class EquipoTicketsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private TicketsEquipoViewModel ticketsEquipoViewModel;
    MyEquipoTicketsRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    private List<TicketsResponse> ticketsList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EquipoTicketsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EquipoTicketsFragment newInstance(int columnCount) {
        EquipoTicketsFragment fragment = new EquipoTicketsFragment();
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
        ticketsEquipoViewModel = new ViewModelProvider(getActivity()).get(TicketsEquipoViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipotickets_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            ticketsList = new ArrayList<>();

            adapter = new MyEquipoTicketsRecyclerViewAdapter(
                    getActivity(), ticketsList, ticketsEquipoViewModel
            );
            recyclerView.setAdapter(adapter);

            loadTicketsTarea();
        }
        return view;
    }

    private void loadTicketsTarea() {
        if (ticketsEquipoViewModel.getListTicketsEquipo() == null)
            Toast.makeText(getActivity(), "No hay tickets creados para este equipo", Toast.LENGTH_SHORT).show();
        else {
            ticketsEquipoViewModel.getListTicketsEquipo().observe(getActivity(), new Observer<List<TicketsResponse>>() {
                @Override
                public void onChanged(List<TicketsResponse> ticketsResponses) {
                    ticketsList = ticketsResponses;
                    adapter.setData(ticketsList);
                }
            });
        }
    }

}
