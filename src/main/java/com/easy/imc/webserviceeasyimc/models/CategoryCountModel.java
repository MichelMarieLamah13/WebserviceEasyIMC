package com.easy.imc.webserviceeasyimc.models;

public class CategoryCountModel extends CategoryModel{
    public int count;

    public CategoryCountModel(int count) {
        this.count = count;
    }

    public CategoryCountModel(int id, String title, String subtitle, double min, double max, String avatar, int count) {
        super(id, title, subtitle, min, max, avatar);
        this.count = count;
    }

    @Override
    public String toString() {
        return "{" +
                "\"count\":" + count +
                ", \"id\":" + id +
                ", \"title\":\"" + title + '"' +
                ", \"subtitle\":\"" + subtitle + '"' +
                ", \"min\":" + min +
                ", \"max\":" + max +
                ", \"avatar\":\"" + avatar + '"' +
                '}';
    }
}
