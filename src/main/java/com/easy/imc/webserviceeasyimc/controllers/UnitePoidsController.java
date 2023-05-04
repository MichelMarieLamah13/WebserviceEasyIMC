package com.easy.imc.webserviceeasyimc.controllers;

import com.easy.imc.webserviceeasyimc.entities.IMCResponse;
import com.easy.imc.webserviceeasyimc.entities.UnitePoids;
import com.easy.imc.webserviceeasyimc.entities.User;
import com.easy.imc.webserviceeasyimc.models.UnitePoidsModel;
import com.easy.imc.webserviceeasyimc.models.UserModel;
import com.easy.imc.webserviceeasyimc.services.UnitePoidsService;
import com.easy.imc.webserviceeasyimc.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unite-poids")
public class UnitePoidsController {
    @GetMapping("/all")
    public IMCResponse<UnitePoidsModel> getAll(){
        return UnitePoidsService.findAll();
    }

    @GetMapping
    public IMCResponse<UnitePoidsModel> getById(@RequestParam("id") int id){
        return UnitePoidsService.findById(id);
    }

    @DeleteMapping("/delete")
    public IMCResponse<UnitePoidsModel> deleteById(@RequestParam("id") int id){
        return UnitePoidsService.delete(id);
    }

    @PostMapping("/create")
    public IMCResponse<UnitePoidsModel> create(@RequestBody UnitePoids unitePoids){
        return UnitePoidsService.create(unitePoids);
    }

    @PostMapping("/update")
    public IMCResponse<UnitePoidsModel> update(@RequestParam("id") int id, @RequestBody UnitePoids unitePoids){
        return UnitePoidsService.update(unitePoids);
    }
}
