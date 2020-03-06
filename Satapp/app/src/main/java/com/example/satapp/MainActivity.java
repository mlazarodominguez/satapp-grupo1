package com.example.satapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.util.Log;

import com.example.satapp.common.Constantes;
import com.example.satapp.viewmodel.EquipoEditViewModel;
import com.example.satapp.viewmodel.EquipoViewModel;
import com.example.satapp.viewmodel.UsuarioViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    EquipoViewModel equipoViewModel;
    UsuarioViewModel usuarioViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_equipo, R.id.navigation_notifications,
                R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        equipoViewModel = new ViewModelProvider(this)
                .get(EquipoViewModel.class);
        usuarioViewModel = new ViewModelProvider(this)
                .get(UsuarioViewModel.class);

        equipoViewModel.getIdEquipo().observe(MainActivity.this, new Observer<String>() {
            @Override
            public void onChanged(final String equipoId) {
                if (equipoId != null) {
                    FloatingActionButton fab = findViewById(R.id.floatingActionButton);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new IntentIntegrator(MainActivity.this).initiateScan();
                            Intent i = new Intent(MainActivity.this, TicketsEquipoActivity.class);
                            i.putExtra(Constantes.EXTRA_ID_INVENTARIABLE, equipoId);
                            startActivity(i);
                        }
                    });

                }
            }
        });

        usuarioViewModel.getUsuarioId().observe(MainActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    Intent i = new Intent(MainActivity.this, DetalleUsuarioAdminActivity.class);
                    i.putExtra("idUser", s);

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.e("Scan*******", "Cancelled scan");
            } else {
                Intent i = new Intent(MainActivity.this, EquipoDetailActivity.class);
                i.putExtra(Constantes.EXTRA_ID_INVENTARIABLE, result.getContents());
                startActivity(i);
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    }

