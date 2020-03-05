package com.example.satapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.satapp.common.MyApp;
import com.example.satapp.models.User;
import com.example.satapp.models.UtilToken;
import com.example.satapp.retrofit.IUsuarioService;
import com.example.satapp.retrofit.ServiceGenerator;
import com.example.satapp.viewmodel.UsuarioViewModel;


import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.satapp.common.MyApp.getContext;

public class DetalleUsuarioAdminActivity extends AppCompatActivity {
    private UsuarioViewModel usuarioViewModel;
    public String id;
    public IUsuarioService service;
    public ServiceGenerator serviceGenerator;
    public TextView tvnombre, tvEmail, tvCreatedAt, tvUpdateAt, tvRole;
    public ImageView ivFoto, ivEmail, ivRol;
    public ProgressBar pbLoading;
    public Button btnEditarFoto,btnPromocionar;
    Uri uriSelected;
    private static final int READ_REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_perfil);
        usuarioViewModel = new ViewModelProvider(this)
                .get(UsuarioViewModel.class);
        service = serviceGenerator.createService(IUsuarioService.class);

        ivFoto = findViewById(R.id.imageViewFotoPerfil);
        tvnombre = findViewById(R.id.textInputNombre);
        tvEmail = findViewById(R.id.textViewEmail);
        tvCreatedAt = findViewById(R.id.textViewCreatedAt);
        tvUpdateAt = findViewById(R.id.textViewUpdateAt);
        tvRole = findViewById(R.id.textViewRole);
        pbLoading = findViewById(R.id.progressBarLoading);
        ivEmail = findViewById(R.id.imageViewEmail);
        ivRol = findViewById(R.id.imageViewRol);
        btnEditarFoto = findViewById(R.id.btnEditarFoto);
        btnPromocionar = findViewById(R.id.btnPromocionar);
        loadData();
        btnEditarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performFileSearch();



            }
        });

        btnPromocionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioViewModel.upgradeTecnico(id).observe(DetalleUsuarioAdminActivity.this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        usuarioViewModel.getUser(id).observe(DetalleUsuarioAdminActivity.this, new Observer<User>() {
                            @Override
                            public void onChanged(User user) {
                                pbLoading.setVisibility(View.GONE);
                                LocalDate createdAt = ConvertToDate(user.getCreatedAt());
                                LocalDate updateAt = ConvertToDate(user.getUpdatedAt());
                                DateTimeFormatter fmt = DateTimeFormat.forPattern("d MMMM, yyyy");
                                if(user.getRole().equalsIgnoreCase("user")){
                                    btnPromocionar.setVisibility(View.VISIBLE);
                                }
                                tvnombre.setText(user.getName().toUpperCase());
                                tvRole.setText(user.getRole().toUpperCase());
                                tvEmail.setText(user.getEmail());
                                tvCreatedAt.setText("Cuenta creada el: " + createdAt.toString(fmt));
                                tvUpdateAt.setText("Última actualización : " + updateAt.toString(fmt));
                                if (user.getPicture() != null) {
                                    Call<ResponseBody> call = service.getAvatarUser(id, UtilToken.getToken(getContext()));
                                    call.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            Bitmap fotoBitMpa = BitmapFactory.decodeStream(response.body().byteStream());
                                            Log.e("bitmap", fotoBitMpa.toString());
                                            Glide.with(getContext())
                                                    .load(fotoBitMpa)
                                                    .circleCrop()
                                                    .into(ivFoto);
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                                        }
                                    });
                                } else {
                                    Glide.with(getContext())
                                            .load("https://d500.epimg.net/cincodias/imagenes/2016/07/04/lifestyle/1467646262_522853_1467646344_noticia_normal.jpg")
                                            .circleCrop()
                                            .into(ivFoto);
                                }

                                ivFoto.setVisibility(View.VISIBLE);
                                tvnombre.setVisibility(View.VISIBLE);
                                tvEmail.setVisibility(View.VISIBLE);
                                tvCreatedAt.setVisibility(View.VISIBLE);
                                tvUpdateAt.setVisibility(View.VISIBLE);
                                tvRole.setVisibility(View.VISIBLE);
                                ivEmail.setVisibility(View.VISIBLE);
                                ivRol.setVisibility(View.VISIBLE);
                            }
                        });

                    }
                });
            }
        });
    }

    public void loadData() {
        pbLoading.setVisibility(View.VISIBLE);
        ivFoto.setVisibility(View.GONE);
        tvnombre.setVisibility(View.GONE);
        tvEmail.setVisibility(View.GONE);
        tvCreatedAt.setVisibility(View.GONE);
        tvUpdateAt.setVisibility(View.GONE);
        tvRole.setVisibility(View.GONE);
        ivEmail.setVisibility(View.GONE);
        ivRol.setVisibility(View.GONE);
        Bundle extras = getIntent().getExtras();
        id = extras.getString("idUser");

        usuarioViewModel.getUser(id).observe(DetalleUsuarioAdminActivity.this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                pbLoading.setVisibility(View.GONE);
                LocalDate createdAt = ConvertToDate(user.getCreatedAt());
                LocalDate updateAt = ConvertToDate(user.getUpdatedAt());
                DateTimeFormatter fmt = DateTimeFormat.forPattern("d MMMM, yyyy");
                if(user.getRole().equalsIgnoreCase("user")){
                    btnPromocionar.setVisibility(View.VISIBLE);
                }
                tvnombre.setText(user.getName().toUpperCase());
                tvRole.setText(user.getRole().toUpperCase());
                tvEmail.setText(user.getEmail());
                tvCreatedAt.setText("Cuenta creada el: " + createdAt.toString(fmt));
                tvUpdateAt.setText("Última actualización : " + updateAt.toString(fmt));
                if (user.getPicture() != null) {
                    Call<ResponseBody> call = service.getAvatarUser(id, UtilToken.getToken(getContext()));
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Bitmap fotoBitMpa = BitmapFactory.decodeStream(response.body().byteStream());
                            Log.e("bitmap", fotoBitMpa.toString());
                            Glide.with(getContext())
                                    .load(fotoBitMpa)
                                    .circleCrop()
                                    .into(ivFoto);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                } else {
                    Glide.with(getContext())
                            .load("https://d500.epimg.net/cincodias/imagenes/2016/07/04/lifestyle/1467646262_522853_1467646344_noticia_normal.jpg")
                            .circleCrop()
                            .into(ivFoto);
                }

                ivFoto.setVisibility(View.VISIBLE);
                tvnombre.setVisibility(View.VISIBLE);
                tvEmail.setVisibility(View.VISIBLE);
                tvCreatedAt.setVisibility(View.VISIBLE);
                tvUpdateAt.setVisibility(View.VISIBLE);
                tvRole.setVisibility(View.VISIBLE);
                ivEmail.setVisibility(View.VISIBLE);
                ivRol.setVisibility(View.VISIBLE);
            }
        });


    }


    public void performFileSearch() {


        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);


        intent.addCategory(Intent.CATEGORY_OPENABLE);


        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            Uri uri = null;
            if (resultData != null) {

                uri = resultData.getData();
                Log.i("Filechooser URI", "Uri: " + uri);
                //showImage(uri);

                uriSelected = uri;
                if (uriSelected != null) {
                    try {
                        InputStream inputStream = getContext().getContentResolver().openInputStream(uriSelected);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                        int cantBytes;
                        byte[] buffer = new byte[1024 * 4];

                        while ((cantBytes = bufferedInputStream.read(buffer, 0, 1024 * 4)) != -1) {
                            baos.write(buffer, 0, cantBytes);
                        }

                        String d = getContext().getContentResolver().getType(uriSelected);
                        RequestBody requestFile =
                                RequestBody.create(
                                        baos.toByteArray(), MediaType.parse(d));

                        String ext = requestFile.contentType().toString();
                        String[] parts = ext.split("/");

                        MultipartBody.Part body =
                                MultipartBody.Part.createFormData("avatar", "avatar." + parts[1].trim(), requestFile);


                        Call<User> call = service.actualizarImagen(id, UtilToken.getToken(MyApp.getContext()), body);
                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                Call<ResponseBody> call2 = service.getAvatarUser(id, UtilToken.getToken(getContext()));
                                call2.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        Bitmap fotoBitMpa = BitmapFactory.decodeStream(response.body().byteStream());
                                        Log.e("bitmap",fotoBitMpa.toString());
                                        Glide.with(MyApp.getContext())
                                                .load(fotoBitMpa)
                                                .circleCrop()
                                                .into(ivFoto);
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                                    }
                                });

                            }
                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private LocalDate ConvertToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        LocalDate convertedDate = new LocalDate();
        try {
            convertedDate = LocalDate.fromDateFields(dateFormat.parse(dateString));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }
}







