package com.easy.imc.webserviceeasyimc.models;

import com.easy.imc.webserviceeasyimc.entities.UnitePoids;
import com.easy.imc.webserviceeasyimc.entities.UniteTaille;

public class UnitePoidsModel {
    public int id;
    public String name;
    public double value;

    public UnitePoidsModel(int id, String name, double value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public UnitePoidsModel() {
    }

    public UnitePoids toEntity(){
        return new UnitePoids(id, name, value);
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"name\":\"" + name + '"' +
                ", \"value\":" + value +
                '}';
    }
}
