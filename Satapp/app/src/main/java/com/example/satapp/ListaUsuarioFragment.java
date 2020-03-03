package com.example.satapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.satapp.dummy.DummyContent;
import com.example.satapp.dummy.DummyContent.DummyItem;
import com.example.satapp.models.User;
import com.example.satapp.viewmodel.UsuarioViewModel;

import java.util.List;

public class ListaUsuarioFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;
    private UsuarioViewModel usuarioViewModel;
    MyListaUsuarioRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    public ListaUsuarioFragment() {
    }


    @SuppressWarnings("unused")
    public static ListaUsuarioFragment newInstance(int columnCount) {
        ListaUsuarioFragment fragment = new ListaUsuarioFragment();
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

        usuarioViewModel = new ViewModelProvider(getActivity()).get(UsuarioViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_usuario_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyListaUsuarioRecyclerViewAdapter(
                    getActivity(),
                    null,
                    usuarioViewModel
            );
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
