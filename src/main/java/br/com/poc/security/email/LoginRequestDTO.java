package br.com.poc.security.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class LoginRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
