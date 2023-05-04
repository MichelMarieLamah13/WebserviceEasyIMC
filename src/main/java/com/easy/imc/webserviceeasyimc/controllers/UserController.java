package com.easy.imc.webserviceeasyimc.controllers;

import com.easy.imc.webserviceeasyimc.entities.IMCResponse;
import com.easy.imc.webserviceeasyimc.entities.User;
import com.easy.imc.webserviceeasyimc.models.UserModel;
import com.easy.imc.webserviceeasyimc.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/all")
    public IMCResponse<UserModel> getAll(){
        return UserService.findAll();
    }

    @GetMapping
    public IMCResponse<UserModel> getById(@RequestParam("id") int id){
        return UserService.findById(id);
    }

    @DeleteMapping("/delete")
    public IMCResponse<UserModel> deleteById(@RequestParam("id") int id){
        return UserService.delete(id);
    }

    @PostMapping("/create")
    public IMCResponse<UserModel> create(@RequestBody User user){
        return UserService.create(user);
    }

    @PostMapping("/update")
    public IMCResponse<UserModel> update(@RequestParam("id") int id, @RequestBody User user){
        return UserService.update(user);
    }

    @PostMapping("/login")
    public IMCResponse<UserModel> getById(@RequestBody User user){
        return UserService.login(user);
    }
}
