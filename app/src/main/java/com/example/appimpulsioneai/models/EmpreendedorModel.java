package com.example.appimpulsioneai.models;

import com.google.gson.annotations.SerializedName;

public class EmpreendedorModel {
    @SerializedName("idEmpreededor")
    private String id;

    @SerializedName("biografia")
    private String biografia;

    @SerializedName("numeroFavoritos")
    private int numeroFavoritos;

    @SerializedName("telefone")
    private String telefone;
    @SerializedName("site")
    private String site;

    @SerializedName("nomeCompleto")
    private String nomeCompleto;
    @SerializedName("cpf")
    private String cpf;
    @SerializedName("mei")
    private String mei;
    @SerializedName("verificado")
    private boolean verificado = false;
    @SerializedName("senha")
    private String senha;
    @SerializedName("nomeEmpreendimento")
    private String nomeEmpreendimento;
    @SerializedName("email")
    private String email;
    @SerializedName("planoAssinatura")
    private String planoAssinatura;
    @SerializedName("facebook")
    private String facebook;
    @SerializedName("instagram")
    private String instagram;
    @SerializedName("nomeExibicao")
    private  String nomeExibicao;
    @SerializedName("nicho")
    private NichoModel nicho;
    @SerializedName("modalidade")
    private String modalidade;
    @SerializedName("numeroVisitas")
    private int numeroVisitas;

    public String getId() {
        return id;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public int getNumeroFavoritos() {
        return numeroFavoritos;
    }

    public void setNumeroFavoritos(int numeroFavoritos) {
        this.numeroFavoritos = numeroFavoritos;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getMei() {
        return mei;
    }

    public void setMei(String mei) {
        this.mei = mei;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNomeEmpreendimento() {
        return nomeEmpreendimento;
    }

    public void setNomeEmpreendimento(String nomeEmpreendimento) {
        this.nomeEmpreendimento = nomeEmpreendimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlanoAssinatura() {
        return planoAssinatura;
    }

    public void setPlanoAssinatura(String planoAssinatura) {
        this.planoAssinatura = planoAssinatura;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getNomeExibicao() {
        return nomeExibicao;
    }

    public void setNomeExibicao(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public NichoModel getNicho() {
        return nicho;
    }

    public void setNicho(NichoModel nicho) {
        this.nicho = nicho;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public int getNumeroVisitas() {
        return numeroVisitas;
    }

    public void setNumeroVisitas(int numeroVisitas) {
        this.numeroVisitas = numeroVisitas;
    }
}
