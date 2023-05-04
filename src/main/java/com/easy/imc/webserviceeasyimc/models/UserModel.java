package com.easy.imc.webserviceeasyimc.models;

import com.easy.imc.webserviceeasyimc.entities.User;

public class UserModel {
    public int id;
    public String login;
    public String password;
    public String avatar;
    public int role;

    public UserModel() {
    }

    public UserModel(int id, String login, String password, String avatar, int role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
    }

    public User toEntity(){
        return new User(id, login, password, avatar, role);
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"login\":\"" + login + '"' +
                ", \"password\":\"" + password + '"' +
                ", \"avatar\":\"" + avatar + '"' +
                ", \"role\":" + role +
                '}';
    }
}
