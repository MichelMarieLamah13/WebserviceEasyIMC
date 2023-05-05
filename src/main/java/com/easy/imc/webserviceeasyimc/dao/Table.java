package com.easy.imc.webserviceeasyimc.dao;

public enum Table {
    USERS("users"),
    CATEGORIES("categories"),
    DESCRIPTIONS("descriptions"),
    HISTORIES("histories"),
    CONSEILS("conseils"),
    UNITE_POIDS("unitePoids"),
    UNITE_TAILLE("uniteTailles"),
    AGE_CATEGORIES("ageCategories");

    private final String value;
    Table(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
