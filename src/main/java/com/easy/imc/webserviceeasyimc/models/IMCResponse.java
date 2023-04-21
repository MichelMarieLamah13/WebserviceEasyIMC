package com.easy.imc.webserviceeasyimc.models;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class IMCResponse {
    public String message;
    public List<Object> values;
    public int status;
    public String name;

    public IMCResponse() {
        message = "Erreur lors de l'exécution";
        values = new ArrayList<>();
        name = HttpStatus.INTERNAL_SERVER_ERROR.name();
        status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
