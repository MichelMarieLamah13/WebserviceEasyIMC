package com.easy.imc.webserviceeasyimc.controllers;

import com.easy.imc.webserviceeasyimc.entities.IMC;
import com.easy.imc.webserviceeasyimc.entities.IMCResponse;
import com.easy.imc.webserviceeasyimc.entities.User;
import com.easy.imc.webserviceeasyimc.models.HistoryModel;
import com.easy.imc.webserviceeasyimc.services.HistoryService;
import com.easy.imc.webserviceeasyimc.services.IMCService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/imc")
public class IMCController {

    @PostMapping
    public IMCResponse<HistoryModel> getIMC(@RequestBody IMC imc){
        IMC nImc = IMCService.getValue(imc);
        IMCResponse<HistoryModel> res = HistoryService.create(nImc);
        res.message = "Calcul de l'imc";
        return res;
    }
}
