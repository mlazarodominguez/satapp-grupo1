package com.example.satapp.ui.dashboard;



import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.satapp.R;


public class NuevoEquipoDialogFragment extends DialogFragment {

    View v;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Configura el dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Configuración del diálogo

        builder.setTitle("Nuevo equipo");


        builder.setCancelable(true);

        v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_nuevo_equipo_dialog, null);
        builder.setView(v);





        builder.setPositiveButton(R.string.button_guardar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



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

}
