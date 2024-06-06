package com.example.appimpulsioneai.Response;

import java.util.UUID;

public class LoginResponse {
    private String token;
    private String tipoUsuarioUri;
    private String idUsuario;

    // Getters and setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipoUsuarioUri() {
        return tipoUsuarioUri;
    }

    public void setTipoUsuarioUri(String tipoUsuarioUri) {
        this.tipoUsuarioUri = tipoUsuarioUri;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isSuccess() {
        return token != null && !token.isEmpty();
    }


}
