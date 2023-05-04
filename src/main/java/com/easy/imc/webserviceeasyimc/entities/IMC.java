package com.easy.imc.webserviceeasyimc.entities;

public class IMC {
    public double taille;
    public double poids;
    public double value;
    public User user;
    public UnitePoids unitePoids;
    public UniteTaille uniteTaille;

    public IMC(double taille, double poids) {
        this.taille = taille;
        this.poids = poids;
    }

    public IMC() {
    }

    public IMC(double taille, double poids, UnitePoids unitePoids, UniteTaille uniteTaille) {
        this.taille = taille;
        this.poids = poids;
        this.unitePoids = unitePoids;
        this.uniteTaille = uniteTaille;
    }


    public IMC(double taille, double poids, double value, User user, UnitePoids unitePoids, UniteTaille uniteTaille) {
        this.taille = taille;
        this.poids = poids;
        this.value = value;
        this.user = user;
        this.unitePoids = unitePoids;
        this.uniteTaille = uniteTaille;
    }

    @Override
    public String toString() {
        return "IMC{" +
                "taille=" + taille +
                ", poids=" + poids +
                ", value=" + value +
                ", user=" + user +
                ", unitePoids=" + unitePoids +
                ", uniteTaille=" + uniteTaille +
                '}';
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UnitePoids getUnitePoids() {
        return unitePoids;
    }

    public void setUnitePoids(UnitePoids unitePoids) {
        this.unitePoids = unitePoids;
    }

    public UniteTaille getUniteTaille() {
        return uniteTaille;
    }

    public void setUniteTaille(UniteTaille uniteTaille) {
        this.uniteTaille = uniteTaille;
    }
}
