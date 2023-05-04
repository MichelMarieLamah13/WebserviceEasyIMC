package com.easy.imc.webserviceeasyimc.models;

public class CategoryModel {
    public int id;
    public String title;
    public String subtitle;
    public double min;
    public double max;
    public String avatar;

    public CategoryModel() {
    }

    public CategoryModel(int id, String title, String subtitle, double min, double max, String avatar) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.min = min;
        this.max = max;
        this.avatar = avatar;
    }

    public CategoryCountModel toCountModel(){
        return new CategoryCountModel(id, title, subtitle, min, max, avatar, 0);
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"title\":\"" + title + '"' +
                ", \"subtitle\":\"" + subtitle + '"' +
                ", \"min\":" + min +
                ", \"max\":" + max +
                ", \"avatar\":\"" + avatar + '"' +
                '}';
    }
}
