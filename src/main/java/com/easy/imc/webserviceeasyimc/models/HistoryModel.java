package com.easy.imc.webserviceeasyimc.models;

public class HistoryModel {
    public int id;
    public UserModel user;
    public UniteTailleModel uniteTaille;
    public UnitePoidsModel unitePoids;
    public double poids;
    public double taille;
    public double imc;
    public String date;
    public String heure;
    public CategoryModel category;

    public boolean save = true;

    public HistoryModel() {
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"user\":" + user +
                ", \"uniteTaille\":" + uniteTaille +
                ", \"unitePoids\":" + unitePoids +
                ", \"poids\":" + poids +
                ", \"taille\":" + taille +
                ", \"imc\":" + imc +
                ", \"date\":\"" + date + '"' +
                ", \"heure\":\"" + heure + '"' +
                ", \"category\":" + category +
                ", \"save\":" + save +
                '}';
    }
}
