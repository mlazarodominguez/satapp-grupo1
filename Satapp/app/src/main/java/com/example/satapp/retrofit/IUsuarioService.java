package com.example.satapp.retrofit;

import com.example.satapp.models.LoginReponse;
import com.example.satapp.models.User;
import com.example.satapp.models.UserLogin;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
}
