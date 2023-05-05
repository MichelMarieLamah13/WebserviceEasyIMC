package com.easy.imc.webserviceeasyimc.models;

import com.easy.imc.webserviceeasyimc.entities.AgeCategorie;
import com.easy.imc.webserviceeasyimc.entities.User;

public class UserModel {
    public int id;
    public String login;
    public String password;
    public String avatar;
    public int role;

    public int age;

    public AgeCategorieModel ageCategorie;

    public UserModel() {
    }

    public UserModel(int id, String login, String password, String avatar, int role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
    }

    public UserModel(int id, String login, String password, String avatar, int role, int age) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
        this.age = age;
    }

    public UserModel(int id, String login, String password, String avatar, int role, int age, AgeCategorieModel ageCategorie) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
        this.age = age;
        this.ageCategorie = ageCategorie;
    }

    public User toEntity(){
        if(ageCategorie!=null){
            return new User(id, login, password, avatar, role, age, ageCategorie.id);
        }
        return new User(id, login, password, avatar, role, age);
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"login\":\"" + login + '"' +
                ", \"password\":\"" + password + '"' +
                ", \"avatar\":\"" + avatar + '"' +
                ", \"role\":" + role +
                ", \"age\":" + age +
                '}';
    }
}
