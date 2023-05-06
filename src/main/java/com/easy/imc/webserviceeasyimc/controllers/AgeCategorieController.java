package com.easy.imc.webserviceeasyimc.controllers;

import com.easy.imc.webserviceeasyimc.entities.IMCResponse;
import com.easy.imc.webserviceeasyimc.models.AgeCategorieModel;
import com.easy.imc.webserviceeasyimc.models.CategoryModel;
import com.easy.imc.webserviceeasyimc.services.AgeCategorieService;
import com.easy.imc.webserviceeasyimc.services.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/age-categories")
public class AgeCategorieController {
    @GetMapping("/all")
    public IMCResponse<AgeCategorieModel> getAll(){
        return AgeCategorieService.findAll();
    }
}
