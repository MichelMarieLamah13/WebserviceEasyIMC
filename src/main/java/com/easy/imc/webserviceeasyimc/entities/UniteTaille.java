package com.easy.imc.webserviceeasyimc.entities;

import com.easy.imc.webserviceeasyimc.models.UniteTailleModel;
import com.easy.imc.webserviceeasyimc.models.UserModel;

public class UniteTaille {
    public int id;
    public String name;
    public double value;

    public UniteTaille(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public UniteTaille() {
    }

    public UniteTailleModel toModel(){
        return new UniteTailleModel(id, name, value);
    }

    public UniteTaille(int id, String name, double value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "UniteTaille{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
