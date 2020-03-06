package com.example.satapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.satapp.common.Constantes;

import com.example.satapp.common.MyApp;
import com.example.satapp.models.User;
import com.example.satapp.models.UtilToken;
import com.example.satapp.retrofit.IUsuarioService;
import com.example.satapp.retrofit.ServiceGenerator;

import com.example.satapp.viewmodel.EquipoViewModel;
import com.example.satapp.viewmodel.UsuarioViewModel;
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
    UsuarioViewModel usuarioViewModel;
    String rol;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_loading);
        equipoViewModel = new ViewModelProvider(this)
                .get(EquipoViewModel.class);
        usuarioViewModel = new ViewModelProvider(this)
                .get(UsuarioViewModel.class);
        usuarioViewModel.getCurrentUser(UtilToken.getToken(MyApp.getContext())).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                rol = user.getRole();
                if(rol.equals("admin")){
                    loadAdmin();
                }else{
                    loadUser();
                }
            }
        });


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

        usuarioViewModel.getUsuarioId().observe(MainActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null){
                    Intent i = new Intent(MainActivity.this,DetalleUsuarioAdminActivity.class);
                    i.putExtra("idUser",s);
                    startActivity(i);
                }
            }
        });



    }
    public void loadAdmin(){
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfigurationAdmin = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_equipo, R.id.navigation_notifications,
                R.id.navigation_profile)
                .build();
        AppBarConfiguration appBarConfigurationUser = new AppBarConfiguration.Builder(R.id.navigation_equipo, R.id.navigation_notifications,
                R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfigurationAdmin);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void loadUser(){
        setContentView(R.layout.activity_main_user);
            BottomNavigationView navView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            AppBarConfiguration appBarConfigurationUser = new AppBarConfiguration.Builder(R.id.navigation_equipo, R.id.navigation_notifications,
                    R.id.navigation_profile)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfigurationUser);
        NavigationUI.setupWithNavController(navView, navController);
        }



}
