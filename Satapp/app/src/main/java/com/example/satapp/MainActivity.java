package com.example.satapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.satapp.common.Constantes;
import com.example.satapp.viewmodel.EquipoViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    EquipoViewModel equipoViewModel;

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

        equipoViewModel.getEquipo().observe(MainActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String equipoId) {
                if(equipoId != null) {
                    Intent i = new Intent(MainActivity.this, EditInventariableActivity.class);
                    i.putExtra(Constantes.EXTRA_ID_INVENTARIABLE, equipoId);
                    startActivity(i);
                }
            }
        });

    }

}
