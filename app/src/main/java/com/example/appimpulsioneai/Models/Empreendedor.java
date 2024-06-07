package com.example.appimpulsioneai.Models;

public class Empreendedor {
    private String nomeEmpreendimento;
    private String nomeEmpreendedor;
    private String biografia;
    private String modalidade;
    private String instagram;
    private String facebook;
    private String numContato;
    private String emailEmpreendedor;

    public Empreendedor(String nomeEmpreendimento, String nomeEmpreendedor, String biografia, String modalidade, String emailEmpreendedor, String numContato, String instagram, String facebook) {
        this.nomeEmpreendimento = nomeEmpreendimento;
        this.nomeEmpreendedor = nomeEmpreendedor;
        this.biografia = biografia;
        this.modalidade = modalidade;
        this.emailEmpreendedor = emailEmpreendedor;
        this.numContato = numContato;
        this.instagram = instagram;
        this.facebook = facebook;
    }

    public String getNomeEmpreendimento() {
        return nomeEmpreendimento;
    }
    public void setNomeEmpreendimento(String nomeEmpreendimento) {
        this.nomeEmpreendimento = nomeEmpreendimento;
    }

    public String getNomeEmpreendedor() {
        return nomeEmpreendedor;
    }
    public void setNomeEmpreendedor(String nomeEmpreendedor) {
        this.nomeEmpreendedor = nomeEmpreendedor;
    }

    public String getBiografia() {
        return biografia;
    }
    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getModalidade() {
        return modalidade;
    }
    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getEmailEmpreendedor() {
        return emailEmpreendedor;
    }
    public void setEmailEmpreendedor(String emailEmpreendedor) { this.emailEmpreendedor = emailEmpreendedor; }

    public String getNumContato() {
        return numContato;
    }
    public void setNumContato(String numContato) { this.numContato = numContato; }

    public String getInstagram() {
        return instagram;
    }
    public void setInstagram(String instagram) { this.instagram = instagram; }

    public String getFacebook() {
        return facebook;
    }
    public void setFacebook(String facebook) { this.facebook = facebook; }
}
