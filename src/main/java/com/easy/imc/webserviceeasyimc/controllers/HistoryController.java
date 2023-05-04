package com.easy.imc.webserviceeasyimc.controllers;

import com.easy.imc.webserviceeasyimc.entities.History;
import com.easy.imc.webserviceeasyimc.entities.IMCResponse;
import com.easy.imc.webserviceeasyimc.entities.User;
import com.easy.imc.webserviceeasyimc.models.HistoryModel;
import com.easy.imc.webserviceeasyimc.services.HistoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/histories")
public class HistoryController {

    @PostMapping("/all")
    public IMCResponse<HistoryModel> getAll(@RequestParam("recentlyAdded") boolean recentlyAdded, @RequestParam("limit") int limit, @RequestBody User user){
        return HistoryService.findAll(user, recentlyAdded, limit);
    }


    @PostMapping("/search")
    public IMCResponse<HistoryModel> getHistoryByMultiCriteria(@RequestBody History history){

        return HistoryService.getHistoryByMultiCriteria(history);
    }
}
