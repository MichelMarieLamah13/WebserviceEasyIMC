package com.easy.imc.webserviceeasyimc.entities;

import com.easy.imc.webserviceeasyimc.models.AgeCategorieModel;
import com.easy.imc.webserviceeasyimc.models.CategoryModel;
import com.easy.imc.webserviceeasyimc.models.UserModel;
import com.easy.imc.webserviceeasyimc.services.AgeCategorieService;
import com.easy.imc.webserviceeasyimc.services.UserService;
import org.springframework.http.HttpStatus;

public class User {
    public int id;
    public String login;
    public String password;
    public String avatar;

    public int role;
    public int age;

    public int idAgeCategorie;

    public User() {
    }

    public User(int id, String login, String password, String avatar, int role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
    }

    public User(int id, String login, String password, String avatar, int role, int age, int idAgeCategorie) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
        this.age =age;
        this.idAgeCategorie = idAgeCategorie;
    }

    public User(int id, String login, String password, String avatar, int role, int age) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
        this.age =age;
    }

    public UserModel toUserModel(){
        AgeCategorieModel ageCategorieModel = new AgeCategorieModel();
        IMCResponse<AgeCategorieModel> ur = AgeCategorieService.findById(idAgeCategorie);
        if(ur.status == HttpStatus.OK.value()){
            if(!ur.values.isEmpty()){
                ageCategorieModel = ur.values.get(0);
            }
        }
        return new UserModel(id, login, password, avatar, role, age, ageCategorieModel);
    }
}
