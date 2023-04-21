package com.easy.imc.webserviceeasyimc.services;

import com.easy.imc.webserviceeasyimc.dao.Database;
import com.easy.imc.webserviceeasyimc.dao.Table;
import com.easy.imc.webserviceeasyimc.models.IMCResponse;
import com.easy.imc.webserviceeasyimc.models.UnitePoids;
import com.easy.imc.webserviceeasyimc.models.UniteTaille;
import org.springframework.http.HttpStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnitePoidsService {
    public static IMCResponse initUnitePoidsTable(List<UnitePoids> unitePoidss){
        IMCResponse result = new IMCResponse();
        for (UnitePoids unitePoids:unitePoidss) {
            IMCResponse ir = create(unitePoids);
            if(ir.status == HttpStatus.CREATED.value()){
                result.values.add(unitePoids);
            }
        }
        result.status = HttpStatus.CREATED.value();
        result.name = HttpStatus.CREATED.name();
        result.message = unitePoidss.size()+" unités poids créées avec succès";
        return result;
    }
    public static IMCResponse create(UnitePoids unitePoids){
        String query = "INSERT INTO "+ Table.UNITE_POIDS.getValue() +" (name, value) VALUES (?, ?)";
        IMCResponse result = new IMCResponse();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, unitePoids.name);
            statement.setDouble(2, unitePoids.value);

            int nbRows = statement.executeUpdate();
            List<Object> values = new ArrayList<>();
            if(nbRows > 0){
                result.status = HttpStatus.CREATED.value();
                result.name = HttpStatus.CREATED.name();
                result.message = "Unité poids avec succès";
                values.add(nbRows);
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
        }
        return result;
    }
}
