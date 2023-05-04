package com.easy.imc.webserviceeasyimc.controllers;

import com.easy.imc.webserviceeasyimc.entities.IMCResponse;
import com.easy.imc.webserviceeasyimc.models.ConseilModel;
import com.easy.imc.webserviceeasyimc.models.DescriptionModel;
import com.easy.imc.webserviceeasyimc.services.ConseilService;
import com.easy.imc.webserviceeasyimc.services.DescriptionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conseils")
public class ConseilController {
    @GetMapping
    public IMCResponse<ConseilModel> getByIdCategory(@RequestParam("idCategory") int id){
        return ConseilService.findByIdCategory(id);
    }
}
