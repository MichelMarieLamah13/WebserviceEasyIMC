package com.easy.imc.webserviceeasyimc.entities;

import com.easy.imc.webserviceeasyimc.models.UserModel;

public class User {
    public int id;
    public String login;
    public String password;
    public String avatar;

    public int role;

    public User() {
    }

    public User(int id, String login, String password, String avatar, int role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
    }

    public UserModel toUserModel(){
        return new UserModel(id, login, password, avatar, role);
    }
}
