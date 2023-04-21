package com.easy.imc.webserviceeasyimc.services;

import com.easy.imc.webserviceeasyimc.models.IMC;
import org.springframework.stereotype.Service;

public class IMCService {

    public static double getValue(double poids, double taille){
        return poids / (taille * taille);
    }
}
