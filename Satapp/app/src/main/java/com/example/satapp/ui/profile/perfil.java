package com.example.satapp.ui.profile;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.satapp.R;
import com.example.satapp.common.MyApp;
import com.example.satapp.models.User;
import com.example.satapp.models.UtilToken;
import com.example.satapp.retrofit.IUsuarioService;
import com.example.satapp.retrofit.ServiceGenerator;
import com.example.satapp.viewmodel.UserProfileViewModel;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class perfil extends Fragment {

    private UserProfileViewModel userProfileViewModel;
    public String jwt;
    public TextView tvnombre, tvEmail, tvCreatedAt, tvUpdateAt, tvRole;
    public ImageView ivFoto, ivEmail, ivRol;
    public ProgressBar pbLoading;
    public Button btnEditar,btnBorrar;
    IUsuarioService service;
    ServiceGenerator serviceGenerator;

    public perfil() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userProfileViewModel = new ViewModelProvider(getActivity())
                .get(UserProfileViewModel.class);
        service = serviceGenerator.createService(IUsuarioService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);
        ivFoto = v.findViewById(R.id.imageViewFotoPerfil);
        tvnombre = v.findViewById(R.id.textInputNombre);
        tvEmail = v.findViewById(R.id.textViewEmail);
        tvCreatedAt = v.findViewById(R.id.textViewCreatedAt);
        tvUpdateAt = v.findViewById(R.id.textViewUpdateAt);
        tvRole = v.findViewById(R.id.textViewRole);
        ivEmail = v.findViewById(R.id.imageViewEmail);
        ivRol = v.findViewById(R.id.imageViewRol);
        btnEditar = v.findViewById(R.id.btnEditarFoto);
        btnBorrar = v.findViewById(R.id.btnEditarPerfil);
        loadData();
        return v;
    }

    public void loadData (){
        pbLoading.setVisibility(View.VISIBLE);
        btnEditar.setVisibility(View.INVISIBLE);
        btnBorrar.setVisibility(View.INVISIBLE);
        ivFoto.setVisibility(View.GONE);
        tvnombre.setVisibility(View.GONE);
        tvEmail.setVisibility(View.GONE);
        tvCreatedAt.setVisibility(View.GONE);
        tvUpdateAt.setVisibility(View.GONE);
        tvRole.setVisibility(View.GONE);
        ivEmail.setVisibility(View.GONE);
        ivRol.setVisibility(View.GONE);

        jwt = UtilToken.getToken(getContext());
        userProfileViewModel.getCurrentUser(jwt).observe(getActivity(),new Observer<User>(){
            @Override
            public void onChanged(User user){
                pbLoading.setVisibility(View.GONE);
                LocalDate createdAt = ConvertToDate(user.getCreatedAt());
                LocalDate updateAt = ConvertToDate(user.getUpdatedAt());
                DateTimeFormatter fmt = DateTimeFormat.forPattern("d MMMM, yyyy");

                tvnombre.setText(user.getName());
                tvRole.setText(user.getRole());
                tvEmail.setText(user.getEmail());
                tvCreatedAt.setText("Cuenta creada el: "+createdAt.toString(fmt));
                tvUpdateAt.setText("Última actualización : "+updateAt.toString(fmt));

                if(user.getPicture()!=null&& user!=null){
                    Call<ResponseBody> call = service.getAvatarUser(user.getId(),UtilToken.getToken(MyApp.getContext()));
                    call.enqueue(new Callback<ResponseBody>() {
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
                }else{
                    Glide.with(MyApp.getContext())
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

        if (jwt == null) {
            Toast.makeText(getActivity(),"Sin autorización",Toast.LENGTH_SHORT).show();
        }
    }

    private LocalDate ConvertToDate(String dateString){
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
