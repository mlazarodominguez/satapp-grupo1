package com.example.satapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.satapp.common.Constantes;
import com.example.satapp.common.MyApp;
import com.example.satapp.models.LoginReponse;
import com.example.satapp.models.UserLogin;
import com.example.satapp.models.UtilToken;
import com.example.satapp.retrofit.IUsuarioService;
import com.example.satapp.retrofit.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    IUsuarioService service;
    Button btnLogin,btnRegister;
    EditText etEmail,etPassword;
    String emailLog, passwordLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailLog = etEmail.getText().toString();
                passwordLog = etPassword.getText().toString();
                String base = emailLog + ":"+ passwordLog;
                String authHeader="Basic " + Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);
                service = ServiceGenerator.createService(IUsuarioService.class);

                Call<LoginReponse> call = service.login(authHeader, Constantes.MASTER_KEY);

                call.enqueue(new Callback<LoginReponse>() {
                    @Override
                    public void onResponse(Call<LoginReponse> call, Response<LoginReponse> response) {
                        if(response.code()==201){
                            Log.e("email", emailLog);
                            Log.e("password", passwordLog);
                            Log.e("token",response.body().getToken());
                            UtilToken.setToken(LoginActivity.this, response.body().getToken());
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this,"Usuario o contraseña incorrectos",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginReponse> call, Throwable t) {

                    }
                });

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}
