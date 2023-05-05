package com.easy.imc.webserviceeasyimc.entities;

import com.easy.imc.webserviceeasyimc.models.CategoryModel;
import com.easy.imc.webserviceeasyimc.models.DescriptionModel;
import org.springframework.http.HttpStatus;

public class Description {
    public int id;
    public int idCategory;
    public String description;

    public Description() {
    }

    public Description(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public Description(int id, int idCategory, String description) {
        this.id = id;
        this.idCategory = idCategory;
        this.description = description;
    }

    public DescriptionModel toModel(){
        DescriptionModel model = new DescriptionModel(id, description);
        IMCResponse<CategoryModel> cr = new IMCResponse<>();
        if(cr.status == HttpStatus.OK.value()){
            if(!cr.values.isEmpty()){
                model.category = cr.values.get(0);
            }
        }
        return model;
    }
}
