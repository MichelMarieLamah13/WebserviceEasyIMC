package com.easy.imc.webserviceeasyimc.models;

import com.easy.imc.webserviceeasyimc.entities.UniteTaille;

public class UniteTailleModel {
    public int id;
    public String name;
    public double value;

    public UniteTailleModel() {
    }

    public UniteTailleModel(int id, String name, double value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public UniteTaille toEntity(){
        return new UniteTaille(id, name, value);
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
