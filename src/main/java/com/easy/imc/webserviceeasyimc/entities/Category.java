package com.easy.imc.webserviceeasyimc.entities;

import com.easy.imc.webserviceeasyimc.models.CategoryModel;

public class Category {
    public int id;
    public String title;

    public String subtitle;
    public double min;
    public double max;

    public String avatar;

    public Category() {
    }

    public Category(int id, String title, String subtitle, double min, double max, String avatar) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.min = min;
        this.max = max;
        this.avatar = avatar;
    }

    public CategoryModel toModel(){
        return new CategoryModel(id, title, subtitle, min, max, avatar);
    }
}
