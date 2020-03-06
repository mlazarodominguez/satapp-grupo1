package com.example.satapp.ui.dashboard.ticketsEquipo;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.satapp.R;
import com.example.satapp.TicketsEquipoActivity;
import com.example.satapp.common.Constantes;

import com.example.satapp.viewmodel.AddTicketViewModel;
import com.example.satapp.viewmodel.EquipoViewModel;
import com.example.satapp.viewmodel.TicketsEquipoViewModel;

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


public class DialogFragmentAddTicket extends DialogFragment {

    TicketsEquipoViewModel ticketsEquipoViewModel;

    private static final int PICK_IMAGE_REQUEST = 1;
    EditText etTitulo, etDescripcion;
    ImageView ivFoto;
    Uri uriSelected;
    Button btnUpload;
    String fileName;
    int nameIndex;
    AddTicketViewModel addTicketViewModel;
    Bundle extras;
    EquipoViewModel equipoViewModel;
    String idEquipo;
    public DialogFragmentAddTicket() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_fragment_add_ticket, null);
        addTicketViewModel= new ViewModelProvider(getActivity()).get(AddTicketViewModel.class);

        TicketsEquipoActivity activity = (TicketsEquipoActivity) getActivity();
        final String idEquipo = activity.sendId();

        etTitulo = v.findViewById(R.id.editTextAddTitulo);
        etDescripcion = v.findViewById(R.id.editTextAddDescripcion);
        btnUpload = v.findViewById(R.id.buttonFichero);
        ivFoto = v.findViewById(R.id.imageViewAddFoto);
        uriSelected = null;
        ivFoto.setVisibility(View.INVISIBLE);

        // Configura el dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Configuración del diálogo
        builder.setTitle("Nuevo Ticket");

        builder.setCancelable(true);

        builder.setView(v);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });


        builder.setPositiveButton(R.string.button_guardar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                        if (uriSelected != null) {
                            try {
                                InputStream inputStream = getActivity().getContentResolver().openInputStream(uriSelected);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                                int cantBytes;
                                byte[] buffer = new byte[1024*4];

                                while ((cantBytes = bufferedInputStream.read(buffer,0,1024*4)) != -1) {
                                    baos.write(buffer,0,cantBytes);
                                }

                                RequestBody requestFile =
                                        RequestBody.create(
                                                MediaType.parse(getActivity().getContentResolver().getType(uriSelected)), baos.toByteArray());

                                Cursor cursor = getActivity().getContentResolver().query(uriSelected, null, null, null, null);
                                if( cursor != null && cursor.moveToFirst() ){
                                    nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                                    fileName = cursor.getString(nameIndex);
                                    cursor.close();
                                }

                                List<MultipartBody.Part> body =
                                        Collections.singletonList(MultipartBody.Part.createFormData("fotos", fileName, requestFile));
                                RequestBody titulo = RequestBody.create(etTitulo.getText().toString(),MultipartBody.FORM);
                                RequestBody descripcion = RequestBody.create(etDescripcion.getText().toString(),MultipartBody.FORM);
                                RequestBody id = RequestBody.create(idEquipo,MultipartBody.FORM);

                                addTicketViewModel.addTicket(titulo, descripcion, id, body);

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                dialog.dismiss();
                    }
        });

        builder.setNegativeButton(R.string.button_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
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

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && resultData != null) {
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
