package com.easy.imc.webserviceeasyimc.entities;

import com.easy.imc.webserviceeasyimc.models.CategoryModel;
import com.easy.imc.webserviceeasyimc.models.ConseilModel;
import com.easy.imc.webserviceeasyimc.models.UserModel;
import com.easy.imc.webserviceeasyimc.services.CategoryService;
import org.springframework.http.HttpStatus;

public class Conseil {
    public int id;
    public int idCategory;
    public String conseil;

    public Conseil() {
    }

    public Conseil(int id, int idCategory, String conseil) {
        this.id = id;
        this.idCategory = idCategory;
        this.conseil = conseil;
    }

    public ConseilModel toModel(){
        ConseilModel model = new ConseilModel(id,  conseil);
        IMCResponse<CategoryModel> cr = CategoryService.findById(idCategory);
        if(cr.status == HttpStatus.OK.value()){
            if(!cr.values.isEmpty()){
                model.category = cr.values.get(0);
            }
        }
        return model;
    }
}
