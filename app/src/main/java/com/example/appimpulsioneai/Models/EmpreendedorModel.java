package com.example.appimpulsioneai.Models;

import com.google.gson.annotations.SerializedName;

public class EmpreendedorModel {

    @SerializedName("nomeEmpreendimento")
    private String nomeEmpreendimento;

    @SerializedName("nicho")
    private Nicho nicho;

    // Getters and setters

    public String getNomeEmpreendimento() {
        return nomeEmpreendimento;
    }

    public void setNomeEmpreendimento(String nomeEmpreendimento) {
        this.nomeEmpreendimento = nomeEmpreendimento;
    }

    public Nicho getNicho() {
        return nicho;
    }

    public void setNicho(Nicho nicho) {
        this.nicho = nicho;
    }

    public static class Nicho {
        @SerializedName("nicho")
        private String nicho;

        @SerializedName("id")
        private int id;

        // Getters and setters

        public String getNicho() {
            return nicho;
        }

        public void setNicho(String nicho) {
            this.nicho = nicho;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
