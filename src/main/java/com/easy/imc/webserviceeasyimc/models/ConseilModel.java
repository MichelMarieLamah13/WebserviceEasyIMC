package com.easy.imc.webserviceeasyimc.models;

public class ConseilModel {
    public int id;
    public CategoryModel category;
    public String conseil;

    public ConseilModel() {
    }

    public ConseilModel(int id, CategoryModel category, String conseil) {
        this.id = id;
        this.category = category;
        this.conseil = conseil;
    }

    public ConseilModel(int id, String conseil) {
        this.id = id;
        this.conseil = conseil;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"category\":" + category +
                ", \"conseil\":\"" + conseil + '"' +
                '}';
    }
}
