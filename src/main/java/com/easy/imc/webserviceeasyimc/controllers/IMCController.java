package com.easy.imc.webserviceeasyimc.controllers;

import com.easy.imc.webserviceeasyimc.models.IMC;
import com.easy.imc.webserviceeasyimc.models.User;
import com.easy.imc.webserviceeasyimc.services.IMCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/imc")
public class IMCController {

    @GetMapping("/poids={poids}&taille={taille}")
    public ResponseEntity<IMC> getIMC(@PathVariable double poids, @PathVariable double taille, @RequestBody User user){
        IMC imc = new IMC(taille, poids);
        return ResponseEntity.ok(IMCService.getValue(imc));
    }
}
