package com.easy.imc.webserviceeasyimc.controllers;

import com.easy.imc.webserviceeasyimc.dao.Database;
import com.easy.imc.webserviceeasyimc.entities.IMCResponse;
import com.easy.imc.webserviceeasyimc.entities.InitDB;
import com.easy.imc.webserviceeasyimc.services.*;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/init")
public class InitController {
    private InitDB initDB;
    @PostConstruct
    public void init(){
        initDB = new InitDB();
    }

    @GetMapping
    public IMCResponse<String> process(@RequestParam boolean reset){
        IMCResponse<String> res = new IMCResponse<>();
        if (reset){
            res.values.add(Database.dropAllTables().message);
        }

        res.values.add(Database.createUsersTable().message);
        res.values.add(Database.createCategoriesTable().message);
        res.values.add(Database.createDescriptionsTable().message);
        res.values.add(Database.createConseilsTable().message);
        res.values.add(Database.createUnitePoidsTable().message);
        res.values.add(Database.createUniteTailleTable().message);
        res.values.add(Database.createHistoriesTable().message);

        if(reset){
            res.values.add(UserService.initUsersTable(initDB.users).message);
            res.values.add(CategoryService.initCategoriesTable(initDB.categories).message);
            res.values.add(ConseilService.initConseilsTable(initDB.conseils).message);
            res.values.add(DescriptionService.initDescriptionsTable(initDB.descriptions).message);
            res.values.add(UnitePoidsService.initUnitePoidsTable(initDB.unitePoids).message);
            res.values.add(UniteTailleService.initUniteTaillesTable(initDB.uniteTailles).message);
            res.values.add(HistoryService.initHistoriesTable(initDB.histories).message);
        }
        res.status = HttpStatus.OK.value();
        res.name = HttpStatus.OK.name();
        res.message = "Initialisation de la base de données";

        return res;
    }
}
