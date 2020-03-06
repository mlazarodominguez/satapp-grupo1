package com.example.satapp;

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
import android.widget.Button;
import android.widget.Toast;

import com.example.satapp.dummy.DummyContent;
import com.example.satapp.dummy.DummyContent.DummyItem;
import com.example.satapp.models.User;
import com.example.satapp.viewmodel.UsuarioViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListaUsuarioFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private Context context;
    private RecyclerView users;
    private UsuarioViewModel userViewModel;
    private List<User> listaUsuarios, listaValidados;
    private MyListaUsuarioRecyclerViewAdapter adapterUsuarios, adapterValidados;
    private Button allUsers,allValidated;


    public ListaUsuarioFragment() {
    }

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

        userViewModel = new ViewModelProvider(getActivity()).get(UsuarioViewModel.class);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_usuario_list, container, false);
        users = view.findViewById(R.id.list);
        allUsers = view.findViewById(R.id.btnListaUser);
        allValidated = view.findViewById(R.id.btnListaNoValidados);
        context = view.getContext();

        loadUser();

        allUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUser();
            }
        });

        allValidated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadValidated();
            }
        });

        //loadValidated();

        return view;
    }

    public void loadUser(){
        listaUsuarios = new ArrayList<>();
        if (mColumnCount <= 1) {
            users.setLayoutManager(new LinearLayoutManager(context));
        } else {
            users.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        adapterUsuarios = new MyListaUsuarioRecyclerViewAdapter(
                context,
                listaUsuarios,
                userViewModel

        );
        users.setAdapter(adapterUsuarios);

        userViewModel.getUsuariosValidados().observe(getActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> authLoginUsers) {
                listaUsuarios.addAll(authLoginUsers);
                adapterUsuarios.notifyDataSetChanged();
            }
        });
    }

    public void loadValidated(){
        listaValidados = new ArrayList<>();
        if (mColumnCount <= 1) {
            users.setLayoutManager(new LinearLayoutManager(context));
        } else {
            users.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        adapterValidados = new MyListaUsuarioRecyclerViewAdapter(
                context,
                listaValidados,
                userViewModel
        );

        users.setAdapter(adapterValidados);

        userViewModel.getUsuariosNoValidados().observe(getActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> authLoginUsers) {
                listaValidados.addAll(authLoginUsers);
                adapterValidados.notifyDataSetChanged();
            }
        });
    }


}

