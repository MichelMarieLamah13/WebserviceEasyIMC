package com.easy.imc.webserviceeasyimc.models;

public class DescriptionModel {
    public int id;
    public CategoryModel category;
    public String description;

    public DescriptionModel(int id, CategoryModel category, String description) {
        this.id = id;
        this.category = category;
        this.description = description;
    }

    public DescriptionModel() {
    }

    public DescriptionModel(int id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"category\":" + category +
                ", \"description\":\"" + description + '"' +
                '}';
    }
}
