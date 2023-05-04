package com.easy.imc.webserviceeasyimc.services;

import com.easy.imc.webserviceeasyimc.entities.IMC;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IMCService {

    public static IMC getValue(IMC imc){
        try{
            double poids = imc.poids * imc.unitePoids.value;
            double taille = imc.taille * imc.uniteTaille.value;
            imc.value = poids / (taille * taille);
            BigDecimal bd=new BigDecimal(imc.value).setScale(2, RoundingMode.HALF_DOWN);
            imc.value = bd.doubleValue();
            return imc;
        }catch (Exception ex){
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +ex.getMessage()+":"+imc);
            throw new RuntimeException("Erreur calcul imc: "+ex.getMessage());
        }
    }
}
