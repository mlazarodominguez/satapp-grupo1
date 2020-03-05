package com.example.satapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketsResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("creado_por")
    @Expose
    private User creadoPor;
    @SerializedName("fecha_creacion")
    @Expose
    private String fechaCreacion;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("inventariable")
    @Expose
    private String inventariable;
    @SerializedName("anotaciones")
    @Expose
    private List<Object> anotaciones = null;
    @SerializedName("asignaciones")
    @Expose
    private List<Object> asignaciones = null;
    @SerializedName("fotos")
    @Expose
    private List<String> fotos = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
}
