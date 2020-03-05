package com.example.satapp.models.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EquipoResponseEdit {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("codigo")
    @Expose
    private String codigo;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("ubicacion")
    @Expose
    private String ubicacion;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("imagen")
    @Expose
    private String imagen;

}
