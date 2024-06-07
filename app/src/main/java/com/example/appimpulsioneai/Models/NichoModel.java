package com.example.appimpulsioneai.Models;

import com.google.gson.annotations.SerializedName;

public class NichoModel {
    @SerializedName("idNicho")
    private int id;

    @SerializedName("nicho")
    private String nome;

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

