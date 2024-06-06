package com.example.appimpulsioneai.Services;

import com.example.appimpulsioneai.Request.LoginRequest;
import com.example.appimpulsioneai.Response.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @PUT("editarSenha/{idUsuario}")
    Call<Void> changePassword(@Path("idUsuario") String idUsuario, @Body PasswordChangeRequest request);

    @GET("verificaUsuarios")
    Call<ResponseBody> verificaUsuarios(@Query("email") String email);

    @POST("usuarios")
    Call<ResponseBody> registerUser(@Body User user);

    @POST("empreendedores")
    Call<ResponseBody> registerPartner(@Body Partner partner);

    @GET("usuarios/{id}")
    Call<User> getUserData(@retrofit2.http.Path("id") String userId);


    class PasswordChangeRequest {
        private String senha;

        public PasswordChangeRequest(String senha) {
            this.senha = senha;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }
    }

    // Classe User
    class User {
        private String nome;
        private String dataNascimento;
        private String cpf;
        private String senha;
        private String email;

        public User(String nome, String dataNascimento, String cpf, String senha, String email) {
            this.nome = nome;
            this.dataNascimento = dataNascimento;
            this.cpf = cpf;
            this.senha = senha;
            this.email = email;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getDataNascimento() {
            return dataNascimento;
        }

        public void setDataNascimento(String dataNascimento) {
            this.dataNascimento = dataNascimento;
        }

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    class Partner {
        private String nomeCompleto;
        private String dataNascimento;
        private String cpf;
        private String mei;
        private String senha;
        private String nomeEmpreendimento;
        private String site;
        private String telefone;
        private String email;
        private String planoAssinatura;
        private String instagram;
        private String facebook;
        private String idNicho;
        private String modalidade;
        private Endereco endereco;

        // Getters e setters

        public String getNomeCompleto() {
            return nomeCompleto;
        }

        public void setNomeCompleto(String nomeCompleto) {
            this.nomeCompleto = nomeCompleto;
        }

        public String getDataNascimento() {
            return dataNascimento;
        }

        public void setDataNascimento(String dataNascimento) {
            this.dataNascimento = dataNascimento;
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

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getTelefone() {
            return telefone;
        }

        public void setTelefone(String telefone) {
            this.telefone = telefone;
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

        public String getInstagram() {
            return instagram;
        }

        public void setInstagram(String instagram) {
            this.instagram = instagram;
        }

        public String getFacebook() {
            return facebook;
        }

        public void setFacebook(String facebook) {
            this.facebook = facebook;
        }

        public String getIdNicho() {
            return idNicho;
        }

        public void setIdNicho(String idNicho) {
            this.idNicho = idNicho;
        }

        public String getModalidade() {
            return modalidade;
        }

        public void setModalidade(String modalidade) {
            this.modalidade = modalidade;
        }

        public Endereco getEndereco() {
            return endereco;
        }

        public void setEndereco(Endereco endereco) {
            this.endereco = endereco;
        }

        public Partner(String nomeCompleto, String dataNascimento, String cpf, String mei, String senha, String nomeEmpreendimento,
                       String site, String telefone, String email, String planoAssinatura, String instagram, String facebook,
                       String idNicho, String modalidade, Endereco endereco) {
            this.nomeCompleto = nomeCompleto;
            this.dataNascimento = dataNascimento;
            this.cpf = cpf;
            this.mei = mei;
            this.senha = senha;
            this.nomeEmpreendimento = nomeEmpreendimento;
            this.site = site;
            this.telefone = telefone;
            this.email = email;
            this.planoAssinatura = planoAssinatura;
            this.instagram = instagram;
            this.facebook = facebook;
            this.idNicho = idNicho;
            this.modalidade = modalidade;
            this.endereco = endereco;
        }


    }

    // Classe Endereco
    class Endereco {
        private String uf;
        private String cidade;
        private String bairro;
        private String logradouro;
        private String numero;

        public Endereco(String uf, String cidade, String bairro, String logradouro, String numero) {
            this.uf = uf;
            this.cidade = cidade;
            this.bairro = bairro;
            this.logradouro = logradouro;
            this.numero = numero;
        }

        // Getters e setters

        public String getUf() {
            return uf;
        }

        public void setUf(String uf) {
            this.uf = uf;
        }

        public String getCidade() {
            return cidade;
        }

        public void setCidade(String cidade) {
            this.cidade = cidade;
        }

        public String getBairro() {
            return bairro;
        }

        public void setBairro(String bairro) {
            this.bairro = bairro;
        }

        public String getLogradouro() {
            return logradouro;
        }

        public void setLogradouro(String logradouro) {
            this.logradouro = logradouro;
        }

        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }
    }


}
