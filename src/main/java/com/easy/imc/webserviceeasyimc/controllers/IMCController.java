package com.easy.imc.webserviceeasyimc.controllers;

import com.easy.imc.webserviceeasyimc.models.IMC;
import com.easy.imc.webserviceeasyimc.models.IMCResponse;
import com.easy.imc.webserviceeasyimc.models.User;
import com.easy.imc.webserviceeasyimc.services.IMCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/imc")
public class IMCController {

    @GetMapping
    public IMCResponse getIMC(@RequestParam("poids") double poids, @RequestParam("taille") double taille, @RequestBody User user){
        IMC imc = new IMC(taille, poids);
        imc.value = IMCService.getValue(poids, taille);
        IMCResponse res = new IMCResponse();
        res.message = "Calcul de l'imc";
        res.status = HttpStatus.OK.value();
        res.name = HttpStatus.OK.name();
        res.values.add(imc);
        return res;
    }
}
