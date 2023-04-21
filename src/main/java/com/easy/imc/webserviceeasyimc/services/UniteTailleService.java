package com.easy.imc.webserviceeasyimc.services;

import com.easy.imc.webserviceeasyimc.dao.Database;
import com.easy.imc.webserviceeasyimc.dao.Table;
import com.easy.imc.webserviceeasyimc.models.Description;
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

public class UniteTailleService {
    public static IMCResponse initUniteTaillesTable(List<UniteTaille> uniteTailles){
        IMCResponse result = new IMCResponse();
        for (UniteTaille uniteTaille:uniteTailles) {
            IMCResponse ir = create(uniteTaille);
            if(ir.status == HttpStatus.CREATED.value()){
                result.values.add(uniteTaille);
            }
        }
        result.status = HttpStatus.CREATED.value();
        result.name = HttpStatus.CREATED.name();
        result.message = uniteTailles.size()+" unités tailles créées avec succès";
        return result;
    }
    public static IMCResponse create(UniteTaille uniteTaille){
        String query = "INSERT INTO "+ Table.UNITE_TAILLE.getValue() +" (name, value) VALUES (?, ?)";
        IMCResponse result = new IMCResponse();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, uniteTaille.name);
            statement.setDouble(2, uniteTaille.value);

            int nbRows = statement.executeUpdate();
            List<Object> values = new ArrayList<>();
            if(nbRows > 0){
                result.status = HttpStatus.CREATED.value();
                result.name = HttpStatus.CREATED.name();
                result.message = "Unité taille avec succès";
                values.add(nbRows);
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
        }
        return result;
    }
}
