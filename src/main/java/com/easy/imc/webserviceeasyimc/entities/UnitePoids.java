package com.easy.imc.webserviceeasyimc.entities;

import com.easy.imc.webserviceeasyimc.models.UnitePoidsModel;

public class UnitePoids {
    public int id;
    public String name;
    public double value;

    public UnitePoids() {
    }

    public UnitePoids(String name, double value) {
        this.name = name;
        this.value = value;
    }



    public UnitePoidsModel toModel(){
        return new UnitePoidsModel(id, name, value);
    }

    public UnitePoids(int id, String name, double value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "UnitePoids{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
