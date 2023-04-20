package com.easy.imc.webserviceeasyimc.services;

import com.easy.imc.webserviceeasyimc.models.IMC;
import org.springframework.stereotype.Service;

public class IMCService {

    public static IMC getValue(IMC imc){
        imc.value = imc.poids / (imc.taille * imc.taille);
        return imc;
    }
}
