package com.example.satapp.ui.profile;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.satapp.R;
import com.example.satapp.models.User;
import com.example.satapp.models.UtilToken;
import com.example.satapp.viewmodel.UserProfileViewModel;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class perfil extends Fragment {

    private UserProfileViewModel userProfileViewModel;
    public String jwt;
    public TextView tvnombre, tvEmail, tvCreatedAt, tvUpdateAt, tvRole;
    public ImageView ivFoto, ivEmail, ivRol;
    public ProgressBar pbLoading;

    public perfil() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userProfileViewModel = new ViewModelProvider(getActivity())
                .get(UserProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);
        ivFoto = v.findViewById(R.id.imageViewFotoPerfil);
        tvnombre = v.findViewById(R.id.textViewNombre);
        tvEmail = v.findViewById(R.id.textViewEmail);
        tvCreatedAt = v.findViewById(R.id.textViewCreatedAt);
        tvUpdateAt = v.findViewById(R.id.textViewUpdateAt);
        tvRole = v.findViewById(R.id.textViewRole);
        pbLoading = v.findViewById(R.id.progressBarLoading);
        ivEmail = v.findViewById(R.id.imageViewEmail);
        ivRol = v.findViewById(R.id.imageViewRol);
        loadData();
        return v;
    }

    public void loadData (){
        pbLoading.setVisibility(View.VISIBLE);
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

                Glide
                        .with(getActivity())
                        .load(user.getPicture())
                        .centerCrop()
                        .into(ivFoto);

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
