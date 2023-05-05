package com.easy.imc.webserviceeasyimc.entities;

import com.easy.imc.webserviceeasyimc.models.*;
import com.easy.imc.webserviceeasyimc.services.CategoryService;
import com.easy.imc.webserviceeasyimc.services.UnitePoidsService;
import com.easy.imc.webserviceeasyimc.services.UniteTailleService;
import com.easy.imc.webserviceeasyimc.services.UserService;
import org.springframework.http.HttpStatus;

public class History {
    public int id;
    public int idUser;
    public double poids;
    public double taille;
    public double imc;

    public int idUnitePoids;

    public int idUniteTaille;

    public String date;

    public String heure;

    public int idCategory;

    public boolean save = true;

    public HistoryModel toHistoryModel(){
        HistoryModel history = new HistoryModel();
        history.poids = poids;
        history.taille = taille;
        history.imc = imc;
        history.date = date;
        history.heure = heure;
        history.save = save;
        IMCResponse<UserModel> ur = UserService.findById(idUser);
        if(ur.status == HttpStatus.OK.value()){
            if(!ur.values.isEmpty()){
                history.user = ur.values.get(0);
            }
        }

        IMCResponse<CategoryModel> cr = CategoryService.findById(idCategory);
        if(cr.status == HttpStatus.OK.value()){
            if(!cr.values.isEmpty()){
                history.category = cr.values.get(0);
            }
        }

        IMCResponse<UnitePoidsModel> upr = UnitePoidsService.findById(idUnitePoids);
        if(upr.status == HttpStatus.OK.value()){
            if(!upr.values.isEmpty()){
                history.unitePoids = upr.values.get(0);
            }
        }

        IMCResponse<UniteTailleModel> utr = UniteTailleService.findById(idUniteTaille);
        if(utr.status == HttpStatus.OK.value()){
            if(!utr.values.isEmpty()){
                history.uniteTaille = utr.values.get(0);
            }
        }
        return history;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"idUser\":" + idUser +
                ", \"poids\":" + poids +
                ", \"taille\":" + taille +
                ", \"imc\":" + imc +
                ", \"idUnitePoids\":" + idUnitePoids +
                ", \"idUniteTaille\":" + idUniteTaille +
                ", \"date\":\"" + date + '"' +
                ", \"heure\":\"" + heure + '"' +
                ", \"idCategory\":" + idCategory +
                ", \"save\":" + save +
                "}";
    }
}
