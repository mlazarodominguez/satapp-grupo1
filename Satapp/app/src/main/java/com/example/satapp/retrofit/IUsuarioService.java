package com.example.satapp.retrofit;

import com.example.satapp.models.LoginReponse;
import com.example.satapp.models.User;
import com.example.satapp.models.UserLogin;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import retrofit2.http.Part;

import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IUsuarioService {

    @POST("auth")
    Call<LoginReponse> login(@Header("Authorization")String authHeader, @Query("access_token")String masterKey);


    @Multipart
    @POST("users")
    Call<User> register(@Query("access_token")String masterKey,
                        @Part("email") RequestBody email,
                        @Part("name") RequestBody fullname,
                        @Part("password") RequestBody password,@Part MultipartBody.Part avatar);
    @Multipart
    @PUT("users/{id}/img")
    Call<User> actualizarImagen(@Path("id")String id,@Query("access_token")String token,@Part MultipartBody.Part avatar);


    @GET("users/no-validated")
    Call<List<User>> allUsersNonValidate(@Query("access_token") String token);

    @GET("users")
    Call<List<User>> allUsersValidated(@Query("access_token") String token);

    @GET("users/img/{id}")
    Call<ResponseBody>  getAvatarUser(@Path("id") String id,@Query("access_token")String token);

    @PUT("users/{id}/validate")
    Call<User> validarUsuario(@Path("id")String id, @Query("access_token")String token);

    @DELETE("users/{id}")
    Call<ResponseBody> borrarUsuario(@Path("id")String id,@Query("access_token")String token);

    @GET("users/me")
    Call<User> profile(@Query("access_token")String token);
    @Multipart
    @PUT("users/{id}")
    Call<User> updateProfile(@Path("id") String id,@Query("access_token")String token,@Part("name") RequestBody fullname);


    @PUT("users/{id}/tecnico")
    Call<User> upgradeTecnico(@Path("id")String id,@Query("access_token")String token);
    @GET("users/{id}")
    Call<User> perfilUsuario(@Path("id")String id,@Query("access_token")String token);
}

