package com.example.satapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.satapp.common.Constantes;
import com.example.satapp.models.Equipo;
import com.example.satapp.models.UtilToken;
import com.example.satapp.retrofit.IEquipoService;
import com.example.satapp.retrofit.ServiceGenerator;
import com.example.satapp.viewmodel.EquipoEditViewModel;
import com.example.satapp.viewmodel.EquipoViewModel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import lombok.SneakyThrows;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquipoDetailActivity extends AppCompatActivity {

    EquipoEditViewModel equipoViewModel;
    TextView tvUbicacion, tvTipo, tvTitulo, tvTickets;
    Button btnCompartir;
    ImageView ivFoto;
    Bundle extras;
    Equipo e;
    UtilToken utilToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_detail);

        retrofitInit();
        findViews();
        events();
    }

    private void retrofitInit() {
        equipoViewModel = new ViewModelProvider(this).get(EquipoEditViewModel.class);
        extras = getIntent().getExtras();
    }

    private void findViews() {
        tvUbicacion = findViewById(R.id.textViewUbicacionEquipoDetail);
        tvTickets = findViewById(R.id.textViewTicketsDetail);
        tvTipo = findViewById(R.id.textViewTipoEquipoDetail);
        tvTitulo = findViewById(R.id.textViewTituloEquipoDetail);
        ivFoto = findViewById(R.id.imageViewEquipoDetail);
        btnCompartir = findViewById(R.id.buttonCompartir);
    }

    private void events() {
        equipoViewModel.getEquipo(extras.getString(Constantes.EXTRA_ID_INVENTARIABLE), UtilToken.getToken(this)).observe(this, new Observer<Equipo>() {
            @Override
            public void onChanged(final Equipo equipo) {
                tvUbicacion.setText(equipo.getUbicacion());
                tvTipo.setText(equipo.getTipo());
                tvTitulo.setText(equipo.getNombre());

                IEquipoService service = ServiceGenerator.createService(IEquipoService.class);

                final Call<ResponseBody> imagenEquipo = service.getImagenEquipo(equipo.getId(),UtilToken.getToken(EquipoDetailActivity.this));

                e= equipo;

                imagenEquipo.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Bitmap fotoBitMap = BitmapFactory.decodeStream(response.body().byteStream());

                        Glide.with(EquipoDetailActivity.this)
                                .load(fotoBitMap)
                                .centerCrop()
                                .into(ivFoto);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Upload error", t.getMessage());
                    }

                });

                tvTickets.setText("Ver Tickets");

                tvTickets.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(EquipoDetailActivity.this, TicketsEquipoActivity.class);
                        i.putExtra(Constantes.EXTRA_ID_INVENTARIABLE, equipo.getId());
                        startActivity(i);
                        finish();
                    }
                });

                btnCompartir.setOnClickListener(new View.OnClickListener() {
                    @SneakyThrows
                    @Override
                    public void onClick(View v) {
                        String path = "https://satapp-grupo1.herokuapp.com/inventariable/img/"+e.getId()+"?access_token="+utilToken.getToken(EquipoDetailActivity.this);
                        new DownloadTask().execute(path);
                    }
                });

            }
        });
    }

    private class DownloadTask extends AsyncTask<String, Void, File> {

        @Override
        protected File doInBackground(String... strings) {
            String url = strings[0];
            File result = null;
            try {
                result = downloadFile(url, EquipoDetailActivity.this);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return result;
            }
        }

        @Override
        protected void onPostExecute(File file) {

            // Obtenemos la URI a exponer a partir del fichero
            Uri myPhotoFileUri = FileProvider.getUriForFile(EquipoDetailActivity.this, EquipoDetailActivity.this.getApplicationContext().getPackageName() + ".provider", file);

            // Compartimos
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.putExtra(Intent.EXTRA_TEXT,"Equipo: "+e.getNombre()+". Aula: "+e.getUbicacion());
            shareIntent.putExtra(Intent.EXTRA_STREAM, myPhotoFileUri);
            // Esta parte sirve para obtener, a partir de la extensión del fichero, el tipo mime
            String type = null;
            String extension = MimeTypeMap.getFileExtensionFromUrl(file.getAbsolutePath());
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            }
            shareIntent.setType(type);
            startActivity(Intent.createChooser(shareIntent, "Enviar"));
        }
    }


    public File downloadFile(String url, Context ctx) throws IOException {
        final OkHttpClient client = new OkHttpClient();

        // Montamos la petición
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Ejecutamos la petición
        try (okhttp3.Response response = client.newCall(request).execute()) {
            // Si hay error, excepción
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // En otro caso...

            // Obtenemos, a partir de las cabeceras, el tipo mime, y de él, la extensión
            Headers headers = response.headers();
            String contentType = headers.get("Content-Type");
            String suffix = "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(contentType);

            // "Armamos" lo necesario para leer el fichero vía flujos
            InputStream is = response.body().byteStream();
            // Se guarda en un fichero temporal, dentro de la caché externa
            File file = File.createTempFile("img", suffix, ctx.getExternalCacheDir());
            BufferedInputStream input = new BufferedInputStream(is);
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));

            // Bucle clásico de lectura de un fichero en grupos de 4KB
            byte[] data = new byte[4*1024];

            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }

            // Cierre de flujos
            output.flush();
            output.close();
            input.close();

            return file;

        }
    }

}
