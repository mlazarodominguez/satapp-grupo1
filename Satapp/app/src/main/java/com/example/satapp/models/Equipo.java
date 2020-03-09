package com.example.satapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipo {

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


        public Equipo(String nombre, String descripcion, String ubicacion) {
                this.nombre = nombre;
                this.descripcion = descripcion;
                this.ubicacion = ubicacion;
        }


}
