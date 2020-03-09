package com.example.satapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Anotacion {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_usuario")
    @Expose
    private User idUsuario;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("cuerpo")
    @Expose
    private String cuerpo;
    @SerializedName("ticket")
    @Expose
    private String ticket;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
}
