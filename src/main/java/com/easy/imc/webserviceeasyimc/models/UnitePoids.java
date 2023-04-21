package com.easy.imc.webserviceeasyimc.models;

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
}
