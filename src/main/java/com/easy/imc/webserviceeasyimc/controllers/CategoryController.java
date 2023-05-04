package com.easy.imc.webserviceeasyimc.controllers;

import com.easy.imc.webserviceeasyimc.entities.IMCResponse;
import com.easy.imc.webserviceeasyimc.entities.User;
import com.easy.imc.webserviceeasyimc.models.CategoryCountModel;
import com.easy.imc.webserviceeasyimc.models.CategoryModel;
import com.easy.imc.webserviceeasyimc.services.CategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @GetMapping("/all")
    public IMCResponse<CategoryModel> getAll(){
        return CategoryService.findAll();
    }

    @PostMapping("/counts")
    public IMCResponse<CategoryCountModel> getAllCounts(@RequestBody User user){
        return CategoryService.findAllCount(user);
    }

}
