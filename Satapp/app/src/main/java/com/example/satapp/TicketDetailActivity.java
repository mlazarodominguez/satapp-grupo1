package com.example.satapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.satapp.common.MyApp;
import com.example.satapp.models.Equipo;
import com.example.satapp.models.TicketResponse;
import com.example.satapp.models.UtilToken;
import com.example.satapp.retrofit.IEquipoService;
import com.example.satapp.retrofit.ServiceGenerator;
import com.example.satapp.viewmodel.EquipoEditViewModel;
import com.example.satapp.viewmodel.TicketDetailViewModel;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketDetailActivity extends AppCompatActivity {

    TicketDetailViewModel ticketDetailViewModel;
    TextView tvDescripcion, tvTitulo;
    ImageView ivFoto;
    IEquipoService service;
    Bundle extras;
    TicketResponse t;
    EquipoEditViewModel equipoViewModel;
    UtilToken utilToken;
    Equipo e;
    Button btnCompartir;
    String token;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);
        service = ServiceGenerator.createService(IEquipoService.class);
        retrofitInit();
        findViews();
        events();

    }

    private void retrofitInit() {
        ticketDetailViewModel = new ViewModelProvider(this).get(TicketDetailViewModel.class);
        extras = getIntent().getExtras();
        equipoViewModel = new ViewModelProvider(this).get(EquipoEditViewModel.class);

    }

    private void findViews() {
        tvDescripcion = findViewById(R.id.textViewDescripcionDetail);
        tvTitulo = findViewById(R.id.textViewTituloDetail);
        ivFoto = findViewById(R.id.imageViewFotoTicketDetail);
        btnCompartir = findViewById(R.id.buttonCompartir);
    }

    private void events() {


        ticketDetailViewModel.getTicketDetail(extras.getString("idTicketDetail"), UtilToken.getToken(this)).observe(this, new Observer<TicketResponse>() {
            @Override
            public void onChanged(TicketResponse ticketResponse) {
                tvDescripcion.setText(ticketResponse.getDescripcion());
                tvTitulo.setText(ticketResponse.getTitulo());

                t = ticketResponse;

                String img= ticketResponse.getFotos().get(0);
                String[] params = img.split("/");
                ticketDetailViewModel.getImagenTicket(params[params.length - 2], params[params.length - 1],UtilToken.getToken(MyApp.getContext())).observeForever(new Observer<Bitmap>() {
                    @Override
                    public void onChanged(Bitmap bitmap) {

                        Glide.with(TicketDetailActivity.this)
                                .load(bitmap)
                                .centerCrop()
                                .into(ivFoto);

                    }
                });

            }



        });




        btnCompartir.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {



                final Call<Equipo> equipo = service.getEquipoDetalles(t.getInventariable(),UtilToken.getToken(TicketDetailActivity.this));

                equipo.enqueue(new Callback<Equipo>() {
                    @Override
                    public void onResponse(Call<Equipo> call, Response<Equipo> response) {
                        e=response.body();
                    }

                    @Override
                    public void onFailure(Call<Equipo> call, Throwable t) {
                        Toast.makeText(MyApp.getContext(), "Error on the response from the Api", Toast.LENGTH_SHORT).show();
                    }
                });

                String path = "https://satapp-grupo1.herokuapp.com/ticket/img/"+t.getId()+"/0"+"?access_token="+utilToken.getToken(TicketDetailActivity.this);
                new DownloadTask().execute(path);
            }
        });

    }




    private class DownloadTask extends AsyncTask<String, Void, File> {

        @Override
        protected File doInBackground(String... strings) {
            String url = strings[0];
            File result = null;
            try {
                result = downloadFile(url, TicketDetailActivity.this);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return result;
            }
        }

        @Override
        protected void onPostExecute(File file) {

            // Obtenemos la URI a exponer a partir del fichero
            Uri myPhotoFileUri = FileProvider.getUriForFile(TicketDetailActivity.this, TicketDetailActivity.this.getApplicationContext().getPackageName() + ".provider", file);

            // Compartimos
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.putExtra(Intent.EXTRA_TEXT,"Nombre: "+e.getNombre()+". Ubicación: "+e.getUbicacion()+" Titulo incidencia: "+t.getTitulo()+". Incidencia: "+t.getDescripcion());
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
