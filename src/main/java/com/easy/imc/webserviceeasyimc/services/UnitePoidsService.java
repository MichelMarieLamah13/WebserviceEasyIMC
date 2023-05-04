package com.easy.imc.webserviceeasyimc.services;

import com.easy.imc.webserviceeasyimc.dao.Database;
import com.easy.imc.webserviceeasyimc.dao.Table;
import com.easy.imc.webserviceeasyimc.entities.IMCResponse;
import com.easy.imc.webserviceeasyimc.entities.UnitePoids;
import com.easy.imc.webserviceeasyimc.entities.User;
import com.easy.imc.webserviceeasyimc.models.UnitePoidsModel;
import com.easy.imc.webserviceeasyimc.models.UserModel;
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

public class UnitePoidsService {
    public static IMCResponse<UnitePoidsModel> initUnitePoidsTable(List<UnitePoids> unitePoidss){
        IMCResponse<UnitePoidsModel> result = new IMCResponse<>();
        for (UnitePoids unitePoids:unitePoidss) {
            IMCResponse<UnitePoidsModel> ir = create(unitePoids);
            if(ir.status == HttpStatus.OK.value()){
                result.values.add(unitePoids.toModel());
            }
        }
        result.status = HttpStatus.OK.value();
        result.name = HttpStatus.OK.name();
        result.message = unitePoidss.size()+" unités poids créées avec succès";
        return result;
    }
    public static IMCResponse<UnitePoidsModel> create(UnitePoids unitePoids){
        String query = "INSERT INTO "+ Table.UNITE_POIDS.getValue() +" (name, value) VALUES (?, ?)";
        IMCResponse<UnitePoidsModel> result = new IMCResponse<>();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, unitePoids.name);
            statement.setDouble(2, unitePoids.value);

            int nbRows = statement.executeUpdate();
            List<UnitePoidsModel> values = new ArrayList<>();
            if(nbRows > 0){
                result.status = HttpStatus.OK.value();
                result.name = HttpStatus.OK.name();
                result.message = "Unité poids avec succès";
                values.add(unitePoids.toModel());
            }else{
                result.status = HttpStatus.BAD_REQUEST.value();
                result.name = HttpStatus.BAD_REQUEST.name();
                result.message = "Unité poids non ajoutée avec succès";
            }
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
            throw new RuntimeException("Erreur ajout Unité poids: "+e.getMessage());
        }
        return result;
    }

    public static IMCResponse<UnitePoidsModel> findById(int id){
        IMCResponse<UnitePoidsModel> result = new IMCResponse<>();
        String query = "SELECT * FROM "+ Table.UNITE_POIDS.getValue() +" WHERE id = ?";
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            List<UnitePoidsModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(rs.next()){
                result.message = "Unité poids trouvé avec succès";
                UnitePoidsModel model = getInfo(rs);
                values.add(model);
            }else{
                result.message = "Pas d'unité poids avec cet id";
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

    public static IMCResponse<UnitePoidsModel> delete(int id){
        IMCResponse<UnitePoidsModel> result = new IMCResponse<>();
        String query = "DELETE FROM "+ Table.UNITE_POIDS.getValue() +" WHERE id = ?";
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            int nbRows = statement.executeUpdate();
            List<UnitePoidsModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(nbRows > 0){
                result.message = "Unité poids supprimé avec succès";
                UnitePoidsModel model = new UnitePoidsModel();
                model.id = id;
                values.add(model);
            }else{
                result.message = "Pas d'unité poids avec cet id";
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

    public static IMCResponse<UnitePoidsModel> update(UnitePoids unitePoids){
        String query = "UPDATE "+ Table.UNITE_POIDS.getValue() +" SET name = ?, value = ? WHERE id = ?";
        IMCResponse<UnitePoidsModel> result = new IMCResponse<>();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, unitePoids.name);
            statement.setDouble(2, unitePoids.value);
            statement.setInt(3, unitePoids.id);

            int nbRows = statement.executeUpdate();
            List<UnitePoidsModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(nbRows>0){
                result.message = "Unité poids mise à jour avec succès";
                values.add(unitePoids.toModel());
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

    public static IMCResponse<UnitePoidsModel> findAll(){
        IMCResponse<UnitePoidsModel> result = new IMCResponse<>();
        String query = "SELECT * FROM "+ Table.UNITE_POIDS.getValue();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet rs = statement.executeQuery();
            List<UnitePoidsModel> values = new ArrayList<>();
            while(rs.next()){
                UnitePoidsModel model = getInfo(rs);
                values.add(model);
            }
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            result.message = "Tous les unités poids obtenus avec succès";
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    public static UnitePoidsModel getInfo(ResultSet rs) throws SQLException {
        UnitePoidsModel model = new UnitePoidsModel();
        model.id = rs.getInt("id");
        model.name = rs.getString("name");
        model.value = rs.getDouble("value");
        return model;
    }
}
