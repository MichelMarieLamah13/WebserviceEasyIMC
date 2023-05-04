package com.easy.imc.webserviceeasyimc.controllers;

import com.easy.imc.webserviceeasyimc.entities.IMCResponse;
import com.easy.imc.webserviceeasyimc.entities.UniteTaille;
import com.easy.imc.webserviceeasyimc.models.UniteTailleModel;
import com.easy.imc.webserviceeasyimc.services.UniteTailleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unite-taille")
public class UniteTailleController {
    @GetMapping("/all")
    public IMCResponse<UniteTailleModel> getAll(){
        return UniteTailleService.findAll();
    }

    @GetMapping
    public IMCResponse<UniteTailleModel> getById(@RequestParam("id") int id){
        return UniteTailleService.findById(id);
    }

    @DeleteMapping("/delete")
    public IMCResponse<UniteTailleModel> deleteById(@RequestParam("id") int id){
        return UniteTailleService.delete(id);
    }

    @PostMapping("/create")
    public IMCResponse<UniteTailleModel> create(@RequestBody UniteTaille uniteTaille){
        return UniteTailleService.create(uniteTaille);
    }

    @PostMapping("/update")
    public IMCResponse<UniteTailleModel> update(@RequestParam("id") int id, @RequestBody UniteTaille uniteTaille){
        return UniteTailleService.update(uniteTaille);
    }
}
