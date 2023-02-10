package com.security.security.config;

//Token Classe criado que vai ser chamado na tokenUtil
public class AuthToken {
    private String token;

    public AuthToken() {}
    public AuthToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
