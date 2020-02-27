package com.example.satapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.satapp.common.Constantes;
import com.example.satapp.models.User;
import com.example.satapp.retrofit.IUsuarioService;
import com.example.satapp.retrofit.ServiceGenerator;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.satapp.common.MyApp.getContext;

public class RegisterActivity extends AppCompatActivity {
    private static final int READ_REQUEST_CODE =1234;
    Button btnRegister,btnAvatar;
    EditText email,name,password,password2;
    Uri uriSelected;
    IUsuarioService service;
    String  password_txt, password_txt_2, fullname_txt,email_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.etEmailRegister);
        name = findViewById(R.id.etNombreRegister);
        password = findViewById(R.id.etPasswordRegister);
        password2 = findViewById(R.id.etPasswordRepeat);
        btnAvatar = findViewById(R.id.btnAvatarRegister);
        btnRegister = findViewById(R.id.btnSignUpRegister);
        uriSelected = null;
        service = ServiceGenerator.createService(IUsuarioService.class);
       btnRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               email_txt = email.getText().toString();
               fullname_txt = name.getText().toString();
               password_txt = password.getText().toString();
               password_txt_2 = password2.getText().toString();

            if(uriSelected !=null){
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


                    RequestBody email = RequestBody.create(email_txt, MultipartBody.FORM);

                    RequestBody fullname = RequestBody.create(fullname_txt, MultipartBody.FORM);
                    RequestBody password = RequestBody.create(password_txt, MultipartBody.FORM);


                    Call<User> callRegister = service.register(Constantes.MASTER_KEY, email, fullname, password,body);

                    callRegister.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {


                                Toast.makeText(RegisterActivity.this, "Usuario Creado Correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("Upload error", response.errorBody().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.e("Upload error", t.getMessage());
                        }
                    });


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{

                RequestBody email = RequestBody.create(email_txt, MultipartBody.FORM);

                RequestBody fullname = RequestBody.create(fullname_txt, MultipartBody.FORM);
                RequestBody password = RequestBody.create(password_txt, MultipartBody.FORM);


                Call<User> callRegister = service.register(Constantes.MASTER_KEY, email, fullname, password,null);

                callRegister.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {


                            Toast.makeText(RegisterActivity.this, "Usuario Creado Correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("Upload error", response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("Upload error", t.getMessage());
                    }
                });
            }

           }

       });

        btnAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performFileSearch();
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
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {

                uri = resultData.getData();
                Log.i("Filechooser URI", "Uri: " + uri);
                //showImage(uri);

                uriSelected = uri;


            }
        }
    }

}
