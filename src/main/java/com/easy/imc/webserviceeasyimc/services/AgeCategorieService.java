package com.easy.imc.webserviceeasyimc.services;

import com.easy.imc.webserviceeasyimc.dao.Database;
import com.easy.imc.webserviceeasyimc.dao.Table;
import com.easy.imc.webserviceeasyimc.entities.AgeCategorie;
import com.easy.imc.webserviceeasyimc.entities.IMCResponse;
import com.easy.imc.webserviceeasyimc.entities.UnitePoids;
import com.easy.imc.webserviceeasyimc.models.AgeCategorieModel;
import com.easy.imc.webserviceeasyimc.models.CategoryModel;
import com.easy.imc.webserviceeasyimc.models.UnitePoidsModel;
import org.springframework.http.HttpStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgeCategorieService {
    public static IMCResponse<AgeCategorieModel> initAgeCategorieTable(List<AgeCategorie> ageCategories){
        IMCResponse<AgeCategorieModel> result = new IMCResponse<>();
        for (AgeCategorie ageCategorie:ageCategories) {
            IMCResponse<AgeCategorieModel> ir = create(ageCategorie);
            if(ir.status == HttpStatus.OK.value()){
                result.values.add(ageCategorie.toModel());
            }
        }
        result.status = HttpStatus.OK.value();
        result.name = HttpStatus.OK.name();
        result.message = ageCategories.size()+" catégories d'ages créées avec succès";
        return result;
    }

    public static IMCResponse<AgeCategorieModel> getAgeCategorieByAge(int age){
        String query = "SELECT * FROM "+ Table.AGE_CATEGORIES.getValue() +" WHERE ? BETWEEN minAge AND maxAge";
        IMCResponse<AgeCategorieModel> result = new IMCResponse<>();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, age);

            ResultSet rs = statement.executeQuery();
            List<AgeCategorieModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(rs.next()){
                result.message = "Catégorie d'age trouvée avec succès";
                AgeCategorieModel model = getInfo(rs);
                values.add(model);
            }else{
                result.message = "Catégorie d'age non trouvée";
            }
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
            throw new RuntimeException("Erreur recherche Category d'age par valeur age: "+e.getMessage());
        }
        return result;
    }

    public static IMCResponse<AgeCategorieModel> create(AgeCategorie ageCategorie){
        String query = "INSERT INTO "+ Table.AGE_CATEGORIES.getValue() +" (name, minAge, maxAge, minVar, maxVar) VALUES (?, ?, ?, ?, ?)";
        IMCResponse<AgeCategorieModel> result = new IMCResponse<>();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, ageCategorie.name);
            statement.setInt(2, ageCategorie.minAge);
            statement.setInt(3, ageCategorie.maxAge);
            statement.setDouble(4, ageCategorie.minVar);
            statement.setDouble(5, ageCategorie.maxVar);

            int nbRows = statement.executeUpdate();
            List<AgeCategorieModel> values = new ArrayList<>();
            if(nbRows > 0){
                result.status = HttpStatus.OK.value();
                result.name = HttpStatus.OK.name();
                result.message = "Catégorie d'age ajoutée avec succès";
                values.add(ageCategorie.toModel());
            }else{
                result.status = HttpStatus.BAD_REQUEST.value();
                result.name = HttpStatus.BAD_REQUEST.name();
                result.message = "Catégorie d'age non ajoutée avec succès";
            }
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
            throw new RuntimeException("Erreur ajout catégorie d'age: "+e.getMessage());
        }
        return result;
    }

    public static IMCResponse<AgeCategorieModel> findById(int id){
        IMCResponse<AgeCategorieModel> result = new IMCResponse<>();
        String query = "SELECT * FROM "+ Table.AGE_CATEGORIES.getValue() +" WHERE id = ?";
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            List<AgeCategorieModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(rs.next()){
                result.message = "Catégorie d'age trouvé avec succès";
                AgeCategorieModel model = getInfo(rs);
                values.add(model);
            }else{
                result.message = "Pas de catégorie d'age avec cet id";
            }
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());

            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    public static IMCResponse<AgeCategorieModel> delete(int id){
        IMCResponse<AgeCategorieModel> result = new IMCResponse<>();
        String query = "DELETE FROM "+ Table.AGE_CATEGORIES.getValue() +" WHERE id = ?";
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            int nbRows = statement.executeUpdate();
            List<AgeCategorieModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(nbRows > 0){
                result.message = "Catégorie d'age supprimée avec succès";
                AgeCategorieModel model = new AgeCategorieModel();
                model.id = id;
                values.add(model);
            }else{
                result.message = "Pas de catégorie d'age avec cet id";
            }
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());

            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    public static IMCResponse<AgeCategorieModel> update(AgeCategorie ageCategorie){
        String query = "UPDATE "+ Table.AGE_CATEGORIES.getValue() +" SET name = ?, minAge = ?, maxAge = ?, minVar = ?, maxVar = ? WHERE id = ?";
        IMCResponse<AgeCategorieModel> result = new IMCResponse<>();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, ageCategorie.name);
            statement.setInt(2, ageCategorie.minAge);
            statement.setInt(3, ageCategorie.maxAge);
            statement.setDouble(4, ageCategorie.minVar);
            statement.setDouble(5, ageCategorie.maxVar);
            statement.setInt(6, ageCategorie.id);

            int nbRows = statement.executeUpdate();
            List<AgeCategorieModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(nbRows>0){
                result.message = "Catégorie Age mise à jour avec succès";
                values.add(ageCategorie.toModel());
            }else{
                result.message = "Unité poids non mise à jour";
            }
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    public static IMCResponse<AgeCategorieModel> findAll(){
        IMCResponse<AgeCategorieModel> result = new IMCResponse<>();
        String query = "SELECT * FROM "+ Table.AGE_CATEGORIES.getValue();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet rs = statement.executeQuery();
            List<AgeCategorieModel> values = new ArrayList<>();
            while(rs.next()){
                AgeCategorieModel model = getInfo(rs);
                values.add(model);
            }
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            result.message = "Toutes les catégories d'age obtenus avec succès";
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    public static AgeCategorieModel getInfo(ResultSet rs) throws SQLException {
        AgeCategorieModel model = new AgeCategorieModel();
        model.id = rs.getInt("id");
        model.name = rs.getString("name");
        model.minAge = rs.getInt("minAge");
        model.maxAge = rs.getInt("maxAge");
        model.minVar = rs.getDouble("minVar");
        model.maxVar = rs.getDouble("maxVar");
        return model;
    }
}
