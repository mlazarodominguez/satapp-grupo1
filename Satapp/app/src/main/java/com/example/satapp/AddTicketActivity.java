package com.example.satapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.satapp.common.Constantes;
import com.example.satapp.retrofit.AddTicketService;
import com.example.satapp.retrofit.ServiceGenerator;
import com.example.satapp.viewmodel.AddTicketViewModel;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddTicketActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    EditText etTitulo, etDescripcion;
    Button btnAddTicket;
    private static final int READ_REQUEST_CODE = 42;
    ImageView ivFoto;
    Uri uriSelected;
    Button btnUpload;
    String fileName;
    int nameIndex;
    AddTicketViewModel addTicketViewModel;

    AddTicketService service;
    ServiceGenerator serviceGenerator;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);

        retrofitInit();
        findViews();
        events();
    }

    private void retrofitInit() {
        addTicketViewModel = new ViewModelProvider(this).get(AddTicketViewModel.class);
        extras = getIntent().getExtras();
    }

    private void findViews() {
        etTitulo = findViewById(R.id.editTextAddTitulo);
        etDescripcion = findViewById(R.id.editTextAddDescripcion);
        btnAddTicket = findViewById(R.id.buttonAddTicket);
        btnUpload = findViewById(R.id.buttonFichero);
        ivFoto = findViewById(R.id.imageViewAddFoto);

        uriSelected = null;
        ivFoto.setVisibility(View.INVISIBLE);
    }

    private void events() {
        btnAddTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uriSelected != null) {
                    service = serviceGenerator.createService(AddTicketService.class);
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uriSelected);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                        int cantBytes;
                        byte[] buffer = new byte[1024*4];

                        while ((cantBytes = bufferedInputStream.read(buffer,0,1024*4)) != -1) {
                            baos.write(buffer,0,cantBytes);
                        }

                        RequestBody requestFile =
                                RequestBody.create(
                                        MediaType.parse(getContentResolver().getType(uriSelected)), baos.toByteArray());

                        Cursor cursor = getContentResolver().query(uriSelected, null, null, null, null);
                        if( cursor != null && cursor.moveToFirst() ){
                            nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                            fileName = cursor.getString(nameIndex);
                            cursor.close();
                        }

                        List<MultipartBody.Part> body =
                                Collections.singletonList(MultipartBody.Part.createFormData("fotos", fileName, requestFile));

                        RequestBody titulo = RequestBody.create(MultipartBody.FORM, etTitulo.getText().toString());
                        RequestBody descripcion = RequestBody.create(MultipartBody.FORM, etDescripcion.getText().toString());
                        RequestBody id = RequestBody.create(MultipartBody.FORM, extras.getString(Constantes.EXTRA_ID_INVENTARIABLE));

                        if(etTitulo.getText().toString().isEmpty())
                            etTitulo.setError("El titulo no puede estar vacío");
                        else if(etDescripcion.getText().toString().isEmpty())
                            etDescripcion.setError("La descripción no puede estar vacía");
                        else {
                            addTicketViewModel.addTicket(titulo, descripcion, id, body);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(AddTicketActivity.this, TicketsEquipoActivity.class);
                startActivity(intent);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });
    }

    private void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && resultData != null) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Glide
                        .with(this)
                        .load(uri)
                        .into(ivFoto);
                uriSelected = uri;
                ivFoto.setVisibility(View.VISIBLE);
            }

        }
    }

}
