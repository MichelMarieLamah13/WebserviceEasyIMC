package com.easy.imc.webserviceeasyimc.dao;

import com.easy.imc.webserviceeasyimc.entities.IMCResponse;
import org.springframework.http.HttpStatus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    public static String location = "imc.sqlite";
    public static Connection connect() {
        String dbPrefix = "jdbc:sqlite:";
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + location);
        } catch (SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE,
                    LocalDateTime.now() + ": Could not connect to SQLite DB at " +
                            location);
            return null;
        }
        return connection;
    }

    public static boolean checkDrivers(){
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new org.sqlite.JDBC());
            System.out.println("Connection to sqlite done");
            return true;
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not start SQLite Drivers");
        }
        return false;
    }

    public static IMCResponse createUsersTable(){
        String query = "CREATE TABLE IF NOT EXISTS "+Table.USERS.getValue()+" (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "login TEXT," +
                "password TEXT," +
                "age INT," +
                "idAgeCategorie INT," +
                "role INT," +
                "avatar TEXT," +
                "FOREIGN KEY (idAgeCategorie) REFERENCES "+Table.AGE_CATEGORIES.getValue()+" (id) )";
        return Database.execute(query, Table.USERS);
    }

    public static IMCResponse createHistoriesTable(){
        String query = "CREATE TABLE IF NOT EXISTS "+Table.HISTORIES.getValue()+" (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "idUser INTEGER," +
                "poids REAL," +
                "taille REAL,"+
                "imc REAL," +
                "date TEXT," +
                "heure TEXT," +
                "idUnitePoids INTEGER,"+
                "idUniteTaille INTEGER,"+
                "idCategory INTEGER," +
                "FOREIGN KEY (idUser) REFERENCES "+Table.USERS.getValue()+" (id)," +
                "FOREIGN KEY (idUnitePoids) REFERENCES "+Table.UNITE_POIDS.getValue()+" (id)," +
                "FOREIGN KEY (idUniteTaille) REFERENCES "+Table.UNITE_TAILLE.getValue()+" (id)," +
                "FOREIGN KEY (idCategory) REFERENCES "+Table.CATEGORIES.getValue()+" (id) )";

        return Database.execute(query, Table.HISTORIES);
    }

    public static IMCResponse execute(String query, Table table){
        IMCResponse res = new IMCResponse();
        try (Connection connection = Database.connect()) {
            Statement statement = connection.createStatement();
            statement.execute(query);
            if(table != null){
                res.name = HttpStatus.CREATED.name();
                res.status = HttpStatus.CREATED.value();
                res.message = "Succès création de la table "+table.getValue();
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + " : "+e.getMessage());
            if(table != null){
                res.message = "Echec création de la table "+table.getValue();
            }
        }
        return res;
    }

    public static IMCResponse createCategoriesTable(){
        String query = "CREATE TABLE IF NOT EXISTS "+Table.CATEGORIES.getValue()+" (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "subtitle TEXT," +
                "min REAL," +
                "max REAL," +
                "avatar TEXT)";

        return Database.execute(query, Table.CATEGORIES);
    }

    public static IMCResponse createAgeCategoriesTable(){
        String query = "CREATE TABLE IF NOT EXISTS "+Table.AGE_CATEGORIES.getValue()+" (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "minAge INTEGER," +
                "maxAge INTEGER," +
                "minVar REAL," +
                "maxVar REAL)";

        return Database.execute(query, Table.AGE_CATEGORIES);
    }

    public static IMCResponse createDescriptionsTable(){
        String query = "CREATE TABLE IF NOT EXISTS "+Table.DESCRIPTIONS.getValue()+" (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "idCategorie INTEGER," +
                "description TEXT, " +
                "FOREIGN KEY (idCategorie) REFERENCES "+Table.CATEGORIES.getValue()+" (id) )";

        return Database.execute(query, Table.DESCRIPTIONS);
    }

    public static IMCResponse createConseilsTable(){
        String query = "CREATE TABLE IF NOT EXISTS "+Table.CONSEILS.getValue()+" (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "idCategorie INTEGER," +
                "conseil TEXT, " +
                "FOREIGN KEY (idCategorie) REFERENCES "+Table.CATEGORIES.getValue()+" (id) )";

        return Database.execute(query, Table.CONSEILS);
    }

    public static IMCResponse createUnitePoidsTable(){
        String query = "CREATE TABLE IF NOT EXISTS "+Table.UNITE_POIDS.getValue()+" (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "value REAL)";

        return Database.execute(query, Table.UNITE_POIDS);
    }

    public static  IMCResponse createUniteTailleTable(){
        String query = "CREATE TABLE IF NOT EXISTS "+Table.UNITE_TAILLE.getValue()+" (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "value REAL)";

        return Database.execute(query, Table.UNITE_TAILLE);
    }

    public static IMCResponse dropAllTables(){
        IMCResponse res = new IMCResponse();
        Table tables[] = Table.values();
        for (Table t:tables ) {
            String query = "DROP TABLE IF EXISTS "+t.getValue();
            Database.execute(query, null);
        }
        res.message = "Toutes les tables supprimées";
        res.status = HttpStatus.OK.value();
        res.name = HttpStatus.OK.name();
        return res;
    }
}
