package com.easy.imc.webserviceeasyimc.services;

import com.easy.imc.webserviceeasyimc.dao.Database;
import com.easy.imc.webserviceeasyimc.dao.Table;
import com.easy.imc.webserviceeasyimc.models.Description;
import com.easy.imc.webserviceeasyimc.models.History;
import com.easy.imc.webserviceeasyimc.models.IMCResponse;
import org.springframework.http.HttpStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoryService {
    public static IMCResponse initHistoriesTable(List<History> histories){
        IMCResponse result = new IMCResponse();
        for (History history:histories) {
            IMCResponse ir = create(history);
            if(ir.status == HttpStatus.CREATED.value()){
                result.values.add(history);
            }
        }
        result.status = HttpStatus.CREATED.value();
        result.name = HttpStatus.CREATED.name();
        result.message = histories.size()+" histories créées avec succès";
        return result;
    }
    public static IMCResponse create(History history){
        String query = "INSERT INTO "+ Table.HISTORIES.getValue() +" (idUser, poids, taille, imc) VALUES (?, ?, ?, ?)";
        IMCResponse result = new IMCResponse();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, history.idUser);
            statement.setDouble(2, history.poids);
            statement.setDouble(3, history.taille);
            statement.setDouble(4, history.imc);

            int nbRows = statement.executeUpdate();
            List<Object> values = new ArrayList<>();
            if(nbRows > 0){
                result.status = HttpStatus.CREATED.value();
                result.name = HttpStatus.CREATED.name();
                result.message = "History ajoutée avec succès";
                values.add(nbRows);
            }else{
                result.status = HttpStatus.BAD_REQUEST.value();
                result.name = HttpStatus.BAD_REQUEST.name();
                result.message = "History non ajoutée avec succès";
            }
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
        }
        return result;
    }
}
