package com.easy.imc.webserviceeasyimc.controllers;

import com.easy.imc.webserviceeasyimc.entities.IMCResponse;
import com.easy.imc.webserviceeasyimc.models.DescriptionModel;
import com.easy.imc.webserviceeasyimc.models.UniteTailleModel;
import com.easy.imc.webserviceeasyimc.services.DescriptionService;
import com.easy.imc.webserviceeasyimc.services.UniteTailleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/descriptions")
public class DescriptionController {

    @GetMapping
    public IMCResponse<DescriptionModel> getByIdCategory(@RequestParam("idCategory") int id){
        return DescriptionService.findByIdCategory(id);
    }
}
