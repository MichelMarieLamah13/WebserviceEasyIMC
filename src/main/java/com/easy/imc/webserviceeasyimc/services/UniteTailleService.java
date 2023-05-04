package com.easy.imc.webserviceeasyimc.services;

import com.easy.imc.webserviceeasyimc.dao.Database;
import com.easy.imc.webserviceeasyimc.dao.Table;
import com.easy.imc.webserviceeasyimc.entities.IMCResponse;
import com.easy.imc.webserviceeasyimc.entities.UnitePoids;
import com.easy.imc.webserviceeasyimc.entities.UniteTaille;
import com.easy.imc.webserviceeasyimc.models.UnitePoidsModel;
import com.easy.imc.webserviceeasyimc.models.UniteTailleModel;
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

public class UniteTailleService {
    public static IMCResponse<UniteTailleModel> initUniteTaillesTable(List<UniteTaille> uniteTailles){
        IMCResponse<UniteTailleModel> result = new IMCResponse<>();
        for (UniteTaille uniteTaille:uniteTailles) {
            IMCResponse<UniteTailleModel> ir = create(uniteTaille);
            if(ir.status == HttpStatus.OK.value()){
                result.values.add(uniteTaille.toModel());
            }
        }
        result.status = HttpStatus.OK.value();
        result.name = HttpStatus.OK.name();
        result.message = uniteTailles.size()+" unités tailles créées avec succès";
        return result;
    }
    public static IMCResponse<UniteTailleModel> create(UniteTaille uniteTaille){
        String query = "INSERT INTO "+ Table.UNITE_TAILLE.getValue() +" (name, value) VALUES (?, ?)";
        IMCResponse<UniteTailleModel> result = new IMCResponse<>();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, uniteTaille.name);
            statement.setDouble(2, uniteTaille.value);

            int nbRows = statement.executeUpdate();
            List<UniteTailleModel> values = new ArrayList<>();
            if(nbRows > 0){
                result.status = HttpStatus.OK.value();
                result.name = HttpStatus.OK.name();
                result.message = "Unité taille avec succès";
                values.add(uniteTaille.toModel());
            }else{
                result.status = HttpStatus.BAD_REQUEST.value();
                result.name = HttpStatus.BAD_REQUEST.name();
                result.message = "Unité taille non ajoutée avec succès";
            }
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
            throw new RuntimeException("Erreur ajout Unité taille: "+e.getMessage());
        }
        return result;
    }

    public static IMCResponse<UniteTailleModel> findById(int id){
        IMCResponse<UniteTailleModel> result = new IMCResponse<>();
        String query = "SELECT * FROM "+ Table.UNITE_TAILLE.getValue() +" WHERE id = ?";
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            List<UniteTailleModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(rs.next()){
                result.message = "Unité taille trouvé avec succès";
                UniteTailleModel model = getInfo(rs);
                values.add(model);
            }else{
                result.message = "Pas d'Unité taille avec cet id";
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

    public static IMCResponse<UniteTailleModel> delete(int id){
        IMCResponse<UniteTailleModel> result = new IMCResponse<>();
        String query = "DELETE FROM "+ Table.UNITE_TAILLE.getValue() +" WHERE id = ?";
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            int nbRows = statement.executeUpdate();
            List<UniteTailleModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(nbRows > 0){
                result.message = "Unité taille supprimé avec succès";
                UniteTailleModel model = new UniteTailleModel();
                model.id = id;
                values.add(model);
            }else{
                result.message = "Pas d'Unité taille avec cet id";
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

    public static IMCResponse<UniteTailleModel> update(UniteTaille uniteTaille){
        String query = "UPDATE "+ Table.UNITE_TAILLE.getValue() +" SET name = ?, value = ? WHERE id = ?";
        IMCResponse<UniteTailleModel> result = new IMCResponse<>();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, uniteTaille.name);
            statement.setDouble(2, uniteTaille.value);
            statement.setInt(3, uniteTaille.id);

            int nbRows = statement.executeUpdate();
            List<UniteTailleModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(nbRows>0){
                result.message = "Unité taille mise à jour avec succès";
                values.add(uniteTaille.toModel());
            }else{
                result.message = "Unité taille non mise à jour";
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

    public static IMCResponse<UniteTailleModel> findAll(){
        IMCResponse<UniteTailleModel> result = new IMCResponse<>();
        String query = "SELECT * FROM "+ Table.UNITE_TAILLE.getValue();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet rs = statement.executeQuery();
            List<UniteTailleModel> values = new ArrayList<>();
            while(rs.next()){
                UniteTailleModel model = getInfo(rs);
                values.add(model);
            }
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            result.message = "Tous les unités taille obtenus avec succès";
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    public static UniteTailleModel getInfo(ResultSet rs) throws SQLException {
        UniteTailleModel model = new UniteTailleModel();
        model.id = rs.getInt("id");
        model.name = rs.getString("name");
        model.value = rs.getDouble("value");
        return model;
    }
}
